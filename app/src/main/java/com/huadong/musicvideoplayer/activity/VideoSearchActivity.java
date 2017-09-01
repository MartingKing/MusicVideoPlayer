package com.huadong.musicvideoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huadong.musicvideoplayer.R;
import com.huadong.musicvideoplayer.adapter.VideoSearchAdapter;
import com.huadong.musicvideoplayer.model.VideoInfo;
import com.huadong.musicvideoplayer.refresh.SwipyRefreshLayout;
import com.huadong.musicvideoplayer.threadpool.MyThreadPool;
import com.huadong.musicvideoplayer.utils.VideoSearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xl on 2017/8/3.
 * 本地视频列表
 */

public class VideoSearchActivity extends BaseActivity implements OnItemClickListener, OnItemLongClickListener, SwipyRefreshLayout.OnRefreshListener {

    private static final int SEARCH_LOCAL_VIDEO = 1;
    private static final String TAG = "DHD";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipe_refresh)
    SwipyRefreshLayout swipeRefresh;
    private MyThreadPool threadPool;
    VideoSearchAdapter mAdapter;
    private List<VideoInfo> videoInfos = new ArrayList<>();
    private int startIndex;
    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEARCH_LOCAL_VIDEO:
                    Log.d(TAG, "handleMessage: videoinfo==" + videoInfos.size());
                    if (videoInfos.size() <= 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        swipeRefresh.setRefreshing(false);
                        llLoading.setVisibility(View.GONE);
                        listView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void search() {
        llLoading.setVisibility(View.VISIBLE);
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                videoInfos = VideoSearchUtil.getVideoFile(videoInfos, Environment.getExternalStorageDirectory());
                Message message = new Message();
                message.what = SEARCH_LOCAL_VIDEO;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosearch);
        ButterKnife.bind(this);

        initSearchView();
    }

    private void initSearchView() {
        swipeRefresh.setFirstIndex(startIndex);
        threadPool = new MyThreadPool(3, 5, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>());
        search();
        mAdapter = new VideoSearchAdapter(this, videoInfos);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(VideoSearchActivity.this, PlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videopath", videoInfos.get(position).getPath());
        bundle.putSerializable("videtitle", videoInfos.get(position).getDisplayName());
        Log.d(TAG, "onItemClick: position==" + videoInfos.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                search();
                break;
            case BOTTOM_REFRESH:
                break;
        }
    }

    @Override
    public void onLoad(int index) {

    }
}
