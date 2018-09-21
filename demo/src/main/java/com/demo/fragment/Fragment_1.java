package com.demo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.IInjectionPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.creaminjector.utils.ULog;
import com.demo.R;
import com.demo.dialog.LoadingDialog;
import com.demo.model.Accounts;
import com.easyjson.EasyJson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

@BindLayoutCreater(creater = Fragment_1.MyCreater.class)
public class Fragment_1 extends Fragment {


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
    public static class MyCreater extends LayoutCreater<Accounts> {


        @BindFieldName("msg")
        @BindView(R.id.tv_content)
        public TextView tv_content;

        @BindLayoutCreater(creater = LvCreater.class)
        @BindView(R.id.lv)
        @BindFieldName("accounts")
        public AdapterView lv;

        @Override
        public void onInitData() {
            ULog.out("time.1:"+System.currentTimeMillis());
            OkHttpUtils.get().url("https://xzbenben.cn/AccountBook/account/getAll?TEST").build().execute(new StringCallback() {

                Dialog dialog = new LoadingDialog(getContext());

                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    ULog.out("time.2:"+System.currentTimeMillis());
                    Accounts javaBean = EasyJson.getJavaBean(response, Accounts.class);
                    ULog.out("time.3:"+System.currentTimeMillis());
                    setContentData(javaBean);
                    ULog.out("time.4:"+System.currentTimeMillis());
                }

                @Override
                public void onBefore(Request request, int id) {
                    super.onBefore(request, id);
                    dialog.show();
                }

                @Override
                public void onAfter(int id) {
                    super.onAfter(id);
                    dialog.dismiss();
                }
            });

        }

        @Override
        public void onDataPrepared() {
            Accounts contentData = getContentData();
            contentData.setMsg("你！！！");
            ULog.out("Fragment_1.MyCreater.onDataPrepared:" + contentData);
        }
    }


    @BindView(R.layout.layout_account)
    public static class LvCreater extends LayoutCreater<Accounts.Account> {


        @BindFieldName("userIcon")
        @BindView(R.id.iv_icon)
        public ImageView iv_icon;

        @BindFieldName("name")
        @BindView(R.id.tv_name)
        public TextView tv_name;

        @BindFieldName("date")
        @BindView(R.id.tv_date)
        public TextView tv_date;

        @BindFieldName("paidIn")
        @BindView(R.id.tv_paidin)
        public TextView tv_paidin;

        @BindView(R.id.tv_tag)
        public TextView tv_tag;

        @BindFieldName("description")
        @BindView(R.id.tv_content)
        public TextView tv_content;

        @BindFieldName("addrName")
        @BindView(R.id.tv_addr)
        public TextView tv_addr;

        @BindFieldName("dateDis")
        @BindView(R.id.tv_time)
        public TextView tv_time;

        @BindLayoutCreater(creater = ImgCreater.class)
        @BindFieldName("imgs")
        @BindView(R.id.gv_pics)
        public GridView gv_pics;

        @Override
        public void onDataPrepared() {
            if (getContentData().getIsPrivate())
                tv_tag.setVisibility(View.VISIBLE);
            else
                tv_tag.setVisibility(View.GONE);
        }


        @BindView(R.layout.layout_img)
        public static class ImgCreater extends LayoutCreater<String> {

            @BindFieldName
            @BindView(R.id.iv_icon)
            public ImageView iv_icon;

            @Override
            public void onDataPrepared() {
                ULog.out("ImgCreater.onDataPrepared:" + getContentData());

            }
        }


    }


}
