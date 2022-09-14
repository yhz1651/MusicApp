package com.example.musicapp.tool;
/**
 *下载模块
 * 用于从远端下载音乐
 */

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTool {
    int count = 0;
    long total = 0;
    int progress = 0;
    public static String url="http://124.221.179.248:7506";//服务器的地址 192.168.1.3 124.221.179.248
    public void download(String site,String filename) throws IOException {//输入下载地址和歌曲名
       URL url = new URL(site);
       URLConnection con = url.openConnection();
       con.connect();
       int fie_length = con.getContentLength();
       InputStream is = url.openStream();
       File path = new File("/storage/emulated/0/Music");
       FileOutputStream fos = new FileOutputStream(path+"/"+filename+".mp3");
       byte data[] = new byte[1024];
       while ((count=is.read(data)) != -1){
           total += count;
           int progress_temp = (int)total*100/fie_length;
           if(progress_temp%10 == 0 && progress != progress_temp){
               progress = progress_temp;
            }
           fos.write(data, 0, count);

       }
       is.close();
       fos.close();
    }
}
