package com.demo.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/9/30.
 */

public class DisplayUtil {


    public static int getScreenWidth(Context context) {
        if (context == null)
            return 0;
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        WindowManager mWindowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }
}
