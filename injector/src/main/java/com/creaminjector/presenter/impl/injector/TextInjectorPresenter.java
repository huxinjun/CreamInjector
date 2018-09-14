package com.creaminjector.presenter.impl.injector;

import android.view.View;
import android.widget.TextView;


public class TextInjectorPresenter extends InjectorPresenter {


	@Override
	public void inject(View target, Object value) {
		TextView tv= (TextView) target;
		tv.setText(value==null?"":value.toString());
	}

}
