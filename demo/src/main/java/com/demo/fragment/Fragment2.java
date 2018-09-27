package com.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.demo.R;
import com.demo.activity.MainActivity;

/**
 * Created by Administrator on 2018/9/21.
 */
@BindLayoutCreater(creater = Fragment2.Frag2Creater.class)
public class Fragment2 extends Fragment {

    private View mContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContainer= CreamUtils.bind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mContainer;
    }


    @BindView(R.layout.fragment_2)
    public static class Frag2Creater extends LayoutCreater{

    }
}
