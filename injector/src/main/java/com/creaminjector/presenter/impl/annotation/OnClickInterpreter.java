package com.creaminjector.presenter.impl.annotation;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.OnClick;
import com.creaminjector.presenter.impl.layout.LayoutCreater;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class OnClickInterpreter extends AnnotationInterpreter {

	@Override
	public void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args) {
		final LayoutCreater creater=(LayoutCreater) args[0];
		OnClick onClickAnno =getAnnotation(target, OnClick.class);
		//这个注解可能会加在类上 或者字段上
		if(target.getClass()==Method.class) {
			//加在方法名上了
			final Method method=(Method) args[1];
			View view=creater.getContentView().findViewById(onClickAnno.viewId());
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					try {
						if(parameterTypes==null || parameterTypes.length==0)
								method.invoke(creater);
						else{
							if(parameterTypes[0]==View.class)
								method.invoke(creater,v);
						}
					}catch (Exception ex){
						throw new RuntimeException(ex);
					}
				}
			});
		}else if(target.getClass()==Field.class) {
			//加在字段上了
			Field field=(Field)args[1];
			String methodName=onClickAnno.value();
			try {
				final View view = (View) field.get(creater);

				Method declaredMethod = null;
				try {
					declaredMethod = creater.getClass().getDeclaredMethod(methodName, View.class);
				}catch (NoSuchMethodException e0) {
					declaredMethod = creater.getClass().getDeclaredMethod(methodName);
				}

				final Method finalDeclaredMethod = declaredMethod;
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Class<?>[] parameterTypes = finalDeclaredMethod.getParameterTypes();
						try {
							if(parameterTypes==null || parameterTypes.length==0)
								finalDeclaredMethod.invoke(creater);
							else{
								if(parameterTypes[0]==View.class)
									finalDeclaredMethod.invoke(creater,v);
							}
						}catch (Exception ex){
							throw new RuntimeException(ex);
						}
					}
				});
			}catch (ClassCastException e) {
				throw new RuntimeException(creater.getClass().getName()+"中的"+field.getName()+"字段必须是View子类!");
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(creater.getClass().getName()+"中的找不到"+methodName+"方法");
			}catch (Exception e) {
				e.printStackTrace();
			}

		}


	}

	@Override
	public void interpreter(Context context,Annotation annotation, InterpreterCallBack callBack, Object... args) {
		// TODO Auto-generated method stub
		
	}


	
}