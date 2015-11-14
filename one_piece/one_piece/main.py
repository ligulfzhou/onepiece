#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import motor
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import tornado.autoreload
import tornado.web
from tornado.options import options

from one_piece.handlers import *
from one_piece.util.session import SessionManager
from one_piece.util.cache import Cache
from one_piece.settings import log
from one_piece.settings import MONGO_ONEPIECE, MONGO_UTIL, SESSION_STORAGE, SESSION_TIMEOUT, SESSION_SECRET, CACHE_SERVER


class OnePiece(tornado.web.Application):
    def __init__(self):
        url_patterns = [
            (r'/api/v1/test', test.TestHandler),
            # user handler
            (r'/api/v1/user/init', user.AuthInitHandler),
            (r'/api/v1/user/login', user.LoginHandler),
            (r'/api/v1/user/logout', user.LogoutHandler),

            (r'/api/v1/threads', thread.ThreadsHandler),

            # get categories list, add one category, get one category info
            # (r"/api/v1/categories", categories.CategoriesHandler),
            # (r"/api/v1/categories/(\d+)", categories.CategoryHandler),

            # get goods list, add one good, get one good info
            # (r"/api/v1/goods", goods.GoodsHandler),
            # (r"/api/v1/goods/(\d+)", goods.GoodHandler),

            # get goods of this kind of category
            # (r"/api/v1/categories/(\d+)/goods", categoriedgoods.CategoriedGoodsHandler),

            # get user list, add one user(used to register one user), get one user info
            # (r"/api/v1/users", users.UsersHandler),
            # (r"/api/v1/users/(\d+)", users.UserHandler),

            # get orders list, make order, get one order info
            # (r"/api/v1/orders", orders.OrdersHandler),
            # (r"/api/v1/orders/(\d+)", orders.OrderHandler),

            # get orderitems for this order
            # (r"/api/v1/orders/(\d+)/orderitems", ordersorderitems.OrdersOrderitemsHandler),
            # (r"/api/v1/orders/(\d+)/orderitems/(\d+)", ordersorderitems.OrdersOrderitemHandler),

            # get user orders list
            # (r"/api/v1/users/(\d+)/orders", usersOrders.UsersOrdersHandler),

            # the goods user likes
            # (r"/api/v1/favorites", favorites.FavoritesHandler),
            # (r"/api/v1/favorites/(\d+)", favorites.FavoriteHandler),
        ]
        settings = dict(
            debug=options.debug,
            xsrf_cookies=True,
            cookie_secret="tehhnJguTtmt2auSkp8sAdw/s99JvUAdibdCtepkAwE=",
            db={
                'util': motor.MotorClient(MONGO_UTIL['master']).util,
                'onepiece': motor.MotorClient(MONGO_ONEPIECE['master']).onepiece
            },
            session_manager=SessionManager(SESSION_SECRET, SESSION_STORAGE, SESSION_TIMEOUT),
            cache=Cache(CACHE_SERVER)
        )
        super(OnePiece, self).__init__(url_patterns, **settings)


def main():
    app = OnePiece()
    http_server = tornado.httpserver.HTTPServer(app,
                                                ssl_options={
                                                    "certfile": "/etc/ssl/certs/selfcacert.pem",
                                                    "keyfile": "/etc/ssl/private/selfprivkey.pem"
                                                })
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()


if __name__ == "__main__":
    tornado.options.parse_command_line()
    print("run on port %s" % str(options.port))
    try:
        os.system('/home/ubuntu/py3env/bin/celery -A celery_task worker -loglevel=info')
    except:
        log.error("celery run error")
        exit(1)
    log.info("run celery tasks")
    main()
