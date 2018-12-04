package com.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.utils.binding.Bind;

import me.mrzhang.music.R;

/**
 * 本地音乐列表
 * create by Mrzhang on 2018/12/4
 */
public class LocalMusicFragment extends BaseFragment {

    @Bind(R.id.list_local_music)
    private ListView list_local_music;
    @Bind(R.id.text_is_scanning)
    private TextView text_is_scanning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_music, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
