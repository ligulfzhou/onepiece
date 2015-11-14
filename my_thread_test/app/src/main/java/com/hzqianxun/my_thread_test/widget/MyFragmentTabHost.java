package com.hzqianxun.my_thread_test.widget;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

public class MyFragmentTabHost extends FragmentTabHost{

    private String mCurrentTab;

    private String mNoTabChangedTab;

    public MyFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onTabChanged(String tag) {
        if (tag.equals(mNoTabChangedTab)){
            setCurrentTabByTag(mCurrentTab);
        }else{
            super.onTabChanged(tag);
            mCurrentTab = tag;
        }
    }

    public void setNoTabChangedTag(String tag){
        this.mNoTabChangedTab = tag;
    }
}
