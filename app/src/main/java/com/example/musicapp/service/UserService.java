package com.example.musicapp.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.musicapp.LoginActivity;
import com.example.musicapp.object.User;
import com.example.musicapp.UserApplication;


public class UserService {
    private static DatabaseHelper dbHelper;
    public static DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public UserService(Context context){
        dbHelper=new DatabaseHelper(context);
    }

    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select u_id from user where u_username=? and u_password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()){
            UserApplication application1 = (UserApplication) LoginActivity.getInstance().getApplication();
            application1.setValue(cursor.getString(0));;
            Toast.makeText(LoginActivity.getInstance(),cursor.getString(0), Toast.LENGTH_LONG).show();
            cursor.close();
            return true;
        }
        return false;
    }
    public boolean register(User user){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="insert into user(u_username,u_password,u_sex,u_age,u_phone,u_like,u_type) values(?,?,?,?,?,?,0);";
        Object obj[]={user.getUsername(),user.getPassword(),user.getSex(),user.getAge(),user.getPhone(),user.getXingqu()};
        sdb.execSQL(sql, obj);
        return true;
    }
}
