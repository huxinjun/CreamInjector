package com.creaminjector.presenter.impl.annotation;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.creater.BindItemDefiner;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.presenter.ILayoutPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.creaminjector.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * BindLayoutCreater注解解释器
 * Created by xinjun on 2017/7/29 14:33
 */
public class BindItemDefinerInterpreter extends AnnotationInterpreter {

	@Override
	public void interpreter(Context context, final AnnotatedElement target, final InterpreterCallBack callBack, Object... args) {
		if(target.getClass()==Field.class){
			//加在字段上了
			LayoutCreater creater=(LayoutCreater) args[0];
			Field field= (Field) args[1];
			field.setAccessible(true);
			try {
				//这个View 可能是ListView,GridView,ViewPager等
				View findViewById = (View) field.get(creater);
				BindItemDefiner definer = getAnnotation(target, BindItemDefiner.class);
				//给配置了@BindLayoutCreater注解的视图中放入一些tag记录其子创建器的和其他需要的信息
				findViewById.setTag(LayoutCreater.TAG_LAYOUT_CRETAER_PARENT, creater);
				findViewById.setTag(LayoutCreater.TAG_DEFINER_ITEM_CLASS, definer.value());
			} catch (Exception e) {
				//出错误说明没有配置这个注解,不用管
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void interpreter(Context context,Annotation annotation,
			InterpreterCallBack callBack, Object... args) {
		//嵌套的，ViewPager能配置BindLayoutCreaters注解
		BindLayoutCreater bindLayoutCreaterAnno= (BindLayoutCreater) annotation;
		ILayoutPresenter.CreaterInfo info=new ILayoutPresenter.CreaterInfo(bindLayoutCreaterAnno.creater());
		if(callBack!=null)
			callBack.onCompleted(annotation.annotationType(),info);
	}
	
}
