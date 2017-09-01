package com.huadong.musicvideoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huadong.musicvideoplayer.R;
import com.huadong.musicvideoplayer.model.VideoInfo;

import java.io.File;
import java.util.List;

/**
 * Created by xl on 2017/6/20.
 */

public class VideoSearchAdapter extends BaseAdapter {

    private Context context;
    private List<VideoInfo> mDatas;

    public VideoSearchAdapter(Context context, List<VideoInfo> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.video_des);
            holder.videoView = (ImageView) convertView.findViewById(R.id.video_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.videoView.setImageBitmap(null);
        }
        final String path = mDatas.get(position).getPath();
        String picname = mDatas.get(position).getDisplayName();

        Glide.with(context).load(new File(path)).into(holder.videoView);

        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.title.setText(picname);
        return convertView;
    }

    /*存放控件 的ViewHolder*/
    public final class ViewHolder {
        TextView title;
        ImageView videoView;
        LinearLayout item_video;
    }
}
