package com.example.kimo.daygo_2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.model.bean.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/13 0013.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Result> items = new ArrayList<>();
    private Context context ;

    public List<Result> getResults(){
        return items;
    }
    public NoteAdapter(Context context,List<Result> items){
        if(items!=null){
            this.items = items;
        }
        this.context = context;
    }

    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoteViewHolder holder = new NoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        //String info = items.get(position);
        holder.tvTitle.setText(items.get(position).getWho());
        holder.tvDesc.setText(items.get(position).getDesc());
        //手动更改高度，不同位置的高度有所不同
        //holder.textView.setHeight(100 + (position % 3) * 30);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvTitle,tvDesc;
        public NoteViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_note);
        }
    }
}
