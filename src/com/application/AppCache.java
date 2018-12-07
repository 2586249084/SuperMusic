package com.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.model.Music;
import com.storage.preference.Preferences;
import com.utils.CoverLoader;
import com.utils.ScreenUtils;
import com.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 工程缓存类
 * create by Mrzhang on 2018/12/5
 */
public class AppCache {

    private Context mContext;
    private final List<Music> mLocalMusicList = new ArrayList<>();
    private final List<Activity> mActivityStack = new ArrayList<>();

    private AppCache() {
    }

    private static class SingletonHolder {
        private static AppCache instance = new AppCache();
    }

    public static AppCache get() {
        return SingletonHolder.instance;
    }

    public void init(Application application) {
        mContext = application.getApplicationContext();
        ToastUtils.init(mContext);
        ScreenUtils.init(mContext);
        CoverLoader.get().init(mContext);
        Preferences.init(mContext);
        application.registerActivityLifecycleCallbacks(new ActivityLifecycle());
    }

    public Context getContext() {
        return mContext;
    }

    public List<Music> getLocalMusicList() {
        return mLocalMusicList;
    }

    public void clearStack() {
        List<Activity> activityStack = mActivityStack;
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    private class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

        private static final String TAG = "Activity";

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Log.i(TAG, "onCreate : " + activity.getClass().getSimpleName());
            mActivityStack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onDestory : " + activity.getClass().getSimpleName());
            mActivityStack.remove(activity);
        }

    }

}
