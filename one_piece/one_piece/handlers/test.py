# -*- coding: utf-8 -*-
from one_piece.settings import log
from one_piece.util.helper import error, ErrorCode
from one_piece.handlers.base import BaseHandler
from one_piece.util.celery_task import fib


class TestHandler(BaseHandler):
    def get(self):
        print("hello world")
        self.write({'hello': 'world'})

    def post(self):
        print("hello world")
        self.write({'hello': 'world'})

    def put(self):
        print("hello world")
        self.write({'hello': 'world'})

class TestCeleryTask(BaseHandler):
    def get(self):
        try:
            n = int(self.get_arguemnt('n'))
        except Exception as e:
            log.error(error(ErrorCode.PARAMERR))

        fib.delay(100)