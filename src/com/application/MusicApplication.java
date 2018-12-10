package com.application;

import android.app.Application;
import android.content.Intent;

import com.service.PlayService;
import com.storage.database.DataBaseManager;

/**
 * 自定义Application
 * create by Mrzhang on 2018/12/4
 */
public class MusicApplication extends Application {

    /**
     * 再该生命周期中执行初始化操作
     */
    @Override
    public void onCreate() {
        super.onCreate();
        AppCache.get().init(this);
        DataBaseManager.get().init(this);

        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }
}
