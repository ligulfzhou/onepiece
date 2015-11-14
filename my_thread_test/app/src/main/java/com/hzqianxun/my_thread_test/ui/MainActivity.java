package com.hzqianxun.my_thread_test.ui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnTouch;

import com.hzqianxun.my_thread_test.AppConfig;
import com.hzqianxun.my_thread_test.AppContext;
import com.hzqianxun.my_thread_test.AppManager;
import com.hzqianxun.my_thread_test.R;
import com.hzqianxun.my_thread_test.interf.BaseViewInterface;
import com.hzqianxun.my_thread_test.interf.OnTabReselectListener;
import com.hzqianxun.my_thread_test.widget.MyFragmentTabHost;

public class MainActivity extends ActionBarActivity implements
        OnTabChangeListener, BaseViewInterface, View.OnClickListener,
        View.OnTouchListener {

    private DoubleClickExitHelper mDoubleClickExit;

    @Bind(android.R.id.tabhost)
    public MyFragmentTabHost mTabHost;

//    private BadgeView mBvNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
        AppManager.getAppManager().addActivity(this);


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void initView() {
        mDoubleClickExit = new DoubleClickExitHelper(this);

        //title

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if(Build.VERSION.SDK_INT > 10){
            mTabHost.getTabWidget().setShowDividers(0);
        }

        initTabs();

//
//        // �м䰴��ͼƬ����
//        mAddBt.setOnClickListener(this);

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);

//        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
//        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
//        registerReceiver(mReceiver, filter);
//        NoticeUtils.bindToService(this);
//
//        if (AppContext.isFristStart()) {
////            mNavigationDrawerFragment.openDrawerMenu();
//            DataCleanManager.cleanInternalCache(AppContext.getInstance());
//            AppContext.setFristStart(false);
//        }
//
//        checkUpdate();
    }

    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(
                    mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                    null);
//            if (i == 2) {
//                indicator.setVisibility(View.INVISIBLE);
//                mTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
//            }
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabContentFactory() {

                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);

//            if (mainTab.equals(MainTab.ME)) {
//                View cn = indicator.findViewById(R.id.tab_mes);
//                mBvNotice = new BadgeView(MainActivity.this, cn);
//                mBvNotice.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//                mBvNotice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//                mBvNotice.setBackgroundResource(R.drawable.notification_bg);
//                mBvNotice.setGravity(Gravity.CENTER);
//            }
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            Log.d("currentview", v.toString());
            Log.d("currenttab", mTabHost.getCurrentTabTag());
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
//        if (tabId.equals(getString(MainTab.ME.getResName()))) {
//            mBvNotice.setText("");
//            mBvNotice.hide();
//        }
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
                return mDoubleClickExit.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        NoticeUtils.unbindFromService(this);
//        unregisterReceiver(mReceiver);
//        mReceiver = null;
//        NoticeUtils.tryToShutDown(this);
//    }
}
