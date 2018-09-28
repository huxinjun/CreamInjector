package com.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.creater.BindItemDefiner;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.templete.SmartRecyclerAdapter;
import com.creaminjector.utils.CreamUtils;
import com.creaminjector.utils.ULog;
import com.demo.R;
import com.demo.activity.MainActivity;
import com.demo.model.Duanzi;
import com.demo.model.News;
import com.demo.net.NetClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/21.
 */
@BindLayoutCreater(creater = Fragment2.Frag2Creater.class)
public class Fragment2 extends Fragment {

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


    @BindView(R.layout.fragment_2)
    public static class Frag2Creater extends LayoutCreater {

        @BindView(R.id.refresh_layout)
        public SmartRefreshLayout refresh_layout;

        @BindView(R.id.rcv)
        @BindFieldName("data")
        @BindItemDefiner(DuanziItemDefiner.class)
        private RecyclerView rcv;


        @Override
        public void onViewCreated() {
            super.onViewCreated();
            initData();
            refresh_layout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    initData();
                }
            });
        }

        private void initData() {
            NetClient.getApi().getDuanzi("https://www.apiopen.top/satinApi?type=1&page=1")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<Duanzi>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ULog.out("onError:" + e.getMessage());
                        }

                        @Override
                        public void onNext(Duanzi duanzi) {
                            ULog.out("data:" + duanzi);
                            setContentData(duanzi);
                            refresh_layout.finishRefresh();
                        }
                    });
        }

        @Override
        public void onDataPrepared() {
            super.onDataPrepared();

        }
    }


    public static class DuanziItemDefiner implements SmartRecyclerAdapter.ItemDefiner {

        @Override
        public void defineHeader(List<Class<? extends LayoutCreater>> headers) {

        }

        @Override
        public void defineFooter(List<Class<? extends LayoutCreater>> footers) {

        }

        @Override
        public Class<? extends LayoutCreater> defineItem(List<Object> allData, int position) {
            Duanzi.Data data = (Duanzi.Data) allData.get(position);
            switch (data.getType()) {
                case IMAGE:
                    return DuanziImageCreater.class;
//                case TEXT:
//                    break;
//                case VIDEO:
//                    break;
            }
            return null;
        }
    }

    @BindView(R.layout.item_duanzi_image)
    public static class DuanziImageCreater extends LayoutCreater<Duanzi.Data> {

        @BindView(R.id.iv_head)
        @BindFieldName("profile_image")
        private ImageView iv_head;

        @BindView(R.id.tv_name)
        @BindFieldName("name")
        private TextView tv_name;

        @BindView(R.id.tv_desc)
        @BindFieldName("text")
        private TextView tv_desc;

        @BindView(R.id.iv_pic)
        @BindFieldName("cdn_img")
        private ImageView iv_pic;

    }
}
