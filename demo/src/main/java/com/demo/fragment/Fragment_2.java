package com.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.BindMultiData;
import com.creaminjector.annotation.creater.BindItemDefiner;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindLayoutCreaterHeader;
import com.creaminjector.annotation.creater.BindLayoutCreaters;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.templete.SmartRecyclerAdapter;
import com.creaminjector.utils.CreamUtils;
import com.demo.R;
import com.demo.model.Accounts;
import com.demo.model.Rooms;
import com.easyjson.EasyJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

@BindLayoutCreater(creater = Fragment_2.MyCreater.class)
public class Fragment_2 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return CreamUtils.bind(this);
    }

    @BindView(R.layout.fragment_2)
    public static class MyCreater extends LayoutCreater {


        @BindLayoutCreaters({@BindLayoutCreater(creater = Vp1Creater.class),
                @BindLayoutCreater(creater = Vp2Creater.class),
                @BindLayoutCreater(creater = Vp3Creater.class),
                @BindLayoutCreater(creater = Vp4Creater.class)})
        @BindView(R.id.vp)
        public ViewPager viewPager;


        @Override
        public void onViewCreated() {
        }

        @Override
        public void onDataPrepared() {

        }
    }


    @BindView(R.layout.layout_vp1)
    public static class Vp1Creater extends LayoutCreater<Rooms> {


        @BindView(R.id.lv_content)
        @BindFieldName("result.rooms")
        @BindMultiData(2)
        @BindLayoutCreater(creater = LvCreater.class)
        @BindLayoutCreaterHeader(creater = LvHeaderCreater.class)
        public ListView lv_content;

        @Override
        public void onInitData() {
            OkHttpUtils.get().url("http://api.mengliaoba.cn/apiv5/live/liveshow.php?cmd=hotroom").build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    Rooms javaBean = EasyJson.getJavaBean(response, Rooms.class);
                    setContentData(javaBean);
                }
            });
        }

        @Override
        public void onViewCreated() {

        }

        @Override
        public void onDataPrepared() {
            Rooms.Result.Room numOne = getContentData().getResult().getRooms().remove(0);
            LayoutCreater headerCreater = getHeaderCreater(lv_content);
            headerCreater.setContentData(numOne);
        }

        @BindView(R.layout.layout_header_img)
        public static class LvHeaderCreater extends LayoutCreater<Rooms.Result.Room> {


            @BindFieldName(value = "imgPathM")
            @BindView(R.id.iv_icon)
            public ImageView iv_icon;

            @Override
            public void onDataPrepared() {
            }
        }


        @BindView(R.layout.layout_room)
        public static class LvCreater extends LayoutCreater<Rooms.Result.Room> {


            @BindView(R.id.hot_item_image_01)
            @BindFieldName(value = "imgPathM", index = 0)
            public ImageView hot_item_image_01;

            @BindView(R.id.hot_item_image_02)
            @BindFieldName(value = "imgPathM", index = 1)
            public ImageView hot_item_image_02;

            @Override
            public void onDataPrepared() {
            }
        }

    }


    @BindView(R.layout.layout_vp2)
    public static class Vp2Creater extends LayoutCreater<Rooms> {

        @BindView(R.id.rcv)
        @BindFieldName("result.rooms")
        @BindItemDefiner(Vp2Definer.class)
        public RecyclerView rcv;

        @Override
        public void onViewCreated() {
            super.onViewCreated();
            rcv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        @Override
        public void onInitData() {

            OkHttpUtils.get().url("http://api.mengliaoba.cn/apiv5/live/liveshow.php?cmd=hotroom").build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    Rooms javaBean = EasyJson.getJavaBean(response, Rooms.class);
                    setContentData(javaBean);
                }
            });
        }

        @Override
        public void onDataPrepared() {
            Rooms.Result.Room numOne = getContentData().getResult().getRooms().remove(0);
            setRecyclerViewHeaderData(rcv, 0, numOne);
            Rooms.Result.Room numTwo = getContentData().getResult().getRooms().remove(10);
            setRecyclerViewFooterData(rcv, 0, numTwo);
        }

        public static class Vp2Definer extends SmartRecyclerAdapter.SimpleItemDefiner {
            @Override
            public void defineHeader(List<Class<? extends LayoutCreater>> headers) {
                headers.add(RcvHeaderCreater.class);
            }

            @Override
            public void defineFooter(List<Class<? extends LayoutCreater>> footers) {
                footers.add(RcvHeaderCreater.class);
            }

            @Override
            public Class<? extends LayoutCreater> defineItem(List<Object> allData, int position) {
                return RcvItemCreater.class;
            }
        }

        @BindView(R.layout.layout_header_img)
        public static class RcvHeaderCreater extends LayoutCreater<Rooms.Result.Room> {
            @BindFieldName(value = "imgPathM")
            @BindView(R.id.iv_icon)
            public ImageView iv_icon;

            @Override
            public void onDataPrepared() {
            }
        }

        @BindView(R.layout.layout_room_single)
        public static class RcvItemCreater extends LayoutCreater<Rooms.Result.Room> {
            @BindView(R.id.hot_item_image_01)
            @BindFieldName(value = "imgPathM", index = 0)
            public ImageView hot_item_image_01;

            @Override
            public void onDataPrepared() {
            }
        }
    }


    @BindView(R.layout.layout_vp3)
    public static class Vp3Creater extends LayoutCreater<Accounts> {


        @BindView(R.id.tv_content)
        public TextView textView;

        @Override
        public void onDataPrepared() {
        }
    }


    @BindView(R.layout.layout_vp4)
    public static class Vp4Creater extends LayoutCreater<Accounts> {


        @BindView(R.id.tv_content)
        public TextView textView;

        @Override
        public void onDataPrepared() {
        }
    }


}
