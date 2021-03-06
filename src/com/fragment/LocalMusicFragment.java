package com.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.adapter.OnMoreClickListener;
import com.adapter.PlayListAdapter;
import com.application.AppCache;
import com.constants.RxBusTags;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.model.Music;
import com.service.AudioPlayer;
import com.utils.MusicUtils;
import com.utils.PermissionReq;
import com.utils.ToastUtils;
import com.utils.binding.Bind;

import java.util.List;

import com.mrzhang.supermusic.R;

/**
 * 本地音乐列表
 * create by Mrzhang on 2018/12/4
 */
public class LocalMusicFragment extends BaseFragment implements OnMoreClickListener,
        AdapterView.OnItemClickListener {

    @Bind(R.id.list_local_music)
    private ListView list_local_music;
    @Bind(R.id.text_is_scanning)
    private TextView text_is_scanning;

    private PlayListAdapter adapter;
    private static final String TAG = "LocalMusicFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_music, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new PlayListAdapter(AppCache.get().getLocalMusicList());
        adapter.setOnMoreClickListener(this);
        list_local_music.setAdapter(adapter);
        if (AppCache.get().getLocalMusicList().isEmpty()) {
            scanMusic(null);
        }
    }

    @SuppressLint("InlinedApi")
    @Subscribe(tags = {@Tag(RxBusTags.SCAN_MUSIC)})
    public void scanMusic(Object object) {
        list_local_music.setVisibility(View.GONE);
        text_is_scanning.setVisibility(View.VISIBLE);
        PermissionReq.with(this).permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .result(new PermissionReq.Result() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onGranted() {
                        new AsyncTask<Void, Void, List<Music>>() {
                            @Override
                            protected List<Music> doInBackground(Void... params) {
                                return MusicUtils.scanMusic(requireContext());
                            }

                            @Override
                            protected void onPostExecute(List<Music> music) {
                                AppCache.get().getLocalMusicList().clear();
                                AppCache.get().getLocalMusicList().addAll(music);
                                list_local_music.setVisibility(View.VISIBLE);
                                text_is_scanning.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }
                        }.execute();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.show(R.string.no_permission_storage);
                        list_local_music.setVisibility(View.VISIBLE);
                        text_is_scanning.setVisibility(View.GONE);
                    }
                }).request();
    }

    @Override
    protected void setListener() {
        list_local_music.setOnItemClickListener(this);
    }

    @Override
    public void onMoreClick(int position) {
        ToastUtils.show("该功能还未完善!");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Music music = AppCache.get().getLocalMusicList().get(position);
        AudioPlayer.get().addAndPlay(music);
        ToastUtils.show("已添加至播放列表");
        Log.i(TAG, music.toString());
    }
}
