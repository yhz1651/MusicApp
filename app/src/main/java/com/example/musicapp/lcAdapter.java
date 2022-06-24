package com.example.musicapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class lcAdapter extends RecyclerView.Adapter<lcAdapter.ItemViewHolder> {
    private List<lc> lcList;
    private Context mContext;
    private LayoutInflater bsman = null;

    // 初始化你的Context
    public lcAdapter(Context context) {
        this.mContext = context;
        bsman = LayoutInflater.from(context);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView lcName;
        private TextView lcPic;
        public ItemViewHolder(View itemView) {
            super(itemView);
            lcName=(TextView) itemView.findViewById(R.id.singername1);
            lcPic=(TextView) itemView.findViewById(R.id.songrname);
        }
    }
    public lcAdapter(List<lc> lcList) {
        this.lcList = lcList;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);
        final ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        itemViewHolder.lcPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=itemViewHolder.getAdapterPosition();
                lc lc=lcList.get(index);
                Intent intent=new Intent(mContext, SearchMusic.class);
                intent.putExtra("key",lc.getUid());
                mContext.startActivity(intent);
            }
        });
        itemViewHolder.lcName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=itemViewHolder.getAdapterPosition();
                lc lc=lcList.get(index);
                Toast.makeText(v.getContext(),
                        "你点击了："+lc.getName()+" 的书名",Toast.LENGTH_LONG).show();
            }
        });
        return itemViewHolder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        lc lc=lcList.get(position);
        holder.lcName.setText(lc.getName());
        holder.lcPic.setText(lc.getPicId());
    }
    @Override
    public int getItemCount() {
        return lcList.size();
    }
}