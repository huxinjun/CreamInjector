package com.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.demo.model.ImageList;
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
@BindLayoutCreater(creater = Fragment3.Frag3Creater.class)
public class Fragment3 extends Fragment {

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


    @BindView(R.layout.fragment_3)
    public static class Frag3Creater extends LayoutCreater {
        @BindView(R.id.refresh_layout)
        public SmartRefreshLayout refresh_layout;

        @BindView(R.id.rcv)
        @BindFieldName("data")
        @BindItemDefiner(Fragment3.ImageListDefiner.class)
        private RecyclerView rcv;


        @Override
        public void onViewCreated() {
            super.onViewCreated();
            initData();
//            rcv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            refresh_layout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    initData();
                }
            });
        }

        private void initData() {
            NetClient.getApi().getImageList("https://www.apiopen.top/meituApi?page=1")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ImageList>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ULog.out("onError:" + e.getMessage());
                        }

                        @Override
                        public void onNext(ImageList imageList) {
                            ULog.out("data:" + imageList);
                            setContentData(imageList);
                            refresh_layout.finishRefresh();
                        }
                    });
        }

        @Override
        public void onDataPrepared() {
            super.onDataPrepared();

        }
    }


    public static class ImageListDefiner implements SmartRecyclerAdapter.ItemDefiner {

        @Override
        public void defineHeader(List<Class<? extends LayoutCreater>> headers) {

        }

        @Override
        public void defineFooter(List<Class<? extends LayoutCreater>> footers) {

        }

        @Override
        public Class<? extends LayoutCreater> defineItem(List<Object> allData, int position) {
            ImageList.Data data = (ImageList.Data) allData.get(position);
            if (data == null)
                return null;
            return ImageItemCreater.class;
        }
    }

    @BindView(R.layout.item_images_item)
    public static class ImageItemCreater extends LayoutCreater{

        @BindView(R.id.iv_pic)
        @BindFieldName("url")
        private ImageView iv_pic;

    }
}