package com.creaminjector.presenter;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.creaminjector.presenter.impl.injector.AdapterInjectorPresenter;
import com.creaminjector.presenter.impl.injector.ButtonInjectorPresenter;
import com.creaminjector.presenter.impl.injector.CheckBoxInjectorPresenter;
import com.creaminjector.presenter.impl.injector.ImageInjectorPresenter;
import com.creaminjector.presenter.impl.injector.RadioButtonInjectorPresenter;
import com.creaminjector.presenter.impl.injector.RecyclerInjectorPresenter;
import com.creaminjector.presenter.impl.injector.TextInjectorPresenter;
import com.creaminjector.presenter.impl.injector.PagerInjectorPresenter;

/**
 * Activity业务类
 * 主要负责:
 * 1.启动一个新的Activity
 * 2.按照Activity类声明的注解绑定布局,实例化初始的Fragment,并切换到相应的视图
 * 3.
 * @author xinjun
 *
 */
public interface IInjectionPresenter{


	/**
	 * 默认的注入器
	 */
	Map<Class<? extends View>,Class<? extends IInjectionPresenter>> DEFAULT_INJECTOR=new HashMap<Class<? extends View>,Class<? extends IInjectionPresenter>>(){
		{
			put(TextView.class, TextInjectorPresenter.class);
			put(ImageView.class, ImageInjectorPresenter.class);
			put(Button.class, ButtonInjectorPresenter.class);
			put(CheckBox.class, CheckBoxInjectorPresenter.class);
			put(RadioButton.class, RadioButtonInjectorPresenter.class);
			put(ListView.class, AdapterInjectorPresenter.class);
			put(GridView.class, AdapterInjectorPresenter.class);
			put(ViewPager.class, PagerInjectorPresenter.class);
			put(RecyclerView.class, RecyclerInjectorPresenter.class);
		}
	};
	
	
	
	void inject(View target,Object value);

}
