package com.huadong.musicvideoplayer.model;

import java.io.Serializable;

/**
 * Created by xl on 2017/6/19.
 */

public class VideoInfo implements Serializable {

    private static final long serialVersionUID = -7920222595800367956L;
    private String displayName;
    private String path;
    private String title;
    private String size;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
