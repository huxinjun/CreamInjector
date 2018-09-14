package com.creaminjector.annotation.creater;


import com.creaminjector.templete.SmartRecyclerAdapter;
import com.creaminjector.annotation.Interpreter;
import com.creaminjector.presenter.impl.annotation.BindItemDefinerInterpreter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 适用于RecyclerView的item布局定义
 * @author xinjun
 *
 */
@Interpreter(BindItemDefinerInterpreter.class)
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindItemDefiner {

	Class<? extends SmartRecyclerAdapter.ItemDefiner> value();

}
