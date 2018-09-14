package com.creaminjector.presenter;

import android.content.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Annotation解释器接口
 * 包含所有关于注解逻辑的接口
 * @author xinjun
 *
 */
public interface IAnnotationPresenter{
	/**
	 * Annotation解释器回调接口
	 * @author xinjun
	 *
	 */
	interface InterpreterCallBack{
		/**
		 * 每一个Annotation解释完毕后将会发起一次回调
		 * @param anno 当前刚解释完的Annotation
		 * @param results 返回的结果,可有可无
		 */
		void onCompleted(Class<? extends Annotation> anno,Object... results);
	}
	
	/**
	 * 根据给定的字段或者类型解释加载在其上的所有注解
	 * @param target 要被解释的目标可以使class,field等等可以声明Annotation任何对象
	 * @param callBack 每当发现一个注解解释完成后会发起回调
	 * @param context 上下文环境
	 */
	void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args);
	
	/**
	 * 给定一个注解对象进行解释(适用于注解参数中的嵌套注解)
	 * @param annotation 注解
	 * @param callBack 解释回调
	 * @param context 上下文环境
	 */
	void interpreter(Context context,Annotation annotation,InterpreterCallBack callBack,Object... args);

}
