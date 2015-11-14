package com.hzqianxun.my_thread_test.bean;

//import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * 数据操作结果实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public class Result implements Serializable {

    @SerializedName("errcode")
    private int errcode;

    @SerializedName("errmsg")
    private String errmsg;

    public boolean OK() {
	return errcode == 1;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
