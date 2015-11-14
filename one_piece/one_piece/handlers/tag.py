# -*- coding='utf-8' -*-
import time
from tornado.gen import coroutine
from tornado.options import options
from one_piece.settings import log
from one_piece.handlers.base import BaseHandler
from one_piece.util.helper import error, ErrorCode


class TagsHandler(BaseHandler):
    @coroutine
    def get(self):
        try:
            tags = yield self.db['onepiece'].tag.find({}, {'_id': 0}).to_list(None)
            self.write(tags)
            return

        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.DBERR))
            return


class TagThreadsHandler(BaseHandler):
    @coroutine
    def get(self, tag_id, threads=True):
        if not tag_id:
            log.note('{tag_id} not legal'.format(tag_id=tag_id))
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
            threads = yield self.db['onepiece'].thread.find({'tags': int(tag_id)}, {'_id': 0}).sort([('id', -1)]).skip(
                pagesize * (page - 1)).limit(pagesize).to_list(pagesize)
            if not threads:
                return self.write({})

            for thread in threads:
                thread['created'] = time.strftime('%Y-%m-%d %X', time.localtime(int(thread['created']) / 1000))
            self.write(threads)
            return

        except Exception as e:
            log.error(e)
            self.write(error(ErrorCode.DBERR))
            return
