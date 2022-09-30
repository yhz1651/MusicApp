package com.example.order.activity;

//登录界面，登录成功进入主界面
import android.Manifest;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.order.R;
import com.example.order.UserApplication;
import com.example.order.service.UserService;

public class LoginActivity extends AppCompatActivity {
    private static LoginActivity instance;

    public static LoginActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        instance = this;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    if(ContextCompat.checkSelfPermission(this,//动态申请内存访问权限
      Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }
}
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;

    private void findViews() {
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        register=(Button) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                System.out.println(name);
                String pass=password.getText().toString();
                System.out.println(pass);

                Log.i("TAG",name+"_"+pass);
                UserService uService=new UserService(LoginActivity.this);
                String flag=uService.login(name, pass);
                if(flag==null){Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();return;}
                if(!flag.equals("false")){//如果不是返回false即登录成功
                    Log.i("TAG","登录成功");
                    UserApplication application1 = (UserApplication) LoginActivity.getInstance().getApplication();//将用户名存入Application
                    application1.setValue(flag);
                    Toast.makeText(LoginActivity.this, "成功登录", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    if(flag.equals("U00000000001"))
                    {intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);}//管理员账号进入管理页面
                    else{
                    intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);}
                }else{
                    Log.i("TAG","登录失败");
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        ImageView btn1 = (ImageView)findViewById(R.id.LogoImage);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });
    }
}