package com.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.creaminjector.annotation.Fragment;
import com.creaminjector.annotation.OnClick;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.IFragmentPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.demo.R;
import com.demo.fragment.Fragment_1;
import com.demo.fragment.Fragment_2;

@BindLayoutCreater(creater = MainActivity.MainActivityCreater.class)
public class MainActivity extends FragmentActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreamUtils.bind(this);
    }

    @BindView(R.layout.activity_main)
    public static class MainActivityCreater extends LayoutCreater {

        @Fragment(clazz = Fragment_1.class)
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

        }

        public void btn1click() {
            Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
            CreamUtils.changeFragment(getContext(), new IFragmentPresenter.FragmentInfo(R.id.fl_container, Fragment_1.class));
        }

        @OnClick(viewId = R.id.btn_2)
        public void btn2click() {
            Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
            CreamUtils.changeFragment(getContext(), new IFragmentPresenter.FragmentInfo(R.id.fl_container, Fragment_2.class));
        }


    }
}
