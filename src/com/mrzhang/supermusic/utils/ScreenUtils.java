package com.mrzhang.supermusic.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * 屏幕相关工具类
 * create by Mrzhang on 2018/12/5
 */
public class ScreenUtils {

    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        WindowManager manager = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBasHeight() {
        int result = 0;
        int resourceId = sContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = sContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dp2px(float dpValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = sContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
