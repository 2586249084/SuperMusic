package com.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.constants.Actions;

/**
 * 音乐后台服务
 */
public class PlayService extends Service {

    private static final String TAG = "Service";

    public class PlayBinder extends Binder {
        public PlayService getService() { return PlayService.this; }
    }

    /**
     * 在该生命周期中进行一些初始化操作
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate : " + getClass().getSimpleName());

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case Actions.ACTION_STOP:
                    stop();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(action);
        context.startService(intent);
    }

    private void stop() {

    }
}
