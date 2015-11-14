# -*- coding: utf-8 -*-

import os
from tornado.options import define
from one_piece.util.logger import initlog

define("port", default=443, help="server running port", type=int)
define("pagesize", default=20, help="page size", type=int)
define('debug', default=False, help="whether debug", type=bool)

SESSION_STORAGE = {'host': '127.0.0.1', 'port': 6379, 'db': 1, 'password': 'redis_pass'}
SESSION_TIMEOUT = 7200
SESSION_SECRET = "lCocJPSaQPKL4TZF/xfD/x1L5OSyUkQdkxQemYSxstE="

CACHE_SERVER = {'host': '127.0.0.1', 'port': 6379, 'db': 2, 'password': 'redis_pass'}

MONGO_UTIL = {'master': 'mongodb://localhost:27017'}
MONGO_ONEPIECE = {'master': 'mongodb://localhost:27017'}

ROOT_PATH = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
PROCESS_SN = os.path.basename(ROOT_PATH)
log = initlog({
    'INFO': '%s/log/one_piece.info.log' % ROOT_PATH,
    'NOTE': '%s/log/one_piece.note.log' % ROOT_PATH,
    'ERROR': '%s/log/one_piece.error.log' % ROOT_PATH
}, mode='timed', backup_count=15)