package com.creaminjector.templete;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.creaminjector.presenter.ILayoutPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;

import java.util.List;

public class SmartViewPagerAdapter extends PagerAdapter {

    List<ILayoutPresenter.CreaterInfo> mCreaterInfos;


    private Context context;

    @SuppressWarnings("unchecked")
    public SmartViewPagerAdapter(Context context, List<ILayoutPresenter.CreaterInfo> createrInfos) {
        this.context = context;
        mCreaterInfos = createrInfos;
    }

    @Override
    public int getCount() {
        return mCreaterInfos == null ? 0 : mCreaterInfos.size();
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ILayoutPresenter.CreaterInfo createrInfo = mCreaterInfos.get(position);
        final View[] view = {null};
        CreamUtils.inflate(context, createrInfo.clazz, new ILayoutPresenter.InflateCallBack() {

            @Override
            public void onCompleted(LayoutCreater instance) {
                view[0] = instance.getContentView();
            }
        });
        container.addView(view[0]);
        return view[0];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
