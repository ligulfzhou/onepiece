# -*- coding: utf-8 -*-
from functools import wraps


def login_required(method):
    @wraps(method)
    def wrapper(self, *args, **kwargs):
        if not self.current_user:
            self.set_status(401)
            return
        return method(self, *args, **kwargs)

    return wrapper


def admin_required(method):
    @wraps(method)
    def wrapper(self, *args, **kwargs):
        if not self.current_user and self.current_user['role'] is 1:
            self.set_status(403)
            return
        return method(self, *args, **kwargs)

    return wrapper
