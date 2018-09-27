package com.creaminjector.presenter.impl.injector;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.templete.SmartViewPagerSameTemplaterAdapter;

public class PagerInjectorPresenter extends InjectorPresenter {


	@Override
	public void inject(View target, Object value) {
		//ViewPager每一页都是一样的
		ViewPager pagerView=(ViewPager) target;
		if(value==null)
			pagerView.setAdapter(null);
		else{
			pagerView.setTag(LayoutCreater.TAG_ITEMS_DATA,value);
			pagerView.setAdapter(new SmartViewPagerSameTemplaterAdapter(target.getContext(),pagerView));
		}
	}

}
