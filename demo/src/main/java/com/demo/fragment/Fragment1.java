package com.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.Injector;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.IInjectionPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.creaminjector.utils.ULog;
import com.demo.R;
import com.demo.model.News;
import com.demo.net.NetClient;
import com.easyjson.annotation.JSONField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/21.
 */
@BindLayoutCreater(creater = Fragment1.Frag1Creater.class)
public class Fragment1 extends Fragment {

    private View mContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContainer = CreamUtils.bind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mContainer;
    }


    @BindView(R.layout.fragment_1)
    public static class Frag1Creater extends LayoutCreater {

        @BindView(R.id.tab_layout)
        @BindFieldName("titles")
        @Injector(TabInjector.class)
        private TabLayout tab_layout;

        @BindView(R.id.vp_pager)
        @BindFieldName("pagers")
        @BindLayoutCreater(creater = PagerCreater.class)
        private ViewPager vp_pager;


        public static class TabInjector implements IInjectionPresenter {

            @Override
            public void inject(View target, Object value) {
                TabLayout tabLayout = (TabLayout) target;
                List<String> data = (List<String>) value;
                Iterator<String> iterator = data.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    tabLayout.addTab(tabLayout.newTab().setText(next));
                }
            }
        }

        private void setTabWidth() {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            WindowManager mWindowManager = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE));
            mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
            try {
                Field mRequestedTabMinWidth = TabLayout.class.getDeclaredField("mRequestedTabMinWidth");
                mRequestedTabMinWidth.setAccessible(true);
                mRequestedTabMinWidth.set(tab_layout, (int) (mDisplayMetrics.widthPixels / 4));
                tab_layout.invalidate();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onViewCreated() {
            super.onViewCreated();
            tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
            setTabWidth();

            NetClient.getApi().getConfig("https://www.apiopen.top/journalismApi")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<News>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(News news) {
                            news.convertDataMapToList();
                            setContentData(news);
                            ULog.out("data:" + news);
                        }
                    });

        }

        //TODO 1.viewpager显示不出来,2.是否可以删除LayoutCreater中的mDataListeners

        @BindView(R.layout.item_pager)
        public static class PagerCreater extends LayoutCreater {

            @BindView(R.id.rcv)
            @BindFieldName("data")
            @BindLayoutCreater(creater = NewsItemCreater.class)
            private RecyclerView rcv;

            @Override
            public void onDataPrepared() {
                super.onDataPrepared();
                ULog.out("");
            }
        }

        @BindView(R.layout.item_news)
        public static class NewsItemCreater extends LayoutCreater {

            @BindView(R.id.tv_title)
            @BindFieldName("title")
            private TextView tv_title;

        }
    }
}
