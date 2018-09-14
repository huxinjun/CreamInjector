package com.creaminjector.presenter.impl.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.presenter.ILayoutPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.creaminjector.utils.ReflectUtils;

/**
 * BindLayoutCreater注解解释器
 * Created by xinjun on 2017/7/29 14:33
 */
public class BindLayoutCreaterInterpreter extends AnnotationInterpreter {

    @Override
    public void interpreter(Context context, AnnotatedElement target, final InterpreterCallBack callBack, Object... args) {
        //这个注解可能会加在类上 或者字段上
        if (target.getClass() == Class.class) {
            //加在类上了
            BindLayoutCreater bindLayoutCreater = getAnnotation(target, BindLayoutCreater.class);
            final Class<? extends LayoutCreater> createrClass = bindLayoutCreater.creater();

            try {
                CreamUtils.inflate(context,createrClass, new ILayoutPresenter.InflateCallBack() {

                    @Override
                    public void onCompleted(LayoutCreater instance) {
                        if (callBack != null)
                            callBack.onCompleted(BindLayoutCreater.class, instance);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if (target.getClass() == Field.class) {
            //加在字段上了
            LayoutCreater creater = (LayoutCreater) args[0];
            Field field = (Field) args[1];
            field.setAccessible(true);
            try {
                //这个View 可能是ListView,GridView,ViewPager等
                View findViewById = (View) field.get(creater);
                BindLayoutCreater itemLayoutCreater = getAnnotation(target, BindLayoutCreater.class);
                //给配置了@BindLayoutCreater注解的视图中放入一些tag记录其子创建器的和其他需要的信息
                findViewById.setTag(LayoutCreater.TAG_LAYOUT_CRETAER_PARENT, creater);
                findViewById.setTag(LayoutCreater.TAG_LAYOUT_CRETAER_ITEM_CLASS, itemLayoutCreater.creater());

                //再给其创建请求数据的命令41
                final View finalFindViewById = findViewById;
                if (itemLayoutCreater != null && target.getAnnotation(BindFieldName.class) != null) {
                    //如果BindLayoutCreater没有配置itemLayoutCreater.requestName()，配置了BindFieldName注解，说明需要从父LayoutCreater的数据中取数据
                    creater.addDataListener(new LayoutCreater.DataListener() {
                        @Override
                        public void onDataPrepared(Object data) {
                            BindFieldName bindFieldName = target.getAnnotation(BindFieldName.class);
                            //根据BindFieldName在父LayoutCreater关联的数据中找自己需要的字段
                            Object findObj = ReflectUtils.getValueByFieldPath(data, bindFieldName.value());
                            finalFindViewById.setTag(LayoutCreater.TAG_ITEMS_DATA, findObj);
                            //数据来了,这个可能是给listview,gridview,recycleview,viewpager使用的数据
                            CreamUtils.inject(finalFindViewById, findObj);
                        }
                    });
                    return;
                }
            } catch (Exception e) {
                //出错误说明没有配置这个注解,不用管
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void interpreter(Context context, Annotation annotation,
                            InterpreterCallBack callBack, Object... args) {
        //嵌套的，ViewPager能配置BindLayoutCreaters注解
        BindLayoutCreater bindLayoutCreaterAnno = (BindLayoutCreater) annotation;
        ILayoutPresenter.CreaterInfo info = new ILayoutPresenter.CreaterInfo(bindLayoutCreaterAnno.creater());
        if (callBack != null)
            callBack.onCompleted(annotation.annotationType(), info);
    }

}
