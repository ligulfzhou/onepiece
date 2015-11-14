# -*- coding: utf-8 -*-
from tornado.gen import coroutine
from one_piece.settings import log
from one_piece.handlers.base import BaseHandler
from one_piece.util.helper import error, ErrorCode


class AuthInitHandler(BaseHandler):
    def get(self):
        self.add_header("X-XSRFToken", self.xsrf_token)
        return self.write({
            'init': 'ok'
        })


class LoginHandler(BaseHandler):
    @coroutine
    def post(self):
        try:
            username = self.get_argument('username')
            password = self.get_argument('password')
        except Exception as e:
            log.error(e)
            return self.set_status(401)

        try:
            user = yield self.db['onepiece'].user.find_one({'name': username}, {'_id': 0})
            if user:
                if password == user['password']:
                    del user['password']
                    self.session['user'] = user
                    self.session.save()
                    self.write(user)
                else:
                    return self.write(error(ErrorCode.PWDERR))
            else:
                return self.write(error(ErrorCode.NOUSER))

        except Exception as e:
            log.error(e)
            return self.write("db error")


class LogoutHandler(BaseHandler):
    def post(self):
        self.session.remove()
        return self.write({
            'logout': 'ok'
        })
