package com.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mrzhang.supermusic.R;
import com.utils.ScreenUtils;
import com.utils.binding.Bind;

/**
 * Created by Mrzhang on 2018/12/10
 */
public class PlayMusicFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.image_play_back)
    private ImageView image_back;
    @Bind(R.id.linear_play_content)
    private LinearLayout linear_play_content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_music, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSystemBar();
    }

    @Override
    protected void setListener() {
        image_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_play_back:
                onBackPressed();
                break;
        }
    }

    private void initSystemBar() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            int top = ScreenUtils.getStatusBasHeight();
            linear_play_content.setPadding(0, top, 0, 0);
        }
    }

    private void onBackPressed(){
        if (getActivity() != null) {
            getActivity().onBackPressed();
            image_back.setEnabled(false);
            handler.postDelayed(() -> image_back.setEnabled(true), 300);
        } else {
            throw new IllegalStateException("getActivity() back a null Object!");
        }
    }

}
