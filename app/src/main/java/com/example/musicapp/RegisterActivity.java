package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.musicapp.service.UserService;
import android.app.AlertDialog;
import android.content.DialogInterface;

//注册界面，注册成功返回登录界面
public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    RadioGroup sex;
    EditText age;
    EditText phone;
    EditText xingquregister;
    Button register;
    TextView back;
    //
    String txt = "";
    //
    String strList[] = new String[] {"摇滚","伤感","爱情","国风","日语","欧美","粤语","舒缓","佛系"};
    boolean blList[] = new boolean[]{false,false,false,false,false,false,false,false,false};
    // 取消内部已勾选
    private void cancelBool(){
        for(short index=0;index<blList.length;++index){
            blList[index] = false;
        }
    }
    //

    //
    public void on2click(View view) {
        Button button = findViewById(R.id.xingquButton);
        if(button != null){
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(RegisterActivity.this);
            aBuilder.setTitle("歌曲兴趣（可多选）");

            // 创建多选对话框的内容
            aBuilder.setMultiChoiceItems(strList, blList, new DialogInterface.OnMultiChoiceClickListener() {
                // 取消或勾选对话框内的单选框
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    blList[i] = b;
                }
            });

            // 加入多选对话框下面的确认按钮,打印勾选的项名称
            aBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 获取勾选到的文本并打印
//                    String txt = "";
                    for(short index=0;index<blList.length;++index){
                        if(blList[index]){
                            txt += (strList[index]+" ");
                        }
                    }
                    EditText et = (EditText)findViewById(R.id.xingquRegister);//获取edittext组件
                    et.setText(txt);
                    // 打印
                    Toast.makeText(RegisterActivity.this, txt, Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.activity_register);
        findViews();
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String sexstr=((RadioButton)RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
                int ag= Integer.parseInt(age.getText().toString().trim());
                String ph=phone.getText().toString().trim();
                String xingqustr=txt.trim();
                Log.i("TAG",name+"_"+pass+"_"+xingqustr+"_"+sexstr);
                UserService uService=new UserService(RegisterActivity.this);
                User user=new User();
                user.setUsername(name);
                user.setPassword(pass);
                user.setSex(sexstr);
                user.setAge(ag);
                user.setPhone(ph);
                user.setXingqu(xingqustr);
                uService.register(user);
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
       back.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
               startActivity(intent);
           }
       });
    }
    //
    private void findViews() {
        username=(EditText) findViewById(R.id.usernameRegister);
        password=(EditText) findViewById(R.id.passwordRegister);
        sex=(RadioGroup) findViewById(R.id.sexRegister);
        age=(EditText) findViewById(R.id.ageRegister);
        phone=(EditText) findViewById(R.id.phoneRegister);
        xingquregister= (EditText) findViewById(R.id.xingquRegister);
        register=(Button) findViewById(R.id.Register);
        back=(TextView) findViewById(R.id.BackToLogin);
    }

}