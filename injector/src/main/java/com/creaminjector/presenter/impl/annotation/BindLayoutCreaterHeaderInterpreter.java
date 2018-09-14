package com.creaminjector.presenter.impl.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.creaminjector.annotation.creater.BindLayoutCreaterHeader;
import com.creaminjector.presenter.ILayoutPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;

public class BindLayoutCreaterHeaderInterpreter extends AnnotationInterpreter {

    @Override
    public void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args) {
        LayoutCreater creater = (LayoutCreater) args[0];
        Field field = (Field) args[1];
        field.setAccessible(true);
        try {
            View findViewById = (View) field.get(creater);
            BindLayoutCreaterHeader bindHeaderLayoutCreater = getAnnotation(target, BindLayoutCreaterHeader.class);
            Class<? extends LayoutCreater> headerCreater = bindHeaderLayoutCreater.creater();
            //这个视图肯定是一个list,给他配置了一个header
            if (!(findViewById instanceof ListView))
                return;
            final ListView lv_content = (ListView) findViewById;
            lv_content.setTag(LayoutCreater.TAG_LAYOUT_CRETAER_HEADER_CLASS, bindHeaderLayoutCreater.creater());


            CreamUtils.inflate(context, headerCreater, new ILayoutPresenter.InflateCallBack() {

                @Override
                public void onCompleted(LayoutCreater instance) {
                    //Header的LayoutCreater创建成功
                    lv_content.setTag(LayoutCreater.TAG_LAYOUT_CRETAER_HEADER, instance);
                    lv_content.addHeaderView(instance.getContentView());
//					lv_content.setAdapter(new BaseAdapter() {
//						@Override
//						public View getView(int position, View convertView, ViewGroup parent) {
//							return null;
//						}
//
//						@Override
//						public long getItemId(int position) {
//							return 0;
//						}
//
//						@Override
//						public Object getItem(int position) {
//							return null;
//						}
//
//						@Override
//						public int getCount() {
//							return 0;
//						}
//					});

                }
            });


        } catch (Exception e) {
            //出错误说明没有配置这个注解,不用管
        }
    }

    @Override
    public void interpreter(Context context, Annotation annotation,
                            InterpreterCallBack callBack, Object... args) {
        // TODO Auto-generated method stub

    }

}
