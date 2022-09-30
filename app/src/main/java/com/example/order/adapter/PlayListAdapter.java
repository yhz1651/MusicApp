package com.example.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.example.order.R;
import com.example.order.object.Music;
/**
 * 播放列表的Adapter
 * 用以绑定歌曲信息和RecyclerView
 * 点击DELETE删除
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ItemViewHolder> {
    private List<Music> MusicList;
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
        private TextView SingerName;
        private TextView MusicName;
        public ItemViewHolder(View itemView) {
            super(itemView);
            SingerName =(TextView) itemView.findViewById(R.id.singername1);
            MusicName =(TextView) itemView.findViewById(R.id.songrname);
        }
    }
    public PlayListAdapter(List<Music> MusicList,Context context) {
        this.MusicList = MusicList;
        this.mContext = context;
        bsman = LayoutInflater.from(context);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.play_list_item,parent,false);
        final ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        return itemViewHolder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {//绑定view
        Music Music=MusicList.get(position);
        holder.SingerName.setText(Music.getM_singer());
        holder.MusicName.setText(Music.getM_name());

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
        return MusicList.size();
    }


}