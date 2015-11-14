# -*- coding='utf-8' -*-
import time
from tornado.gen import coroutine
from tornado.options import options
from one_piece.settings import log
from one_piece.handlers.base import BaseHandler
from one_piece.utils import login_required
from one_piece.util.helper import error, ErrorCode, mongo_uid


class CommentsHandler(BaseHandler):
    @coroutine
    def get(self, thread_id, comments=True):
        if not thread_id:
            log.note('{thread_id} not legal'.format(thread_id=thread_id))
            self.write(error(ErrorCode.REQERR))
            return

        try:
            page = int(self.get_argument('page', None) or 1)
            pagesize = int(self.get_argument('pagesize', None) or options.pagesize)
        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.PARAMERR))
            return

        try:
            comments = yield self.db['onepiece'].comment.find({'tid': int(thread_id)}, {'_id': 0}).sort(
                [('id', -1)]).skip(
                pagesize * (page - 1)).limit(pagesize).to_list(pagesize)
            if not comments:
                return self.write({})

            for comment in comments:
                comment['created'] = time.strftime('%Y-%m-%d %X', time.localtime(int(comment['created']) / 1000))
            self.write(comments)

        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.DBERR))
            return

    @login_required
    @coroutine
    def post(self, thread_id, comments=True):
        try:
            content = self.get_argument('content')
        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.PARAMERR))
            return

        try:
            floor = yield self.db['onepiece'].comment.find({'tid': int(thread_id)}, {'_id': 0}).count() + 1
            data = {
                'id': mongo_uid('onepiece', 'comment'),
                'tid': int(thread_id),
                'uid': self.current_user['id'],
                'name': self.current_user['name'],
                'nickname': self.current_user['nickname'],
                'headimg': self.current_user['headimg'],
                'content': content,
                'floor': floor,
                'created': round(time.time() * 1000)
            }
            yield self.db['onepiece'].comment.insert(data)

        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.DBERR))
            return
