package com.creaminjector.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.presenter.IAnnotationPresenter;
import com.creaminjector.presenter.IFragmentPresenter;
import com.creaminjector.presenter.ILayoutPresenter;
import com.creaminjector.presenter.impl.annotation.AnnotationPresenter;
import com.creaminjector.presenter.impl.fragment.FragmentPresenter;
import com.creaminjector.presenter.impl.injector.InjectorPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.presenter.impl.layout.LayoutPresenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 核心工具
 * Created by xinjun on 2018/9/14.
 */

public class CreamUtils {


    public static void inject(View target, Object value) {
        new InjectorPresenter().inject(target, value);
    }

    public static void interpreter(Context context, AnnotatedElement target, IAnnotationPresenter.InterpreterCallBack callBack, Object... args) {
        new AnnotationPresenter().interpreter(context, target, callBack, args);
    }

    public static void interpreter(Context context, Annotation annotation, IAnnotationPresenter.InterpreterCallBack callBack, Object... args) {
        new AnnotationPresenter().interpreter(context, annotation, callBack, args);
    }

    public static void changeFragment(Context context, IFragmentPresenter.FragmentInfo... fragments) {
        new FragmentPresenter().changeFragment(context, fragments);
    }

    public static void inflate(Context context, Class<? extends LayoutCreater> createrClass, ILayoutPresenter.InflateCallBack callBack) {
        new LayoutPresenter().inflate(context, createrClass, callBack);
    }


    public static View bind(Object target) {
        if (target == null)
            return null;
        View genericView = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            genericView = getCreater(activity, target.getClass()).getContentView();
            activity.setContentView(genericView);
        }
        if (target instanceof Fragment) {
            Fragment fragment = (Fragment) target;
            genericView = getCreater(fragment.getContext(), target.getClass()).getContentView();
        }
        if (target instanceof Dialog) {
            Dialog dialog = (Dialog) target;
            genericView = getCreater(dialog.getContext(), target.getClass()).getContentView();
            dialog.setContentView(genericView);
        }
        return genericView;
    }

    private static LayoutCreater getCreater(Context context, Class clazz) {
        final LayoutCreater[] mLayoutCreater = new LayoutCreater[1];
        CreamUtils.interpreter(context, clazz, new IAnnotationPresenter.InterpreterCallBack() {
            @Override
            public void onCompleted(Class<? extends Annotation> anno, Object... results) {
                if (anno == BindLayoutCreater.class) {
                    mLayoutCreater[0] = (LayoutCreater) results[0];
                }
            }
        });
        return mLayoutCreater[0];
    }
}
