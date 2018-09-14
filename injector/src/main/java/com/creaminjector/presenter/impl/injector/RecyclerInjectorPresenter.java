package com.creaminjector.presenter.impl.injector;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.creaminjector.templete.SmartRecyclerAdapter;

public class RecyclerInjectorPresenter extends InjectorPresenter {


	@Override
	public void inject(View target, Object value) {
		RecyclerView recyclerView=(RecyclerView) target;
		if(value!=null)
			recyclerView.setAdapter(new SmartRecyclerAdapter(target.getContext(),recyclerView));
	}

}
