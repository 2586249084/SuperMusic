package com.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragment.PlayMusicFragment;
import com.mrzhang.supermusic.R;

import com.adapter.FragmentAdapter;
import com.constants.Keys;
import com.executor.NavigationMenuExecutor;
import com.fragment.LocalMusicFragment;
import com.fragment.OnlineMusicFragment;
import com.utils.binding.Bind;

public class MusicActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        ViewPager.OnPageChangeListener {

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
    @Bind(R.id.frame_play_bar)
    private FrameLayout frame_play_bar;

    private LocalMusicFragment mLocalMusicFragment;
    private OnlineMusicFragment mOnlineMusicFragment;
    private PlayMusicFragment mPlayMusicFragment;
    private boolean isPlayFragmentShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
    }

    @Override
    protected void onServiceBound() {
        setupView();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawers();
        handler.postDelayed(() -> item.setChecked(false), 500);
        return NavigationMenuExecutor.getInstance(this).onNavigationItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.image_search:
                intentActivity(this, com.activity.SearchMusicActivity.class);
                break;
            case R.id.text_local_music:
                viewPager.setCurrentItem(0);
                break;
            case R.id.text_online_music:
                viewPager.setCurrentItem(1);
                break;
            case R.id.frame_play_bar:
                showPlayFragment();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            text_local_music.setSelected(true);
            text_online_music.setSelected(false);
        } else {
            text_local_music.setSelected(false);
            text_online_music.setSelected(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void setupView() {
        // setup view pager
        mLocalMusicFragment = new LocalMusicFragment();
        mOnlineMusicFragment = new OnlineMusicFragment();
        FragmentAdapter adapter = new FragmentAdapter(this.getSupportFragmentManager());
        adapter.addFragment(mLocalMusicFragment);
        adapter.addFragment(mOnlineMusicFragment);
        viewPager.setAdapter(adapter);
        text_local_music.setSelected(true);

        image_search.setOnClickListener(this);
        image_menu.setOnClickListener(this);
        frame_play_bar.setOnClickListener(this);
        text_local_music.setOnClickListener(this);
        text_online_music.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Keys.VIEW_PAGER_INDEX, viewPager.getCurrentItem());
        mLocalMusicFragment.onSaveInstanceState(outState);
        mOnlineMusicFragment.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        viewPager.post(() -> {
            viewPager.setCurrentItem(savedInstanceState.getInt(Keys.VIEW_PAGER_INDEX), false);
            mLocalMusicFragment.onSaveInstanceState(savedInstanceState);
            mOnlineMusicFragment.onSaveInstanceState(savedInstanceState);
        });
    }

    @Override
    public void onBackPressed() {
        if (mPlayMusicFragment != null && isPlayFragmentShow) {
            hidePlayFragment();
            return;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        moveTaskToBack(true);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mPlayMusicFragment != null && isPlayFragmentShow) {
                    hidePlayFragment();
                    return false;
                }
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                    return false;
                }
                moveTaskToBack(true);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    private void showPlayFragment() {
        if (isPlayFragmentShow) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayMusicFragment == null) {
            mPlayMusicFragment = new PlayMusicFragment();
            ft.replace(android.R.id.content, mPlayMusicFragment);
        } else {
            ft.show(mPlayMusicFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }

    private void hidePlayFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayMusicFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }

}
