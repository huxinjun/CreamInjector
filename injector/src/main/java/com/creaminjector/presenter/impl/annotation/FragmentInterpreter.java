package com.creaminjector.presenter.impl.annotation;

import android.content.Context;
import android.view.View;

import com.creaminjector.annotation.Fragment;
import com.creaminjector.presenter.IFragmentPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

public class FragmentInterpreter extends AnnotationInterpreter {


    @Override
    public void interpreter(Context context, AnnotatedElement target, InterpreterCallBack callBack, Object... args) {
        if (target.getClass() == Field.class) {
            @SuppressWarnings("unused")
            LayoutCreater creater = (LayoutCreater) args[0];
            int viewID = 0;

            Field targetField = (Field) args[1];
            Fragment annotation = targetField.getAnnotation(Fragment.class);
            Class clazz = annotation.clazz();
            try {
                View view = (View) targetField.get(creater);
                viewID = view.getId();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            Fragment fragment = getAnnotation(target, Fragment.class);
            IFragmentPresenter.FragmentInfo fInfo = new IFragmentPresenter.FragmentInfo(viewID, fragment.clazz());
            //切换到此fragment
            CreamUtils.changeFragment(context,fInfo);
        }


    }


    @Override
    public void interpreter(Context context,Annotation annotation, InterpreterCallBack callBack, Object... args) {
        Fragment fragment = (Fragment) annotation;
        IFragmentPresenter.FragmentInfo fInfo = new IFragmentPresenter.FragmentInfo(fragment.containerId(), fragment.clazz());
        callBack.onCompleted(annotation.getClass(), fInfo);
    }

}