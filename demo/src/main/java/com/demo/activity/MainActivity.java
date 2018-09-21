package com.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.creaminjector.annotation.OnClick;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.IFragmentPresenter;
import com.creaminjector.presenter.IInjectionPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.creaminjector.utils.ULog;
import com.demo.R;
import com.demo.fragment.Fragment_1;
import com.demo.fragment.Fragment_2;
import com.squareup.picasso.Picasso;

@BindLayoutCreater(creater = MainActivity.MainActivityCreater.class)
public class MainActivity extends FragmentActivity {


    public static class ImageInjector implements IInjectionPresenter {

        @Override
        public void inject(View target, Object value) {
            value = value.toString().startsWith("http") ? value : "https://xzbenben.cn/AccountBook/image/get/" + value;
            ULog.out("image:" + value);
            Picasso.with(target.getContext()).load(value.toString()).into((ImageView) target);
//            Glide.with(target.getContext()).load(value.toString()).into((ImageView) target);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreamUtils.bind(this);
        IInjectionPresenter.DEFAULT_INJECTOR.put(ImageView.class, ImageInjector.class);
    }

    @BindView(R.layout.activity_main)
    public static class MainActivityCreater extends LayoutCreater {

        @BindView(R.id.fl_container)
        public FrameLayout fl_container;

        @BindView(R.id.btn_1)
        @OnClick("btn1click")
        public Button btn_1;

        @BindView(R.id.btn_2)
        public Button btn_2;

        @Override
        public void onDataPrepared() {

        }

        @Override
        public void onViewCreated() {
            CreamUtils.changeFragment(getContext(), R.anim.right_in, R.anim.right_out, new IFragmentPresenter.FragmentInfo(R.id.fl_container, f1));
        }

        private Fragment_1 f1 = new Fragment_1();
        private Fragment_2 f2 = new Fragment_2();


        public void btn1click() {
            Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
            CreamUtils.changeFragment(getContext(),new IFragmentPresenter.FragmentInfo(R.id.fl_container, f1));
        }

        @OnClick(viewId = R.id.btn_2)
        public void btn2click() {
            Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
            CreamUtils.changeFragment(getContext(),new IFragmentPresenter.FragmentInfo(R.id.fl_container, f2));
        }


    }

}
