package com.example.musicapp.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static String name="user.db";
    static int dbVersion=1;
    public DatabaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql=
                "CREATE TABLE [User]" +
                "(" +
                " u_id integer PRIMARY KEY  autoincrement," +
                " u_username varchar(15)," +
                " u_password varchar(20)," +
                " u_sex CHAR(2)  CHECK(u_sex='男' OR u_sex='女')," +
                " u_age INTEGER," +
                " u_phone char(11) ," +
                " u_like varchar(40)," +
                " u_type INTEGER CHECK(u_type=1 OR u_type=0)" +
                ");";
        db.execSQL(sql);
        String sql1="create table Singer " +
                "(" +
                " s_id INTEGER PRIMARY KEY autoincrement," +
                " s_name varchar(30)," +
                " s_region varchar(30)," +
                " s_intro varchar(200)" +
                ");";
        String sql2="CREATE TABLE Music" +
                "(" +
                " m_id INTEGER PRIMARY KEY  autoincrement," +
                " m_name varchar(30)," +
                " m_url varchar(50)," +
                " m_singer varchar(30)," +
                " m_type INTEGER CHECK(m_type=1 OR m_type=0)," +
                " m_userid INTEGER" +
                ");";
        String sql3 = "CREATE TABLE Musiclist" +
                "(" +
                " ml_id INTEGER  PRIMARY KEY autoincrement," +
                " ml_name varchar(20)," +
                " ml_userid INTEGER" +
                ");";
        String sql4 ="CREATE TABLE Music_musiclist" +
                "(" +
                " mml_musicid INTEGER ," +
                " mml_listid INTEGER ," +
                " primary key(mml_musicid,mml_listid)" +
                ");";
        String sql5 ="CREATE TABLE Comment" +
                "(" +
                " c_id INTEGER  PRIMARY KEY autoincrement," +
                " c_content varchar(200)," +
                " c_userid INTEGER " +
                ");";
        String sql6 = "CREATE TABLE Music_commment" +
                "(" +
                " mc_musicid INTEGER," +
                " mc_commentid INTEGER," +
                " primary key(mc_musicid,mc_commentid)" +
                ");";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
