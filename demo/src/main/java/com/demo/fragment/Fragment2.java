package com.demo.fragment;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.OnClick;
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
import com.demo.utils.DisplayUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
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
//                case IMAGE:
//                    return DuanziImageCreater.class;
//                case TEXT:
//                    break;
                case VIDEO:
                    return DuanziVideoCreater.class;
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


    @BindView(R.layout.item_duanzi_video)
    public static class DuanziVideoCreater extends LayoutCreater<Duanzi.Data> {

        @BindView(R.id.iv_head)
        @BindFieldName("profile_image")
        private ImageView iv_head;

        @BindView(R.id.tv_name)
        @BindFieldName("name")
        private TextView tv_name;

        @BindView(R.id.tv_desc)
        @BindFieldName("text")
        private TextView tv_desc;

        @BindView(R.id.rl_video)
        private View rl_video;

        @BindView(R.id.video_view)
        private VideoView video_view;

        @BindView(R.id.iv_video)
        @BindFieldName("bimageuri")
        private ImageView iv_video;


        @Override
        public void onViewCreated() {
            super.onViewCreated();

        }

        @Override
        public void onDataPrepared() {
            super.onDataPrepared();
            int width = Integer.parseInt(getContentData().width);
            int height = Integer.parseInt(getContentData().height);

            ViewGroup.LayoutParams layoutParams = rl_video.getLayoutParams();
            layoutParams.width = DisplayUtil.getScreenWidth(getContext());
            layoutParams.height = (int) (layoutParams.width * 1.0f / width * height);
            rl_video.setLayoutParams(layoutParams);
        }

        @OnClick(viewId = R.id.video_view)
        private void play() {
            ULog.out("play()");
            final String videouri = getContentData().videouri;
            getContext().bindService(new Intent(getContext(), PlayerService.class), new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    PlayerService.MyBinder binder = (PlayerService.MyBinder) iBinder;
                    MediaPlayer player = binder.getService().getPlayer();
                    if (player != null && player.isPlaying() && binder.getService().getCurrPlayUrl().equals(videouri)) {
                        ULog.out("play videouri playing!!!");
                        player.setDisplay(video_view.getHolder());
                        return;
                    } else {
                        ULog.out("play videouri new Player///");
                        binder.getService().releasePlayer();
                        MediaPlayer playerNew = new MediaPlayer();
                        binder.getService().setPlayer(playerNew);
                        play(playerNew, videouri);
                        binder.getService().setCurrPlayUrl(videouri);
                    }


                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {

                }
            }, Context.BIND_AUTO_CREATE);
        }

        private void play(final MediaPlayer player, String videouri) {
            ULog.out("play videouri:" + videouri);
            try {
                player.setDisplay(video_view.getHolder());
                player.setDataSource(videouri);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        int videoWidth = mediaPlayer.getVideoWidth();
                        int videoHeight = mediaPlayer.getVideoHeight();
                        ULog.out("play onPrepared:videoWidth=" + videoWidth + ",videoHeight=" + videoHeight);
                        player.start();
                        iv_video.setVisibility(View.GONE);
                    }
                });
                player.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                        ULog.out("play onInfo:i=" + i + ",i1=" + i1);
                        return false;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static class PlayerService extends Service {

        MediaPlayer player;

        public String getCurrPlayUrl() {
            return currPlayUrl;
        }

        public void setCurrPlayUrl(String currPlayUrl) {
            this.currPlayUrl = currPlayUrl;
        }

        String currPlayUrl;

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return new MyBinder();
        }

        public class MyBinder extends Binder {

            public PlayerService getService() {
                return PlayerService.this;
            }
        }

        public void setPlayer(MediaPlayer player) {
            this.player = player;
        }

        public MediaPlayer getPlayer() {
            return player;
        }

        public void releasePlayer() {
            if (player != null) {
                player.stop();
                player.release();
            }
        }
    }
}
