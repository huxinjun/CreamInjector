package com.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

@BindLayoutCreater(creater = WebActivity.WebCreater.class)
public class WebActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = CreamUtils.bind(this);
        WebCreater webCreater = (WebCreater) LayoutCreater.getLayoutCreater(view);
        webCreater.loadUrl(getIntent().getStringExtra("url"));
    }

    @BindView(R.layout.activity_web)
    public static class WebCreater extends LayoutCreater {


        @BindView(R.id.web_view)
        private WebView web_view;

        private String url;

        @Override
        public void onViewCreated() {
            super.onViewCreated();
            WebSettings webSettings = web_view.getSettings();
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 设置缓存
            webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
            webSettings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        }

        private void loadUrl(String url) {
            ULog.out("url:" + url);
            this.url = url;
            web_view.loadUrl(url);
        }
    }

}
