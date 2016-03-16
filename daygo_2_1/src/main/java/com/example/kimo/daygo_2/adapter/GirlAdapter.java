package com.example.kimo.daygo_2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.activity.ImageActivity;
import com.example.kimo.daygo_2.model.bean.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlViewHolder> {

    private List<Result> results = new ArrayList<>();
    private Context context ;

    public List<Result> getResults(){
        return results;
    }

    public GirlAdapter(Context context,List<Result> girl_list){
        if(girl_list!=null){
            this.results = girl_list;
        }
        this.context = context ;
    }

    @Override
    public GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GirlViewHolder girlViewHolder = new GirlViewHolder(LayoutInflater.from(parent.getContext
                ()).inflate(R.layout.item_girl,parent,false));
        return girlViewHolder;
    }

    @Override
    public void onBindViewHolder(GirlViewHolder holder, final int position) {
        Glide.with(context)
                .load(results.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.textView.setText(results.get(position).getDesc());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("url",results.get(position).getUrl());
                intent.putExtra("desc",results.get(position).getDesc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class GirlViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public GirlViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            textView = (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
