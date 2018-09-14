package com.creaminjector.presenter.impl.annotation;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.presenter.impl.layout.LayoutCreater;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * @BindFieldName解释器
 * @author XINJUN
 *
 */
public class BindFieldNameInterpreter extends AnnotationInterpreter {


	@Override
	public void interpreter(Context context,AnnotatedElement target,
			InterpreterCallBack callBack, Object... args) {
		LayoutCreater creater=(LayoutCreater) args[0];
		Field field= (Field) args[1];
		BindFieldName annotation = getAnnotation(target, BindFieldName.class);

		try {
			View view= (View) field.get(creater);
			view.setTag(LayoutCreater.TAG_INJECTOR_FIELD, annotation.value());
			view.setTag(LayoutCreater.TAG_MULTI_DATA_INDEX, annotation.index());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
