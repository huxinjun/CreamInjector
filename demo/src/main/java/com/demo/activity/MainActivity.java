package com.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;
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
import com.demo.fragment.Fragment1;
import com.demo.fragment.Fragment2;
import com.demo.fragment.Fragment3;
import com.demo.view.BottomBar;
import com.squareup.picasso.Picasso;

@BindLayoutCreater(creater = MainActivity.MainActivityCreater.class)
public class MainActivity extends AppCompatActivity {


    public static class ImageInjector implements IInjectionPresenter {

        @Override
        public void inject(View target, Object value) {
            value = value.toString().startsWith("http") ? value : "https://xzbenben.cn/AccountBook/image/get/" + value;
            ULog.out("image:" + value);
            Picasso.with(target.getContext()).load(value.toString()).into((ImageView) target);
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


        @BindView(R.id.ll_tab1)
        @OnClick("tabSelect")
        private View ll_tab1;

        @BindView(R.id.iv_tab1)
        private ImageView iv_tab1;

        @BindView(R.id.tv_tab1)
        private TextView tv_tab1;


        @BindView(R.id.ll_tab2)
        @OnClick("tabSelect")
        private View ll_tab2;

        @BindView(R.id.iv_tab2)
        private ImageView iv_tab2;

        @BindView(R.id.tv_tab2)
        private TextView tv_tab2;


        @BindView(R.id.ll_tab3)
        @OnClick("tabSelect")
        private View ll_tab3;

        @BindView(R.id.iv_tab3)
        private ImageView iv_tab3;

        @BindView(R.id.tv_tab3)
        private TextView tv_tab3;


        private Fragment1 fragment1 = new Fragment1();
        private Fragment2 fragment2 = new Fragment2();
        private Fragment3 fragment3 = new Fragment3();

        @Override
        public void onViewCreated() {
            super.onViewCreated();
            CreamUtils.changeFragment(getContext(), new IFragmentPresenter.FragmentInfo(R.id.fl_container, fragment1));
        }

        private void tabSelect(View view) {
            switch (view.getId()) {
                case R.id.ll_tab1:
                    iv_tab1.setImageResource(R.drawable.tab_news_select);
                    tv_tab1.setTextColor(getContext().getColor(R.color.colorAccent));
                    iv_tab2.setImageResource(R.drawable.tab_duanzi);
                    tv_tab2.setTextColor(getContext().getColor(R.color.text));
                    iv_tab3.setImageResource(R.drawable.tab_photo);
                    tv_tab3.setTextColor(getContext().getColor(R.color.text));
                    CreamUtils.changeFragment(getContext(), new IFragmentPresenter.FragmentInfo(R.id.fl_container, fragment1));
                    break;
                case R.id.ll_tab2:
                    iv_tab1.setImageResource(R.drawable.tab_news);
                    tv_tab1.setTextColor(getContext().getColor(R.color.text));
                    iv_tab2.setImageResource(R.drawable.tab_duanzi_select);
                    tv_tab2.setTextColor(getContext().getColor(R.color.colorAccent));
                    iv_tab3.setImageResource(R.drawable.tab_photo);
                    tv_tab3.setTextColor(getContext().getColor(R.color.text));
                    CreamUtils.changeFragment(getContext(), new IFragmentPresenter.FragmentInfo(R.id.fl_container, fragment2));

                    break;
                case R.id.ll_tab3:
                    iv_tab1.setImageResource(R.drawable.tab_news);
                    tv_tab1.setTextColor(getContext().getColor(R.color.text));
                    iv_tab2.setImageResource(R.drawable.tab_duanzi);
                    tv_tab2.setTextColor(getContext().getColor(R.color.text));
                    iv_tab3.setImageResource(R.drawable.tab_photo_select);
                    tv_tab3.setTextColor(getContext().getColor(R.color.colorAccent));
                    CreamUtils.changeFragment(getContext(), new IFragmentPresenter.FragmentInfo(R.id.fl_container, fragment3));
                    break;

            }
        }
    }

}
