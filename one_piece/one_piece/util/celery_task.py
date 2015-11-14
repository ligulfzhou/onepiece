# -*- coding: utf-8 -*-
from celery import Celery, Task
from one_piece.settings import log

app = Celery('hello', backend='amqp://guest@localhost', broker='amqp://guest@localhost//')


class BaseTask(Task):
    abstract = True

    def after_return(self, *args, **kwargs):
        # should log to different file
        log.info("")

@app.task
def fib(n):
    if n == 1:
        return 1
    return fib(n-1) * n
