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
        changeFragment(context, 0, 0, fragments);
    }

    @Override
    public void changeFragment(Context context, int enterAnim, int exitAnim, FragmentInfo... fragments) {
        ULog.out("切换fragment到：" + fragments[0].mFragment.getClass().getName());
        if (!(context instanceof FragmentActivity))
            throw new RuntimeException("Activity需要继承自android.support.v4.app.FragmentActivity");
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (enterAnim != 0 || exitAnim != 0)
            ft.setCustomAnimations(enterAnim, exitAnim);
        for (IFragmentPresenter.FragmentInfo info : fragments) {
            Fragment fragmentById = fm.findFragmentById(info.viewID);
            if (info.mFragment != null && info.mFragment.isVisible())
                continue;
            if (fragmentById != null) {
                ft.detach(fragmentById);
            }
            if (info.mFragment != null) {
                ULog.out("FragmentPresenter:frag name=" + info.mFragment.getClass().getSimpleName() + ",hash=" + info.mFragment.hashCode() + ",isAdd=" + info.mFragment.isAdded() + ",isDetached=" + info.mFragment.isDetached());
                if (info.mFragment.isDetached())
                    ft.attach(info.mFragment);
                else
                    ft.add(info.viewID, info.mFragment);
            }
        }

        ft.commitAllowingStateLoss();
        fm.executePendingTransactions();
    }
}
