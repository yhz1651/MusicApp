package com.example.musicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.ChangeUserInfoActivity;
import com.example.musicapp.R;
import com.example.musicapp.object.User;
import com.example.musicapp.object.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemViewHolder> {
    private List<User> UserList;
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
        public ImageView change_User_button;
        private TextView UserName;
        public ItemViewHolder(View itemView) {
            super(itemView);
            UserName =(TextView) itemView.findViewById(R.id.username);
            change_User_button =(ImageView) itemView.findViewById(R.id.change_user_button);
        }
    }
    public UserAdapter(List<User> UserList,Context context) {
        this.UserList = UserList;
        this.mContext = context;
        bsman = LayoutInflater.from(context);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_change_item,parent,false);
        final ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        itemViewHolder.change_User_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=itemViewHolder.getAdapterPosition();
                User User=UserList.get(index);
                Intent intent = new Intent(mContext, ChangeUserInfoActivity.class);
                intent.putExtra("id",User.getU_id());
                mContext.startActivity(intent);
            }
        });
        return itemViewHolder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {//绑定view
        User User=UserList.get(position);
        holder.UserName.setText(User.getU_username());

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
        return UserList.size();
    }


}