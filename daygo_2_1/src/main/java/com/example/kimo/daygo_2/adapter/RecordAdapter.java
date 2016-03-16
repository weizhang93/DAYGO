package com.example.kimo.daygo_2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.activity.WebActivity;
import com.example.kimo.daygo_2.model.bean.Result;
import com.example.kimo.daygo_2.util.TimeDifferenceUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/10 0010.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ArticleViewHolder> {

    private Context context;
    private List<Result> results = new ArrayList<>();

    public List<Result> getResults(){
        return results;
    }

    public RecordAdapter(Context context, List<Result> results){
        this.context = context;
        if(results!=null){
            this.results = results;
        }
    }

    @Override
    public RecordAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleViewHolder holder = new ArticleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ArticleViewHolder holder,final int position) {
        String type = results.get(position).getType();

        holder.relativeLayout.setVisibility(View.VISIBLE);
        holder.draweeView.setVisibility(View.GONE);
        holder.iv_video.setVisibility(View.GONE);
        holder.textView.setText(results.get(position).getDesc());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", results.get(position).getUrl());
                intent.putExtra("desc", results.get(position).getDesc());
                context.startActivity(intent);
            }
        });

        Uri uri = null;
        switch (results.get(position).getType()) {
            case "Android":
                uri = Uri.parse("res:///" + R.drawable.icon_android);
                break;
            case "iOS":
                uri = Uri.parse("res:///" + R.drawable.icon_ios);
                break;
            case "前端":
                uri = Uri.parse("res:///" + R.drawable.icon_html5);
                break;
            case "拓展资源":
                uri = Uri.parse("res:///" + R.drawable.icon_html5);
                break;
            case "瞎推荐":
                uri = Uri.parse("res:///" + R.drawable.icon_resource);
                break;
            case "福利":
                uri = Uri.parse("res:///" + R.drawable.icon_img);
                break;
        }

        holder.dv_icon.setImageURI(uri);
        holder.tv_author.setText(results.get(position).getWho());
        holder.tv_author.setTextColor(Color.parseColor("#87000000"));
        String time = results.get(position).getCreatedAt();
        if (time != null) {
            holder.tv_time.setText(TimeDifferenceUtils.getTimeDifference(time));
        } else {
            holder.tv_time.setText("");
        }
        holder.tv_type.setText(type);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout relativeLayout;
        TextView tv_author,tv_time,tv_type;
        SimpleDraweeView draweeView,dv_icon;
        ImageView iv_video;
        TextView textView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.part_message);
            tv_author = (TextView) itemView.findViewById(R.id.part_tv_author);
            tv_time = (TextView) itemView.findViewById(R.id.part_tv_time);
            dv_icon = (SimpleDraweeView) itemView.findViewById(R.id.part_type_icon);
            tv_type = (TextView) itemView.findViewById(R.id.part_tv_type);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.part_iv);
            iv_video = (ImageView) itemView.findViewById(R.id.part_video_iv);
            textView = (TextView) itemView.findViewById(R.id.part_tv);
        }
    }
}
