# -*- coding='utf-8' -*-
from tornado.gen import coroutine
from tornado.options import options
from one_piece.settings import log
from one_piece.handlers.base import BaseHandler
from one_piece.util.decorator import login_required
from one_piece.util.helper import error, ErrorCode, mongo_uid


class ThreadsHandler(BaseHandler):
    @coroutine
    def get(self):
        '''
        获取thread列表
        参数：page： int
              tag： int
        '''
        try:
            page = int(self.get_argument('page', None) or 1)
            print(page)
            # tag = int(self.get_argument('tag', None) or 0)
        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.PARAMERR))
            return

        query = {}
        #if tag:

        #    query.update({'tags': tag})
        try:
            threads = yield self.db['onepiece'].thread.find(query, {'_id': 0, 'id': 1, 'title': 1, 'imgs': 1}).sort(
                [('id', -1)]).skip(options.pagesize * (page - 1)).limit(options.pagesize).to_list(options.pagesize)
            self.write(threads)
        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.DBERR))
            return

    @login_required
    @coroutine
    def post(self):
        '''title: string
           content: string
           tags: [int]
           imgs: [url]
        '''
        try:
            title = self.get_argument('title')
            content = self.get_argument('content')
            tags = self.get_arguments('tags[]')
            imgs = self.get_arguments('imgs[]')
        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.PARAMERR))
            return

        data = {
            'id': mongo_uid('onepiece', 'thread'),
            'title': title,
            'content': content,
            'uid': self.current_user.get('id'),
            'role': self.current_user.get('role'),
            'headimg': self.current_user.get('headimg'),
            'reads': 0,
            'favorites': 0,
            'shares': 0,
            'comments': 0,
            'tags': [int(tag) for tag in tags],
            'imgs': imgs
        }
        try:
            yield self.db['onepiece'].thread.insert(data)
        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.DBERR))
            return
