package com.huadong.musicvideoplayer.executor;

import android.app.Activity;
import android.text.TextUtils;

import com.huadong.musicvideoplayer.http.HttpCallback;
import com.huadong.musicvideoplayer.http.HttpClient;
import com.huadong.musicvideoplayer.model.DownloadInfo;
import com.huadong.musicvideoplayer.model.Lrc;
import com.huadong.musicvideoplayer.model.Music;
import com.huadong.musicvideoplayer.model.SearchMusic;
import com.huadong.musicvideoplayer.utils.FileUtils;

import java.io.File;


/**
 * 播放搜索的音乐
 */
public abstract class PlaySearchedMusic extends PlayMusic {
    private SearchMusic.Song mSong;

    public PlaySearchedMusic(Activity activity, SearchMusic.Song song) {
        super(activity, 2);
        mSong = song;
    }

    @Override
    protected void getPlayInfo() {
        String lrcFileName = FileUtils.getLrcFileName(mSong.getArtistname(), mSong.getSongname());
        File lrcFile = new File(FileUtils.getLrcDir() + lrcFileName);
        if (!lrcFile.exists()) {
            downloadLrc(lrcFile.getPath());
        } else {
            mCounter++;
        }

        music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setTitle(mSong.getSongname());
        music.setArtist(mSong.getArtistname());

        // 获取歌曲播放链接
        HttpClient.getMusicDownloadInfo(mSong.getSongid(), new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null || response.getBitrate() == null) {
                    onFail(null);
                    return;
                }

                music.setPath(response.getBitrate().getFile_link());
                music.setDuration(response.getBitrate().getFile_duration() * 1000);
                checkCounter();
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });
    }

    private void downloadLrc(final String filePath) {
        HttpClient.getLrc(mSong.getSongid(), new HttpCallback<Lrc>() {
            @Override
            public void onSuccess(Lrc response) {
                if (response == null || TextUtils.isEmpty(response.getLrcContent())) {
                    return;
                }

                FileUtils.saveLrcFile(filePath, response.getLrcContent());
            }

            @Override
            public void onFail(Exception e) {
            }

            @Override
            public void onFinish() {
                checkCounter();
            }
        });
    }
}
