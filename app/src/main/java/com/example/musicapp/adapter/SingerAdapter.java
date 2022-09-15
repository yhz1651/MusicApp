package com.example.musicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.ChangeSinger;
import com.example.musicapp.R;
import com.example.musicapp.object.Singer;
/**
 * 播放列表的Adapter
 * 用以绑定歌曲信息和RecyclerView
 * 点击DELETE删除
 */
public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.ItemViewHolder> {
    private List<Singer> SingerList;
    private Context mContext;
    private LayoutInflater bsman = null;
    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView change_singer_button;
        private TextView SingerName;
        public ItemViewHolder(View itemView) {
            super(itemView);
            SingerName =(TextView) itemView.findViewById(R.id.singername);
            change_singer_button =(ImageView) itemView.findViewById(R.id.change_singer_button);
        }
    }
    public SingerAdapter(List<Singer> SingerList,Context context) {
        this.SingerList = SingerList;
        this.mContext = context;
        bsman = LayoutInflater.from(context);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singer_item,parent,false);
        final ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        itemViewHolder.change_singer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=itemViewHolder.getAdapterPosition();
                Singer Singer=SingerList.get(index);//获得点击位置
                Bundle bundle = new Bundle();
                bundle.putSerializable("singer",Singer);//将singer存入bundle通过Intent传递
                Intent intent=new Intent(mContext, ChangeSinger.class);//进入改变歌手信息界面
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return itemViewHolder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {//绑定view
        Singer Singer=SingerList.get(position);
        holder.SingerName.setText(Singer.getS_name());

        if(listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemClick(holder.itemView, pos);
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return SingerList.size();
    }


}