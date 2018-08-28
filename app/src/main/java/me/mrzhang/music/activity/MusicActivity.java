package me.mrzhang.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import me.mrzhang.music.R;
import me.mrzhang.music.utils.binding.ViewBinder;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ViewBinder.bind(this);
    }

}
