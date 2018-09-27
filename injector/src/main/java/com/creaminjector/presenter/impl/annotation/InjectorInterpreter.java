package com.creaminjector.presenter.impl.annotation;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.Injector;
import com.creaminjector.presenter.IInjectionPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * @author xinjun
 * @Injector注解解释器
 */
public class InjectorInterpreter extends AnnotationInterpreter {

    @Override
    public void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args) {
        final LayoutCreater creater = (LayoutCreater) args[0];
        Field field = (Field) args[1];
        field.setAccessible(true);
        if (target.getClass() == Field.class) {

            final Injector injector = getAnnotation(target, Injector.class);
            Class<? extends IInjectionPresenter> value = injector.value();
            try {
                View view= (View) field.get(creater);
                view.setTag(LayoutCreater.TAG_INJECTOR_CLASS, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interpreter(Context context, Annotation annotation, InterpreterCallBack callBack, Object... args) {
        //Ignore
    }

}