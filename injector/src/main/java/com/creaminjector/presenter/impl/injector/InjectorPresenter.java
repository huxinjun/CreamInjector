package com.creaminjector.presenter.impl.injector;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.creaminjector.presenter.IInjectionPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.ULog;

import java.util.Iterator;
import java.util.Set;

public class InjectorPresenter implements IInjectionPresenter {

    @Override
    public void inject(View target, Object value) {
        if (target == null)
            return;
        //配置了@Injector注解后view的tag下会记录其使用的注入器类型
        Class<? extends IInjectionPresenter> injectorClass = (Class<? extends IInjectionPresenter>) target.getTag(LayoutCreater.TAG_INJECTOR_CLASS);

        IInjectionPresenter injector = null;
        try {
            //木有配置Injector注解
            if (injectorClass == null)
                //使用默认的数据注入器
                injector = findDefaultInjectClass(target);
            else
                injector = injectorClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("无法实例化在:" + target.getClass().getSimpleName() + "上配置的注入解释器");
        }
        injector.inject(target, value);
    }

    private IInjectionPresenter findDefaultInjectClass(View view) {
        IInjectionPresenter injectionPresenter = null;
        if (view instanceof TextView)
            injectionPresenter = new TextInjectorPresenter();
        else if (view instanceof ImageView)
            injectionPresenter = new ImageInjectorPresenter();
        else if (view instanceof Button)
            injectionPresenter = new ButtonInjectorPresenter();
        else if (view instanceof CheckBox)
            injectionPresenter = new CheckBoxInjectorPresenter();
        else if (view instanceof RadioButton)
            injectionPresenter = new RadioButtonInjectorPresenter();
        else if (view instanceof AdapterView)
            injectionPresenter = new AdapterInjectorPresenter();
        else if (view instanceof ViewPager)
            injectionPresenter = new PagerInjectorPresenter();
        else if (view instanceof RecyclerView)
            injectionPresenter = new RecyclerInjectorPresenter();

        ULog.out("view:" + view.getClass().getSimpleName() + "--->injector:" + injectionPresenter.getClass().getSimpleName());
        return injectionPresenter;
    }

}
