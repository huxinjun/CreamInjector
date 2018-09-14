package com.creaminjector.presenter.impl.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.creaminjector.utils.ULog;
import com.creaminjector.presenter.IFragmentPresenter;

import java.util.List;

/**
 * Created by xinjun on 2018/9/14.
 */

public class FragmentPresenter implements IFragmentPresenter {


    @Override
    public void changeFragment(Context context, FragmentInfo... fragments) {
        ULog.out("切换fragment到：" + fragments[0].clazz.getName());
        if(!(context instanceof FragmentActivity))
            throw new RuntimeException("Activity需要继承自android.support.v4.app.FragmentActivity");
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (IFragmentPresenter.FragmentInfo info : fragments) {
            boolean attached = false;
            @SuppressLint("RestrictedApi") List<Fragment> allFragment = fm.getFragments();
            if (allFragment != null)
                for (Fragment fragment : allFragment) {
                    if (info.clazz == fragment.getClass()) {
                        attached = true;
                        ft.attach(fragment);
                    } else
                        ft.detach(fragment);
                }
            if (!attached) {
                try {
                    ft.add(info.viewID, (Fragment) info.clazz.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ft.commitAllowingStateLoss();
    }
}
