package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.object.Music;
import com.example.musicapp.object.User;
import com.example.musicapp.service.DatabaseHelper;
import com.example.musicapp.service.UserService;
import com.example.musicapp.tool.CallAble;
import com.example.musicapp.tool.DownloadTool;
import com.example.musicapp.tool.UriTool;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListMakeActivity extends AppCompatActivity {
    String user_id;
    String ans;
    String img_path;
    ImageView searchfile;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_make);
        ImageView searchfile = (ImageView)findViewById(R.id.cover) ;
        searchfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用系统文件夹
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //选择音频

                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }

        });
        name=(EditText) findViewById(R.id.listname);
        Button Submit=(Button) findViewById(R.id.Create);
        Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserApplication application1 = (UserApplication) ListMakeActivity.this.getApplication();//获取application
                user_id = application1.getValue();//获得用户ID
                String ml_name= name.getText().toString().trim();//获取歌曲名字
                makelist(user_id,ml_name);
                Toast.makeText(ListMakeActivity.this, "创建成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ListMakeActivity.this,MainActivity.class);//上传成功后，回到主页面
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                Uri uri = data.getData();
                UriTool ut=new UriTool();
                img_path= ut.UriToPath(this, uri);

            }
        }
    }
    public String  makelist(String ml_userid, String ml_name){//创建歌单
        CallAble.createlist callable = new CallAble.createlist (ml_userid,ml_name);
        //将实现Callable接口的对象作为参数创建一个FutureTask对象
        FutureTask<String> task = new FutureTask<>(callable);
        //创建线程处理当前callable任务
        Thread thread = new Thread(task);
        //开启线程
        thread.start();
        //获取到call方法的返回值
        try {
            String result = task.get();
            ans=result;
            Log.e("text", "result" +ans );
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}