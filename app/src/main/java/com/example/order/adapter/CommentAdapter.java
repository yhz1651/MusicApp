package com.example.order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.example.order.R;
import com.example.order.object.Comment;
import com.example.order.tool.DownloadTool;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 用来显示评论列表的Adapter
 * 用以绑定评论信息和RecyclerView
 * 点击点赞增加点赞数
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemViewHolder> {
    private List<Comment> CommentList;
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
        private TextView UserName;
        private TextView CommentLikes;
        private TextView CommentContent;
        private ImageView AddLike;
        public ItemViewHolder(View itemView) {
            super(itemView);
            UserName =(TextView) itemView.findViewById(R.id.comment_username);
            CommentLikes =(TextView) itemView.findViewById(R.id.comment_likes);
            CommentContent =(TextView) itemView.findViewById(R.id.comment_content);
            AddLike =(ImageView) itemView.findViewById(R.id.add_comment_like);
        }
    }
    public CommentAdapter(List<Comment> CommentList,Context context) {
        this.CommentList = CommentList;
        this.mContext = context;
        bsman = LayoutInflater.from(context);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item,parent,false);
        final ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        itemViewHolder.AddLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=itemViewHolder.getAdapterPosition();
                Comment comment=CommentList.get(index);
//                Intent intent=new Intent(mContext, AddCommentLikes.class);
//                intent.putExtra("commentid",comment.getC_id());
//                mContext.startActivity(intent);
                String comid = comment.getC_id();
                int comgood = comment.getC_good();
                comment.setC_good(comment.getC_good()+1);

                addLike(comid,comgood);
            }
        });
        return itemViewHolder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) { // 绑定view
        Comment comment=CommentList.get(position);
        holder.UserName.setText(comment.getC_username());
        holder.CommentLikes.setText(String.valueOf(comment.getC_good()));
        holder.CommentContent.setText(comment.getC_content());

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
        return CommentList.size();
    }

    // 点赞
    private void addLike(String comid,int comgood){
        new Thread(new Runnable() {//使用多线程，防止主线程堵塞
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                        .setType(MultipartBody.FORM)
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"c_id\""),
                                RequestBody.create(null, comid))
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"c_good\""),
                                RequestBody.create(null, comgood+""))
                        .build();
                String url = DownloadTool.url+"/addLike";
                System.out.println("----------------------------------------------------");
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {//回调方法
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("text", "failure upload!" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.i("success", "成功" + json); // 上传成功后插入到本地歌曲表

                    }
                });
            }
        }).start();
    }

}