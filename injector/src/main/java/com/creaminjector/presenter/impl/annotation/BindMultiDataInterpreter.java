package com.creaminjector.presenter.impl.annotation;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.BindMultiData;
import com.creaminjector.presenter.impl.layout.LayoutCreater;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * @BindMultiData解释器
 * @author XINJUN
 *
 */
public class BindMultiDataInterpreter extends AnnotationInterpreter {

	@Override
	public void interpreter(Context context, Annotation annotation,
							InterpreterCallBack callBack, Object... args) {
		//Ignore
	}

	@Override
	public void interpreter(Context context,AnnotatedElement target,
			InterpreterCallBack callBack, Object... args) {
		LayoutCreater creater=(LayoutCreater) args[0];
		Field field= (Field) args[1];
		BindMultiData annotation = getAnnotation(target, BindMultiData.class);

		try {
			View view= (View) field.get(creater);
			view.setTag(LayoutCreater.TAG_MULTI_DATA_COUNT, annotation.value());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
