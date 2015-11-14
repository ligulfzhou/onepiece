# -*- coding: utf-8 -*-
import time
from tornado.web import RequestHandler
from one_piece.settings import log
from one_piece.util.helper import json_dumps
from one_piece.util.session import Session


class BaseHandler(RequestHandler):
    def __init__(self, application, request, **kwargs):
        self.start = time.time()
        self.errcode = 0
        self.response = None

        super(BaseHandler, self).__init__(application, request, **kwargs)
        self.db = self.settings['db']
        self.cache = self.settings['cache']
        self.session = Session(self.settings['session_manager'], self)

    def write(self, chunk):
        if isinstance(chunk, (dict, list, tuple)):
            if 'errcode' in chunk:
                self.errcode = chunk['errcode']

            chunk = json_dumps(chunk)
            self.set_header('Content-Type', 'application/json; charset=UTF-8')
            self.response = chunk[:253] + '...' if len(chunk) > 256 else chunk
        else:
            ct = self._headers['Content-Type']
            if isinstance(ct, bytes):
                ct = ct.decode()

            self.response = '<%s>' % ct.split(';')[0]

        super(BaseHandler, self).write(chunk)

    # def check_xsrf_cookie(self):
    #     if self.request.path == '/api/v1/threads':
    #         return
    #     return

    def get_current_user(self):
        return self.session.get('user', None)

    def on_finish(self):
        queries = self.request.query_arguments
        if '_xsrf' in queries:
            queries.pop('_xsrf')

        bodies = self.request.body_arguments
        if '_xsrf' in bodies:
            bodies.pop('_xsrf')
        if 'mobile' in bodies:
            mobile = bodies['mobile'][0]
            bodies['mobile'] = mobile[:3] + b'*' * 4 + mobile[-4:]
        if 'password' in bodies:
            password = bodies['password'][0]
            bodies['password'] = password[:1] + b'*' * (len(password) - 2) + password[-1:]
        if 'account' in bodies:
            account = bodies['account'][0]
            bodies['account'] = account[:4] + b'*' * (len(account) - 8) + account[-4:]
        if 'idcard' in bodies:
            idcard = bodies['idcard'][0]
            bodies['idcard'] = idcard[:4] + b'*' * (len(idcard) - 8) + idcard[-4:]

        log.note('userid=%d|ip=%s|method=%s|path=%s|query=%s|body=%s|code=%d|errcode=%d|time=%.3f|ua=%s|resp=%s' %
                 (self.current_user.get('id'), self.request.remote_ip, self.request.method, self.request.path,
                  queries, bodies, self._status_code, self.errcode, (time.time() - self.start) * 1000,
                  self.request.headers.get('User-Agent', ''), self.response or ''))

        self.errcode = 0
        self.response = None
