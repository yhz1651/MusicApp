package com.example.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.example.order.fragment.PlayViewModel;
import com.example.order.R;
import com.example.order.activity.ShowListActivity;
import com.example.order.object.MusicList;

/**
 * 用来显示歌单列表的Adapter
 */
public class MusicListAdapter1 extends RecyclerView.Adapter<MusicListAdapter1.ItemViewHolder> {
    private List<MusicList> MusicList1;
    private Context mContext;
    private LayoutInflater bsman = null;
    public OnItemClickListener listener;
    private PlayViewModel viewModel;
    private int kind;
    private String uid;

    public MusicListAdapter1(List<MusicList> musicList1, ShowListActivity context) {
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView SingerName;
        private ImageView image;
        public ItemViewHolder(View itemView) {
            super(itemView);
            SingerName =(TextView) itemView.findViewById(R.id.listname);
            image= (ImageView) itemView.findViewById(R.id.delete);
        }
    }
    public MusicListAdapter1(List<MusicList> MusicList1, Context context, int kind, String uid) {
        this.MusicList1 = MusicList1;
        this.mContext = context;
        this.kind=kind;//画面类型
        this.uid=uid;
        bsman = LayoutInflater.from(context);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mlist_item,parent,false);
        final ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        itemViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击删除按钮
                int index=itemViewHolder.getAdapterPosition();
                MusicList Music= MusicList1.get(index);
                if(kind==1){
                    return;
                }else{
//                    delete_reclist(uid,Music.getMl_id());//删除关注

                }

            }
        });
        return itemViewHolder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {//绑定view
        MusicList Music= MusicList1.get(position);
        holder.SingerName.setText(Music.getMl_name());
        if(kind==1){holder.image.setImageAlpha(0);}
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
        return MusicList1.size();
    }

//    public String  delete_reclist(String ml_id, String ml_uid){//删除关注的歌单
//        String ans = null;
//        CallAble.delete_reclist callable = new CallAble.delete_reclist(ml_id,ml_uid);
//        //将实现Callable接口的对象作为参数创建一个FutureTask对象
//        FutureTask<String> task = new FutureTask<>(callable);
//        //创建线程处理当前callable任务
//        Thread thread = new Thread(task);
//        //开启线程
//        thread.start();
//        //获取到call方法的返回值
//        try {
//            String result = task.get();
//            ans=result;
//            Log.e("text", "result" +ans );
//            thread.join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ans;
//    }
}