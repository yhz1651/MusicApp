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
        String sql2="CREATE TABLE Music" +
                "(" +
                " m_id INTEGER PRIMARY KEY  autoincrement," +
                " m_name varchar(30)," +
                " m_url varchar(200)," +
                " m_singer varchar(30)," +
                " m_duration INTEGER,"+
                " m_type INTEGER CHECK(m_type=1 OR m_type=0)," +
                " m_userid INTEGER" +
                ");";

        db.execSQL(sql2);
        String sql1="CREATE TABLE Singer" +
                "(" +
                " s_id varchar(12)," +
                " s_name varchar(30)," +
                " s_regin varchar(30)," +
                " s_intro varchar(200)" +
                ");";
        db.execSQL(sql1);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
