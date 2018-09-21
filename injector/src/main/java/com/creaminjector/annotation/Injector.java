package com.creaminjector.annotation;

import com.creaminjector.presenter.IInjectionPresenter;
import com.creaminjector.presenter.impl.annotation.InjectorInterpreter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在字段上使用此注解表明数据注入器
 * @author xinjun
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Interpreter(InjectorInterpreter.class)
public @interface Injector {

	Class<? extends IInjectionPresenter> value();

}
