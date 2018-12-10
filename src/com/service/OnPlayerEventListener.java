package com.service;

import com.model.Music;

/**
 * 播放进度监听器
 * Created by Mrzhang on 2018/12/10
 */
public interface OnPlayerEventListener {

    /**
     * 切换音乐
     * @param music 音乐
     */
    void onChange(Music music);

    /**
     * 继续播放
     */
    void onPlayerStart();

    /**
     * 暂停播放
     */
    void onPlayerPause();

    /**
     * 更新进度
     * @param progress 进度
     */
    void onPublish(int progress);

    /**
     * 缓冲百分比
     * @param percent 百分比
     */
    void onBufferingUpdate(int percent);

}
