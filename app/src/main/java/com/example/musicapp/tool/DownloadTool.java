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
    public static String url="http://192.168.1.110:7506";
    public void download(String site) throws IOException {
       URL url = new URL(site);

        URLConnection conexion = url.openConnection();

        conexion.connect();

        int lenghtOfFile = conexion.getContentLength();

        InputStream is = url.openStream();

        File testDirectory =

                new File("/storage/emulated/0/Music");

        if(!testDirectory.exists()){
            testDirectory.mkdir();

        }

        FileOutputStream fos = new FileOutputStream(testDirectory+"/love.mp3");

        byte data[] = new byte[1024];

        int count = 0;

        long total = 0;

        int progress = 0;

        while ((count=is.read(data)) != -1){
            total += count;

            int progress_temp = (int)total*100/lenghtOfFile;

            if(progress_temp%10 == 0 && progress != progress_temp){
                progress = progress_temp;

            }

            fos.write(data, 0, count);

        }

        is.close();

        fos.close();
    }
}
