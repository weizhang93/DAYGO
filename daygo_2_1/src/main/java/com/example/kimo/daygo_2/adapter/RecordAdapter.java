package com.example.kimo.daygo_2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.activity.PlayActivity;
import com.example.kimo.daygo_2.activity.WebActivity;
import com.example.kimo.daygo_2.model.bean.Result;
import com.example.kimo.daygo_2.model.db.RecordBean;
import com.example.kimo.daygo_2.util.TimeDifferenceUtils;
import com.example.kimo.daygo_2.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/10 0010.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private Context context;
    private List<RecordBean> results = new ArrayList<>();

    public List<RecordBean> getResults(){
        return results;
    }

    public RecordAdapter(Context context, List<RecordBean> results){
        this.context = context;
        if(results!=null){
            this.results = results;
        }
    }

    @Override
    public RecordAdapter.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecordViewHolder holder = new RecordViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        final String title = results.get(position).getTitle();
        holder.textView.setText(title);
        holder.tv_author.setText("weiZhang");
        holder.tv_time.setText("17:18");
        // 获取视频的缩略图
//        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(Utils.filePath+ File.separator +
//                "DayGo_2/video/"+title, MediaStore.Images.Thumbnails.MINI_KIND);
//        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 210, 210);

//        Bitmap bitmap = Utils.gitBitMap(title);
//        holder.draweeView.setImageBitmap(bitmap);

        String filePath = Utils.saveBitmap(title);
        Glide.with(context)
                .load(filePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.draweeView);//爽，流畅度爆表

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("wei", title);//将文件名传入PlayActivity
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class RecordViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tv_author,tv_time,tv_type;
        ImageView draweeView;
        ImageView iv_video;
        TextView textView;
        public RecordViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_author = (TextView) itemView.findViewById(R.id.part_tv_author);
            tv_time = (TextView) itemView.findViewById(R.id.part_tv_time);
            tv_type = (TextView) itemView.findViewById(R.id.part_tv_type);
            draweeView = (ImageView) itemView.findViewById(R.id.part_iv);
            iv_video = (ImageView) itemView.findViewById(R.id.part_video_iv);
            textView = (TextView) itemView.findViewById(R.id.part_tv);
        }
    }
}
