package com.creaminjector.presenter.impl.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import android.content.Context;

import com.creaminjector.presenter.IAnnotationPresenter;

/**
 * 关于注解的逻辑类
 * @author xinjun
 *
 */
public abstract class AnnotationInterpreter implements IAnnotationPresenter {

	/**
	 * 具体此方法的实现逻辑由众多解释器来做,这样也便于以后支持新的注解
	 */
	@Override
	public abstract void interpreter(Context context,AnnotatedElement target,InterpreterCallBack callBack,Object... args);

	@Override
	public void interpreter(Context context, Annotation annotation, InterpreterCallBack callBack, Object... args) {

	}

	public <T extends Annotation> T getAnnotation(AnnotatedElement target,
												  Class<T> annoType) {
		T annotation = target.getAnnotation(annoType);
		if(annotation==null)
			throw new RuntimeException("在"+target+"中未找到"+annoType.getName()+"注解");
		return annotation;
	}
}
