package com.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.enums.PlayModeEnum;
import com.model.Music;
import com.storage.database.DataBaseManager;
import com.storage.preference.Preferences;
import com.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Mrzhang on 2018/12/10
 */
public class AudioPlayer {

    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_PAUSE = 3;
    private static final long TIME_UPDATE = 300L;

    private Context context;
    private MediaPlayer mediaPlayer;
    private List<Music> musicList;
    private Handler handler;
    private final List<OnPlayerEventListener> listeners = new ArrayList<>();
    private int state = STATE_IDLE;
    private static final String TAG = "AudioPlayer";

    private AudioPlayer() {
    }

    public static AudioPlayer get() {
        return SingleHolder.instance;
    }

    private static class SingleHolder {
        private static AudioPlayer instance = new AudioPlayer();
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
        mediaPlayer = new MediaPlayer();
        musicList = DataBaseManager.get().getMusicDao().queryBuilder().build().list();
        handler = new Handler(Looper.getMainLooper());
        mediaPlayer.setOnCompletionListener(mp -> next());
        mediaPlayer.setOnPreparedListener(mp -> {
            if (isPreparing()) {
                startPlayer();
            }
        });
        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {
            for (OnPlayerEventListener listener : listeners) {
                listener.onBufferingUpdate(percent);
            }
        });
    }

    public void addOnPlayEventListener(OnPlayerEventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeOnPlayEventListener(OnPlayerEventListener listener) {
        listeners.remove(listener);
    }

    public void addAndPlay(Music music) {
        int position = musicList.indexOf(music);
        Log.i(TAG, Arrays.toString(musicList.toArray()));
        Log.i(TAG, position + "");
        if (position < 0) {
            musicList.add(music);
            DataBaseManager.get().getMusicDao().insert(music);
            position = musicList.size() - 1;
        }
        play(position);
    }

    public void play(int position) {
        if (musicList.isEmpty()) {
            return;
        }

        if (position < 0) {
            position = musicList.size() - 1;
        } else if (position >= musicList.size()) {
            position = 0;
        }
        setPlayPosition(position);
        Music music = getPlayMusic();
        state = STATE_PREPARING;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getPath());
            mediaPlayer.prepareAsync();
            state = STATE_PREPARING;
            for (OnPlayerEventListener listener : listeners) {
                listener.onChange(music);
            }
            MediaSessionManager.get().updateMetaData(music);
            MediaSessionManager.get().updatePlaybackState();
        } catch (IOException exception) {
            exception.printStackTrace();
            ToastUtils.show("当前歌曲无法播放!");
        }
    }

    public void delete(int position) {
        int playPosition = getPlayPosition();
        Music music = musicList.remove(position);
        DataBaseManager.get().getMusicDao().delete(music);
        if (playPosition > position) {
            setPlayPosition(playPosition - 1);
        } else if (playPosition == position) {
            if (isPlaying() || isPreparing()) {
                setPlayPosition(playPosition - 1);
                next();
            } else {
                stopPlayer();
                for (OnPlayerEventListener listener : listeners) {
                    listener.onChange(music);
                }
            }
        }
    }

    public void playPause() {
        if (isPreparing()) {
            stopPlayer();
        } else if (isPlaying()) {
            pausePlayer();
        } else if (isPausing()) {
            startPlayer();
        } else {
            play(getPlayPosition());
        }
    }

    private void startPlayer() {
        if (!isPreparing() && !isPausing()) {
            return;
        }

        mediaPlayer.start();
        state = STATE_PLAYING;
        handler.post(mPublishRunnable);
        MediaSessionManager.get().updatePlaybackState();
        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerStart();
        }
    }

    private void pausePlayer() {
        pausePlayer(true);
    }

    private void pausePlayer(boolean abandonAudioFocus) {
        if (!isPlaying()) {
            return;
        }

        mediaPlayer.pause();
        state = STATE_PAUSE;
        handler.removeCallbacks(mPublishRunnable);
        MediaSessionManager.get().updatePlaybackState();
        if (abandonAudioFocus) {

        }

        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerPause();
        }
    }

    public void stopPlayer() {
        if (isIdle()) {
            return;
        }

        pausePlayer();
        mediaPlayer.reset();
        state = STATE_IDLE;
    }

    public void next() {
        if (musicList.isEmpty()) {
            return;
        }

        PlayModeEnum mode = PlayModeEnum.valueOf(Preferences.getPlayMode());
        switch (mode) {
            case SHUFFLE:
                play(new Random().nextInt(musicList.size()));
                break;
            case SINGLE:
                play(getPlayPosition());
                break;
            case LOOP:
            default:
                play(getPlayPosition() + 1);
        }
    }

    public void prev() {
        if (musicList.isEmpty()) {
            return;
        }

        PlayModeEnum mode = PlayModeEnum.valueOf(Preferences.getPlayMode());
        switch (mode) {
            case SHUFFLE:
                play(new Random().nextInt(musicList.size()));
                break;
            case SINGLE:
                play(getPlayPosition());
                break;
            case LOOP:
            default:
                play(getPlayPosition() - 1);
        }
    }

    /**
     * 跳转到指定时间位置
     * @param time 时间
     */
    public void seekTo(int time) {
        if (isPlaying() || isPausing()) {
            mediaPlayer.seekTo(time);
            MediaSessionManager.get().updatePlaybackState();
            for (OnPlayerEventListener listener : listeners) {
                listener.onPublish(time);
            }
        }
    }

    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    public long getAudioPosition() {
        if (isPlaying() || isPausing()) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public Music getPlayMusic() {
        if (musicList.isEmpty()) {
            return null;
        }
        return musicList.get(getPlayPosition());
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public boolean isPlaying() {
        return state == STATE_PLAYING;
    }

    public boolean isPausing() {
        return state == STATE_PAUSE;
    }

    public boolean isPreparing() {
        return state == STATE_PREPARING;
    }

    public boolean isIdle() {
        return state == STATE_IDLE;
    }

    public int getPlayPosition() {
        int position = Preferences.getPlayPosition();
        if (position < 0 || position >= musicList.size()) {
            position = 0;
            Preferences.savePlayPosition(position);
        }
        return position;
    }

    private void setPlayPosition(int position) {
        Preferences.savePlayPosition(position);
    }

    private Runnable mPublishRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying()) {
                for (OnPlayerEventListener listener : listeners) {
                    listener.onPublish(mediaPlayer.getCurrentPosition());
                }
            }
            handler.postDelayed(this, TIME_UPDATE);
        }
    };

}
