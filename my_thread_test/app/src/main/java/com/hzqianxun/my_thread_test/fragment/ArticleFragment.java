package com.hzqianxun.my_thread_test.fragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

//import net.oschina.app.adapter.NewsAdapter;
//import net.oschina.app.api.remote.OSChinaApi;
//import net.oschina.app.base.BaseListFragment;
//import net.oschina.app.base.ListBaseAdapter;
//import net.oschina.app.bean.News;
//import net.oschina.app.bean.NewsList;
//import net.oschina.app.interf.OnTabReselectListener;
//import net.oschina.app.ui.empty.EmptyLayout;
//import net.oschina.app.util.UIHelper;
//import net.oschina.app.util.XmlUtils;
import android.view.View;
import android.widget.AdapterView;

import com.hzqianxun.my_thread_test.adapter.ArticleAdapter;
import com.hzqianxun.my_thread_test.api.remote.RestApi;
import com.hzqianxun.my_thread_test.base.BaseListFragment;
import com.hzqianxun.my_thread_test.base.ListBaseAdapter;
import com.hzqianxun.my_thread_test.bean.Article;
import com.hzqianxun.my_thread_test.interf.OnTabReselectListener;
//import com.hzqianxun.my_thread_test.util.JsonUtil;
import com.hzqianxun.my_thread_test.util.JsonUtils;

import org.apache.commons.io.IOUtils;

/**
 * 新闻资讯
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年11月12日 下午4:17:45
 *
 */
public class ArticleFragment extends BaseListFragment<Article> implements
        OnTabReselectListener {

    protected static final String TAG = ArticleFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "newslist_";

    @Override
    protected ArticleAdapter getListAdapter() {
        return new ArticleAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mCatalog;
    }

//    @Override
//    protected ListEntity<Article> parseList(InputStream is) throws Exception {
//        ListEntity<Article> list = null;
//        try {
//            list = JsonUtils.getList(Article[].class, is);
//        } catch (NullPointerException e) {
//            list =  new ListEntity<Article>();
//        }
//        return list;
//    }
    @Override
    protected List<Article> parseList(InputStream is) throws Exception {
//        ListEntity<Article> list = null;
        List<Article> list = null;
        try {
            list = JsonUtils.getList(Article[].class, IOUtils.toByteArray(is));
//            list = JsonUtil.getList(Article[].class, is);

        } catch (NullPointerException e) {
            list =  new ArrayList<Article>();
        }
        return list;
    }

//    @Override
//    protected NewsList readList(Serializable seri) {
//        return ((NewsList) seri);
//    }

    @Override
    protected List<Article> readList(Serializable seri) {
        return ((List<Article>) seri);
    }

    @Override
    protected void sendRequestData() {
        RestApi.getThreadList(mCurrentPage, 0, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Article article = mAdapter.getItem(position);
//        News news = mAdapter.getItem(position);
//        if (news != null) {
//            UIHelper.showNewsRedirect(view.getContext(), news);
//
//            // 放入已读列表
//            saveToReadedList(view, NewsList.PREF_READED_NEWS_LIST, news.getId()
//                    + "");
//        }
    }

    @Override
    protected void executeOnLoadDataSuccess(List<Article> data) {
//        if (mCatalog == NewsList.CATALOG_WEEK
//                || mCatalog == NewsList.CATALOG_MONTH) {
//            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            if (mState == STATE_REFRESH)
                mAdapter.clear();
            mAdapter.addData(data);
            mState = STATE_NOMORE;
            mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
            return;
//        }
        // android studio error TODO
        //super.executeOnLoadDataSuccess(data);
    }

    @Override
    public void onTabReselect() {
        onRefresh();
    }

//    @Override
//    protected long getAutoRefreshTime() {
//        // 最新资讯两小时刷新一次
//        if (mCatalog == NewsList.CATALOG_ALL) {
//
//            return 2 * 60 * 60;
//        }
//        return super.getAutoRefreshTime();
//    }
}
