package com.storage.database;

import android.content.Context;

import com.storage.greendao.DaoMaster;
import com.storage.greendao.DaoSession;
import com.storage.greendao.MusicDao;

import org.greenrobot.greendao.database.Database;

/**
 * create by Mrzhang on 2018/12/5
 */
public class DataBaseManager {

    private static final String DB_NAME = "database";
    private MusicDao musicDao;

    private DataBaseManager() {
    }

    public static DataBaseManager get() {
        return SingleHolder.instance;
    }

    private static class SingleHolder {
        private static DataBaseManager instance = new DataBaseManager();
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database database = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(database).newSession();
        musicDao = daoSession.getMusicDao();
    }

    public MusicDao getMusicDao() {
        return musicDao;
    }

}
