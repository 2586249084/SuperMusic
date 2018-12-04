package com.executor;

import android.content.Intent;
import android.view.MenuItem;
import me.mrzhang.music.R;
import com.activity.AboutActivity;
import com.activity.MusicActivity;
import com.activity.SettingActivity;
import com.utils.ToastUtils;

/**
 * 导航菜单执行器
 */
public class NavigationMenuExecutor {

    private MusicActivity activity;
    private static NavigationMenuExecutor instance = null;

    private NavigationMenuExecutor(MusicActivity activity){
        this.activity = activity;
    }

    public static NavigationMenuExecutor getInstance(MusicActivity activity){
        if (instance == null) {
            instance = new NavigationMenuExecutor(activity);
        }
        return instance;
    }

    public boolean onNavigationItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_set:
                startActivity(SettingActivity.class);
                return true;
            case R.id.action_night:
                setNightMode();
                return true;
            case R.id.action_timer:
                return true;
            case R.id.action_exit:
                return true;
            case R.id.action_about:
                startActivity(AboutActivity.class);
                return true;
        }
        return false;
    }

    private void startActivity(Class<?> clazz){
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    private void setNightMode(){
        ToastUtils.init(activity).show(R.string.no_function);
    }
}
