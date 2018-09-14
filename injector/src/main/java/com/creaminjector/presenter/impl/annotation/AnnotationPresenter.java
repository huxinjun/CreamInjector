package com.creaminjector.presenter.impl.annotation;

import android.content.Context;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.Interpreter;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.IAnnotationPresenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;

/**
 * Created by xinjun on 2018/9/14.
 */

public class AnnotationPresenter implements IAnnotationPresenter {


    @Override
    public void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args) {
        Annotation[] annotations = target.getAnnotations();
        if (annotations == null || annotations.length == 0)
            return;
        //BindView优先于其他注解
        //BindFieldName优先于其他注解，次于BindView
        ArrayList<Annotation> annos = new ArrayList<>();
        Annotation bindViewAnno = findAnnotation(annotations, BindView.class);
        if (bindViewAnno != null)
            annos.add(bindViewAnno);
        Annotation bindFieldNameAnno = findAnnotation(annotations, BindFieldName.class);
        if (bindFieldNameAnno != null)
            annos.add(bindFieldNameAnno);

        for (int i = 0; i < annotations.length; i++) {
            if (!annos.contains(annotations[i]))
                annos.add(annotations[i]);
        }

        //解释其他注解
        for (Annotation anno : annos) {
            if (anno == null)
                continue;
            Interpreter interpreter = anno.annotationType().getAnnotation(Interpreter.class);
            if (interpreter == null)
                throw new RuntimeException("请为" + anno.getClass().getName() + "配置@Interpreter注解,指明该注解使用的解释器类型!");
            try {
                //给定的目标class上有什么注解就创建相应的注解解释器去解释
                IAnnotationPresenter iAnnotationPresenter = interpreter.value().newInstance();
                iAnnotationPresenter.interpreter(context, target, callBack, args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void interpreter(Context context, Annotation annotation, InterpreterCallBack callBack, Object... args) {
        Interpreter interpreter = annotation.annotationType().getAnnotation(Interpreter.class);
        if (interpreter == null)
            throw new RuntimeException("请为" + annotation.getClass().getName() + "配置@Interpreter注解,指明该注解使用的解释器类型!");
        try {
            //给定的目标class上有什么注解就创建相应的注解解释器去解释
            IAnnotationPresenter iAnnotationPresenter = interpreter.value().newInstance();
            iAnnotationPresenter.interpreter(context, annotation, callBack, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Annotation findAnnotation(Annotation[] annotations, Class annoClazz) {
        for (Annotation anno : annotations) {
            if (anno.annotationType() == annoClazz)
                return anno;
        }
        return null;
    }
}
