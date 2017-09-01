package com.huadong.musicvideoplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huadong.musicvideoplayer.R;
import com.huadong.musicvideoplayer.activity.VideoSearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xl on 2017/8/3.
 * 本地视频列表
 */

public class MyVideoFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.mLLLocalVideo)
    LinearLayout mLLLocalVideo;
    @BindView(R.id.mLLNetVideo)
    LinearLayout mLLNetVideo;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLLLocalVideo:
                startActivity(new Intent(getActivity(), VideoSearchActivity.class));
                break;
            case R.id.mLLNetVideo:
                Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void init() {
        mLLLocalVideo.setOnClickListener(this);
        mLLNetVideo.setOnClickListener(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
