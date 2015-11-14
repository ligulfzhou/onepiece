package com.hzqianxun.my_thread_test.ui;

//import net.oschina.app.R;
//import net.oschina.app.fragment.ExploreFragment;
//import net.oschina.app.fragment.MyInformationFragment;
//import net.oschina.app.viewpagerfragment.NewsViewPagerFragment;
//import net.oschina.app.viewpagerfragment.TweetsViewPagerFragment;

import com.hzqianxun.my_thread_test.R;
import com.hzqianxun.my_thread_test.fragment.ArticleFragment;
import com.hzqianxun.my_thread_test.viewpagerfragment.HomeViewPagerFragment;

public enum MainTab {

	NEWS(0, R.string.main_tab_name_home, R.drawable.tab_icon_home, HomeViewPagerFragment.class),

	TWEET(1, R.string.main_tab_name_home1, R.drawable.tab_icon_home, ArticleFragment.class),

	QUICK(2, R.string.main_tab_name_home2, R.drawable.tab_icon_home, HomeViewPagerFragment.class),

	EXPLORE(3, R.string.main_tab_name_home3, R.drawable.tab_icon_home, ArticleFragment.class),

	ME(4, R.string.main_tab_name_home4, R.drawable.tab_icon_home, HomeViewPagerFragment.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
