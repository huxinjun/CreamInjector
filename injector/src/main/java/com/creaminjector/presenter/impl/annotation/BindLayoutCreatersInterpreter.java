package com.creaminjector.presenter.impl.annotation;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.creaminjector.templete.SmartViewPagerAdapter;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindLayoutCreaters;
import com.creaminjector.presenter.ILayoutPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author xinjun
 * @BindLayoutCreaters注解解释器
 */
public class BindLayoutCreatersInterpreter extends AnnotationInterpreter {

    @Override
    public void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args) {
        final LayoutCreater creater = (LayoutCreater) args[0];
        Field field = (Field) args[1];
        field.setAccessible(true);
        if (target.getClass() == Field.class) {

            final BindLayoutCreaters annotation = getAnnotation(target, BindLayoutCreaters.class);
            BindLayoutCreater[] creaters = annotation.value();
            final ArrayList<ILayoutPresenter.CreaterInfo> createrInfos = new ArrayList<>();
            if (creaters != null && creaters.length > 0) {
                for (BindLayoutCreater anno : creaters)
                    CreamUtils.interpreter(context, anno, new InterpreterCallBack() {
                        @Override
                        public void onCompleted(Class<? extends Annotation> anno, Object... results) {
                            createrInfos.add((ILayoutPresenter.CreaterInfo) results[0]);
                        }
                    });
            }
            //解释完毕后
            try {
                ViewPager viewpager = (ViewPager) field.get(creater);
                //设置Adapter
                viewpager.setAdapter(new SmartViewPagerAdapter(context, createrInfos));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void interpreter(Context context, Annotation annotation, InterpreterCallBack callBack, Object... args) {
        //Ignore
    }

}