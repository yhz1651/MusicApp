package com.example.musicapp.tool;

import com.example.musicapp.object.Music;
import com.example.musicapp.object.MusicList;
import com.example.musicapp.object.Singer;
import com.example.musicapp.object.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class JsonTool {
    private List<Music> musicList =new ArrayList<Music>();
    private List<Singer> SingerList = new ArrayList<Singer>();
    private List<User> UserList= new ArrayList<User>();
    private List<MusicList> MusicList1= new ArrayList<MusicList>();
    public List<Singer> jsonToList_singer(String json) {//将JSON转化为对象数组
        Gson gson = new Gson();
        SingerList = gson.fromJson(json, new TypeToken<List<Singer>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (Singer singer1 : SingerList) {
            System.out.println(singer1.getS_name());
        }
        return SingerList;
    }
    public  List<Music> jsonToList_music(String json) {//将JSON转化为对象数组
        Gson gson = new Gson();
        musicList = gson.fromJson(json, new TypeToken<List<Music>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (Music Music1 : musicList) {
            System.out.println(Music1.getM_name());
        }
        return musicList;
    }
    public  List<User>  jsonToList_user(String json){ //将JSON转化为对象数组
        Gson gson = new Gson();
        UserList = gson.fromJson(json, new TypeToken<List<User>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (User User1 : UserList) {
            System.out.println(User1.getU_username());
        }
        return UserList;
    }
    public  List<MusicList>  jsonToList_musiclist(String json){ //将JSON转化为对象数组
        Gson gson = new Gson();
        MusicList1 = gson.fromJson(json, new TypeToken<List<MusicList>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (MusicList List1 : MusicList1) {
            System.out.println(List1.getMl_name());
        }
        return MusicList1;
    }
}
