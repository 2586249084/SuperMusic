package me.mrzhang.music.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.mrzhang.music.R;
import me.mrzhang.music.executor.NavigationMenuExecutor;
import me.mrzhang.music.utils.binding.Bind;

public class MusicActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    @Bind(R.id.drawerLayout)
    private DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.image_menu)
    private ImageView image_menu;
    @Bind(R.id.image_search)
    private ImageView image_search;
    @Bind(R.id.viewPager)
    private ViewPager viewPager;
    @Bind(R.id.text_local_music)
    private TextView text_local_music;
    @Bind(R.id.text_online_music)
    private TextView text_online_music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        image_search.setOnClickListener(this);
        image_menu.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawers();
        handler.postDelayed(() -> item.setChecked(false), 500);
        return NavigationMenuExecutor.getInstance(this).onNavigationItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.image_search:
                intentActivity(this, SearchMusicActivity.class);
                break;
        }
    }
}
