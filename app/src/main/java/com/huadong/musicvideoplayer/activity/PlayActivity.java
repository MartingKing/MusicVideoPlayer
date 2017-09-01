package com.huadong.musicvideoplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.huadong.musicvideoplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by xl on 2017/8/3.
 */

public class PlayActivity extends AppCompatActivity {

    @BindView(R.id.videoView)
    VideoView videoView;
    private String videoPath;
    private String videtitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        initview();
        playfunction();
    }

    private void initview() {
        Bundle bundle = getIntent().getExtras();
        videoPath = (String) bundle.getSerializable("videopath");
        videtitle = (String) bundle.getSerializable("videtitle");
    }

    void playfunction() {
        if (videoPath.equals("")) {
            Toast.makeText(PlayActivity.this, "视频路径错误", Toast.LENGTH_SHORT).show();
        } else {
            videoView.setVideoPath(videoPath);
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }
    }
}
