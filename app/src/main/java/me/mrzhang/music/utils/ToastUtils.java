package me.mrzhang.music.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtils {

    private static ToastUtils instance = null;
    private Context mContext;
    private static Toast mToast;

    private ToastUtils(Context context){
        mContext = context.getApplicationContext();
    }

    public static ToastUtils init(Context context){
        if (instance == null) {
            instance = new ToastUtils(context);
        }
        return instance;
    }

    public void show(int resId){
        show(mContext.getString(resId));
    }

    @SuppressLint("ShowToast")
    private void show(String text){
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
