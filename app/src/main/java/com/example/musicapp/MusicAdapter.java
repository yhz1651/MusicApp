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

import com.example.musicapp.object.Music;
/**
 * 用来显示歌曲列表的Adapter
 * 用以绑定歌曲信息和RecyclerView
 * 以及设置点击动作
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ItemViewHolder> {
    private List<Music> MusicList;
    private Context mContext;
    private LayoutInflater bsman = null;


    static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView SingerName;
        private TextView MusicName;
        public ItemViewHolder(View itemView) {
            super(itemView);
            SingerName =(TextView) itemView.findViewById(R.id.singername1);
            MusicName =(TextView) itemView.findViewById(R.id.songrname);
        }
    }
    public MusicAdapter(List<Music> MusicList,Context context) {
        this.MusicList = MusicList;
        this.mContext = context;
        bsman = LayoutInflater.from(context);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);
        final ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        itemViewHolder.MusicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=itemViewHolder.getAdapterPosition();
                Music Music=MusicList.get(index);
                Intent intent=new Intent(mContext, RegisterActivity.class);
                intent.putExtra("key",Music.getName());
                mContext.startActivity(intent);
            }
        });
        itemViewHolder.SingerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=itemViewHolder.getAdapterPosition();
                Music Music=MusicList.get(index);
                Toast.makeText(v.getContext(),
                        "你点击了："+Music.getName(),Toast.LENGTH_LONG).show();
            }
        });
        return itemViewHolder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {//绑定view
        Music Music=MusicList.get(position);
        holder.SingerName.setText(Music.getSinger());
        holder.MusicName.setText(Music.getName());
    }
    @Override
    public int getItemCount() {
        return MusicList.size();
    }
}