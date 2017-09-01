package com.huadong.musicvideoplayer.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.huadong.musicvideoplayer.R;
import com.huadong.musicvideoplayer.application.AppCache;
import com.huadong.musicvideoplayer.service.PlayService;
import com.huadong.musicvideoplayer.utils.ToastUtils;


/**
 * 下载完成广播接收器
 */
public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        String title = AppCache.getDownloadList().get(id);
        if (!TextUtils.isEmpty(title)) {
            ToastUtils.show(context.getString(R.string.download_success, title));

            // 由于系统扫描音乐是异步执行，因此延迟刷新音乐列表
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanMusic();
                }
            }, 1000);
        }
    }

    private void scanMusic() {
        PlayService playService = AppCache.getPlayService();
        if (playService == null) {
            return;
        }

        playService.updateMusicList(null);
    }
}
