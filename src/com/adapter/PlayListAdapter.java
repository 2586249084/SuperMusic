package com.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.model.Music;
import com.utils.binding.Bind;
import com.utils.binding.ViewBinder;

import java.util.List;

import me.mrzhang.music.R;

/**
 * 本地音乐适配器
 * create by Mrzhang on 2018/12/4
 */
public class PlayListAdapter extends BaseAdapter {

    private List<Music> musicList;
    private OnMoreClickListener listener;
    private boolean isPlayList;

    public PlayListAdapter(List<Music> musicList) {
        this.musicList = musicList;
    }

    public void setOnMoreClickListener(OnMoreClickListener listener) {
        this.listener = listener;
    }

    public void setIsPlayList(boolean playList) {
        isPlayList = playList;
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
        holder.view_playing.setVisibility(View.VISIBLE);
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
