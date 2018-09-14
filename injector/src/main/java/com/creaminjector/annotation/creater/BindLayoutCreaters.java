package com.creaminjector.annotation.creater;


import com.creaminjector.annotation.Interpreter;
import com.creaminjector.presenter.impl.annotation.BindLayoutCreatersInterpreter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在类型声明上使用此注解,来指示这个类管理的布局所使用的数据类型
 * @author xinjun
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Interpreter(BindLayoutCreatersInterpreter.class)
public @interface BindLayoutCreaters {

	BindLayoutCreater[] value();

}
