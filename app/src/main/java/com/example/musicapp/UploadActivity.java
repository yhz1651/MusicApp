package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.musicapp.service.DatabaseHelper;
import com.example.musicapp.service.UserService;

//注册界面，注册成功返回登录界面
public class UploadActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    public UploadActivity(Context context){
        dbHelper=new DatabaseHelper(context);
    }
    int index;
    int s_id;
    EditText songname;
    EditText singername;
    EditText songkind;
    EditText url_text;
    Button searchfile;
    Button select_kind;
    Button Submit;
    String txt = "";
    String kind_list[] = new String[] {"摇滚","伤感","爱情","国风","日语","欧美","粤语","舒缓","佛系"};
    private void cancelBool(){
    }

    public void on2click(View view) {
        Button button = findViewById(R.id.select_kind);
        if(button != null){
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(UploadActivity.this);
            aBuilder.setTitle("歌曲类别");

            // 创建对话框的内容
            aBuilder .setSingleChoiceItems(kind_list,1,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    index = i;
                }
            });

            // 加入多选对话框下面的确认按钮,打印勾选的项名称
            aBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 获取勾选到的文本并打印
//                    String txt = "";
                    txt= kind_list[index];
                    songkind.setText(txt);
                    // 打印
                    Toast.makeText(UploadActivity.this, txt, Toast.LENGTH_SHORT).show();

                    // 取消内部已勾选
                    cancelBool();
                }
            });

            // 点击对话框外,进行取消对话框事件
            aBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    // 取消内部已勾选
                    cancelBool();
                }
            });

            // 显示对话框
            aBuilder.show();
        }
    }
    //
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);
        findViews();
        searchfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用系统文件夹
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*"); //选择音频

                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }

        });
        Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String song_n= songname.getText().toString().trim();
                String singer_n= singername.getText().toString().trim();
                String url = url_text.getText().toString().trim();
                dbHelper= UserService.getDbHelper();
                SQLiteDatabase sdb=dbHelper.getReadableDatabase();
                String sql="SELECT s_id FROM Singer WHERE s_name=?";
                Cursor cursor=sdb.rawQuery(sql, new String[]{singer_n});
                UserApplication application1 = (UserApplication) UploadActivity.this.getApplication();
                int id = application1.getValue();;
                if(cursor.moveToFirst()){
                    s_id = cursor.getInt(0);
                    Toast.makeText(LoginActivity.getInstance(),cursor.getString(0), Toast.LENGTH_LONG).show();
                    cursor.close();
                }else{
                    s_id = 1;
                }
                Song  s = new Song(song_n,s_id,url,id);//存入歌曲对象中
                sql="insert into Music(m_name,m_url,m_singer,m_type,m_userid) values(?,?,?,0,?);";
                Object obj[]={s.getName(),s.getUrl(),s.getSinger(),s.getUser()};
                sdb.execSQL(sql, obj);
                Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UploadActivity.this,MainActivity.class);
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
                Log.e("图片URI：", uri.toString());
                url_text.setText(uri.toString());
            }
        }
    }
    private void findViews() {
        songname =(EditText) findViewById(R.id.song_name);
        singername =(EditText) findViewById(R.id.singername);
        songkind = (EditText) findViewById(R.id.songKind);
        url_text = (EditText) findViewById(R.id.url);
        searchfile =(Button)findViewById(R.id.search_file);
        select_kind = (Button)findViewById(R.id.select_kind);
        Submit = (Button) findViewById(R.id.Submit);
    }

}