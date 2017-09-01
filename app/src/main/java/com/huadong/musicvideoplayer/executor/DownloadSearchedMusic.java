package com.huadong.musicvideoplayer.executor;

import android.app.Activity;
import android.text.TextUtils;

import com.huadong.musicvideoplayer.http.HttpCallback;
import com.huadong.musicvideoplayer.http.HttpClient;
import com.huadong.musicvideoplayer.model.DownloadInfo;
import com.huadong.musicvideoplayer.model.Lrc;
import com.huadong.musicvideoplayer.model.SearchMusic;
import com.huadong.musicvideoplayer.utils.FileUtils;

import java.io.File;

/**
 * 下载搜索的音乐
 */
public abstract class DownloadSearchedMusic extends DownloadMusic {
    private SearchMusic.Song mSong;

    public DownloadSearchedMusic(Activity activity, SearchMusic.Song song) {
        super(activity);
        mSong = song;
    }

    @Override
    protected void download() {
        final String artist = mSong.getArtistname();
        final String title = mSong.getSongname();

        // 获取歌曲下载链接
        HttpClient.getMusicDownloadInfo(mSong.getSongid(), new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null || response.getBitrate() == null) {
                    onFail(null);
                    return;
                }

                downloadMusic(response.getBitrate().getFile_link(), artist, title);
                onExecuteSuccess(null);
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });

        // 下载歌词
        String lrcFileName = FileUtils.getLrcFileName(artist, title);
        File lrcFile = new File(FileUtils.getLrcDir() + lrcFileName);
        if (!lrcFile.exists()) {
            downloadLrc(mSong.getSongid(), lrcFileName);
        }
    }

    private void downloadLrc(String songId, final String fileName) {
        HttpClient.getLrc(songId, new HttpCallback<Lrc>() {
            @Override
            public void onSuccess(Lrc response) {
                if (response == null || TextUtils.isEmpty(response.getLrcContent())) {
                    return;
                }

                String filePath = FileUtils.getLrcDir() + fileName;
                FileUtils.saveLrcFile(filePath, response.getLrcContent());
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }
}
