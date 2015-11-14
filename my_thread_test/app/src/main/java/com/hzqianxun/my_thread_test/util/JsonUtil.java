package com.hzqianxun.my_thread_test.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static <T> T toBean(Class<T> tClass, InputStream is){
        T obj = null;
        Reader reader = new InputStreamReader(is);
        obj = gson.fromJson(reader, tClass);
        return obj;
    }

    public static <T> List<T> getList(Class<T[]> type, InputStream is){
        List<T> objs = null;
        Reader reader = new InputStreamReader(is);
        Type t = new TypeToken<ArrayList<T>>(){}.getType();
        objs = gson.fromJson(reader, t);
        return objs;
    }
}
