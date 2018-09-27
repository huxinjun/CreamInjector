package com.creaminjector.presenter.impl.injector;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.templete.SmartAbsListAdapter;

public class AdapterInjectorPresenter extends InjectorPresenter {

	@SuppressWarnings("unchecked")
	@Override
	public void inject(View target, Object value) {
		//设置adapter
		AdapterView<BaseAdapter> adapterView=(AdapterView<BaseAdapter>) target;
		if(value==null)
			adapterView.setAdapter(null);
		else{
			adapterView.setTag(LayoutCreater.TAG_ITEMS_DATA,value);
			adapterView.setAdapter(new SmartAbsListAdapter(target.getContext(),adapterView));
		}
	}

}
