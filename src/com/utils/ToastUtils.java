package com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtils {

    private static Context sContext;
    private static Toast mToast;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void show(int resId) {
        show(sContext.getString(resId));
    }

    @SuppressLint("ShowToast")
    public static void show(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
