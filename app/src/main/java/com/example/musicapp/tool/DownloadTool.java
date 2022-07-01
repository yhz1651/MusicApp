package com.example.musicapp.tool;

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
    public static String url="http://192.168.1.110:7506";
    public void download(String site,String filename) throws IOException {
       URL url = new URL(site);
       URLConnection con = url.openConnection();
       con.connect();
       int fie_length = con.getContentLength();
       InputStream is = url.openStream();
       File path = new File("/storage/emulated/0/Music");
       FileOutputStream fos = new FileOutputStream(path+"/"+filename);
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
