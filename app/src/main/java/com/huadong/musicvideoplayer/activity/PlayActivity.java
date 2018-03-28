package com.huadong.musicvideoplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.huadong.musicvideoplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xl on 2017/8/3.
 */

public class PlayActivity extends AppCompatActivity {

    @BindView(R.id.video_view)
    VideoView mVideoView;
    private String videoPath;
    private String videtitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        Bundle bundle = getIntent().getExtras();
        videoPath = (String) bundle.getSerializable("videopath");
        videtitle = (String) bundle.getSerializable("videtitle");
        mVideoView.setVideoPath(videoPath);
        mVideoView.start();
    }

}
