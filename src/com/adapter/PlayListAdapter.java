package com.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.model.Music;
import com.service.AudioPlayer;
import com.utils.CoverLoader;
import com.utils.FileUtils;
import com.utils.binding.Bind;
import com.utils.binding.ViewBinder;

import java.util.List;

import com.mrzhang.supermusic.R;

/**
 * 本地音乐适配器
 * create by Mrzhang on 2018/12/4
 */
public class PlayListAdapter extends BaseAdapter {

    private List<Music> musicList;
    private com.adapter.OnMoreClickListener listener;
    private boolean isPlayList;

    public PlayListAdapter(List<Music> musicList) {
        this.musicList = musicList;
    }

    public void setOnMoreClickListener(com.adapter.OnMoreClickListener listener) {
        this.listener = listener;
    }

    public void setIsPlayList(boolean playList) {
        isPlayList = playList;
    }

    private boolean isShowDivider(int position) {
        return position != musicList.size() - 1;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_music_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.view_playing.setVisibility(position == AudioPlayer.get().getPlayPosition() ? View.VISIBLE : View.INVISIBLE);
        Music music = musicList.get(position);
        Bitmap cover = CoverLoader.get().loadThumb(music);
        holder.image_cover.setImageBitmap(cover);
        holder.text_title.setText(music.getTitle());
        String artist = FileUtils.getArtistAndAlbum(music.getArtist(), music.getAlbum());
        holder.text_artist.setText(artist);
        holder.image_more.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMoreClick(position);
            }
        });
        holder.view_divider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.INVISIBLE);
        return convertView;
    }

    private static class ViewHolder {
        @Bind(R.id.view_playing)
        private View view_playing;
        @Bind(R.id.image_cover)
        private ImageView image_cover;
        @Bind(R.id.text_title)
        private TextView text_title;
        @Bind(R.id.text_artist)
        private TextView text_artist;
        @Bind(R.id.image_more)
        private ImageView image_more;
        @Bind(R.id.view_divider)
        private View view_divider;

        public ViewHolder(View view) {
            ViewBinder.bind(this, view);
        }
    }
}
