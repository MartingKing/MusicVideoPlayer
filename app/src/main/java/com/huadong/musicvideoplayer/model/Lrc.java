package com.huadong.musicvideoplayer.model;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean
 */
public class Lrc {
    @SerializedName("lrcContent")
    private String lrcContent;

    public String getLrcContent() {
        return lrcContent;
    }

    public void setLrcContent(String lrcContent) {
        this.lrcContent = lrcContent;
    }
}
