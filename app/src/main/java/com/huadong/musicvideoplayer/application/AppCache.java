package com.huadong.musicvideoplayer.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.util.DisplayMetrics;
import android.util.Log;

import com.amap.api.location.AMapLocalWeatherLive;
import com.huadong.musicvideoplayer.model.Music;
import com.huadong.musicvideoplayer.model.SongListInfo;
import com.huadong.musicvideoplayer.service.PlayService;
import com.huadong.musicvideoplayer.utils.Preferences;
import com.huadong.musicvideoplayer.utils.ScreenUtils;
import com.huadong.musicvideoplayer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;



public class AppCache {
    private Context mContext;
    private PlayService mPlayService;
    // 本地歌曲列表
    private final List<Music> mMusicList = new ArrayList<>();
    // 歌单列表
    private final List<SongListInfo> mSongListInfos = new ArrayList<>();
    private final List<Activity> mActivityStack = new ArrayList<>();
    private final LongSparseArray<String> mDownloadList = new LongSparseArray<>();
    private AMapLocalWeatherLive mAMapLocalWeatherLive;

    private AppCache() {
    }

    private static class SingletonHolder {
        private static AppCache sAppCache = new AppCache();
    }

    private static AppCache getInstance() {
        return SingletonHolder.sAppCache;
    }

    public static void init(Application application) {
        getInstance().onInit(application);
    }

    private void onInit(Application application) {
        mContext = application.getApplicationContext();
        ToastUtils.init(mContext);
        Preferences.init(mContext);
        ScreenUtils.init(mContext);
        CrashHandler.getInstance().init();
        application.registerActivityLifecycleCallbacks(new ActivityLifecycle());
    }

    public static Context getContext() {
        return getInstance().mContext;
    }

    public static PlayService getPlayService() {
        return getInstance().mPlayService;
    }

    public static void setPlayService(PlayService service) {
        getInstance().mPlayService = service;
    }

    public static void updateNightMode(boolean on) {
        Resources resources = getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        config.uiMode |= on ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        resources.updateConfiguration(config, dm);
    }

    public static List<Music> getMusicList() {
        return getInstance().mMusicList;
    }

    public static List<SongListInfo> getSongListInfos() {
        return getInstance().mSongListInfos;
    }

    public static void clearStack() {
        List<Activity> activityStack = getInstance().mActivityStack;
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    public static LongSparseArray<String> getDownloadList() {
        return getInstance().mDownloadList;
    }

    public static AMapLocalWeatherLive getAMapLocalWeatherLive() {
        return getInstance().mAMapLocalWeatherLive;
    }

    public static void setAMapLocalWeatherLive(AMapLocalWeatherLive aMapLocalWeatherLive) {
        getInstance().mAMapLocalWeatherLive = aMapLocalWeatherLive;
    }

    private static class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
        private static final String TAG = "Activity";

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(TAG, "onCreate: " + activity.getClass().getSimpleName());
            getInstance().mActivityStack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onDestroy: " + activity.getClass().getSimpleName());
            getInstance().mActivityStack.remove(activity);
        }
    }
}
