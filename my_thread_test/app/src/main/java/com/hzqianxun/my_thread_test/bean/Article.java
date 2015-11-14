package com.hzqianxun.my_thread_test.bean;

//import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Article extends Entity{
    @SerializedName("title")
    private String title;

    @SerializedName("imgs")
    private String[] imgs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }
}
