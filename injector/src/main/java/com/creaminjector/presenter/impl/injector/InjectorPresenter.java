package com.creaminjector.presenter.impl.injector;

import android.view.View;

import com.creaminjector.presenter.IInjectionPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;

public class InjectorPresenter implements IInjectionPresenter {

    @Override
    public void inject(View target, Object value) {
        if (target == null)
            return;
        //配置了@Injector注解后view的tag下会记录其使用的注入器类型
        Class<IInjectionPresenter> injectorClass = (Class<IInjectionPresenter>) target.getTag(LayoutCreater.TAG_INJECTOR_CLASS);

        IInjectionPresenter injector = null;
        try {
            //木有配置Injector注解
            if (injectorClass == null)
                //使用默认的数据注入器
                injector = DEFAULT_INJECTOR.get(target.getClass()).newInstance();
            else
                injector = injectorClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        injector.inject(target, value);
    }

}
