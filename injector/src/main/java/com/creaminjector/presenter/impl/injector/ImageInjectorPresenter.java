package com.creaminjector.presenter.impl.injector;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ImageInjectorPresenter extends InjectorPresenter {


	@Override
	public void inject(View target, Object value) {
//		Picasso.with(target.getContext()).load(value.toString()).into((ImageView) target);
//		Glide.with(target.getContext()).load(value).into((ImageView) target);
	}

}
