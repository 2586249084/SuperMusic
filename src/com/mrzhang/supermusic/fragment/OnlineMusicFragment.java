package com.mrzhang.supermusic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mrzhang.supermusic.utils.binding.Bind;

import com.mrzhang.supermusic.R;

/**
 * 在线音乐
 * create by Mrzhang on 2018/12/4
 */
public class OnlineMusicFragment extends BaseFragment {

    @Bind(R.id.list_online_music)
    private ListView list_online_music;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_music, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
