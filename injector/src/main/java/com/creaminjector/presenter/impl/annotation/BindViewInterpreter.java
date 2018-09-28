package com.creaminjector.presenter.impl.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.impl.layout.LayoutCreater;

public class BindViewInterpreter extends AnnotationInterpreter {

    @Override
    public void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args) {
        LayoutCreater creater = (LayoutCreater) args[0];
        BindView bindViewAnno = getAnnotation(target, BindView.class);
        //这个注解可能会加在类上 或者字段上
        if (target instanceof Class) {
            //加在类上了
            creater.setContentView(View.inflate(context, bindViewAnno.value(), null));
            creater.getContentView().setTag(LayoutCreater.TAG_LAYOUT_CREATER, creater);
            if (callBack != null)
                callBack.onCompleted(BindView.class);
        } else if (target instanceof Field) {
            //加在字段上了
            //根据creater类声明上配置的@LayoutResource实例化出View
            Field viewField = (Field) args[1];
            if (viewField == null)
                return;
            try {
                //反射注入控件实例
                View viewById = creater.getContentView().findViewById(bindViewAnno.value());
                if (viewById == null)
                    throw new RuntimeException("BinView配置的视图id在布局中找不到相应的view,请检查" + creater.getClass().getName() + "." + viewField.getName());
                viewField.set(creater, viewById);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("申明的BinView字段类型和布局中的类型不匹配：" + creater.getClass().getName() + "." + viewField.getName());
            }

        }


    }

    @Override
    public void interpreter(Context context, Annotation annotation,
                            InterpreterCallBack callBack, Object... args) {
        // TODO Auto-generated method stub

    }


}