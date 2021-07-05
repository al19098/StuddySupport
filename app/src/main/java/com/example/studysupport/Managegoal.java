package com.example.studysupport;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Managegoal {
    Context context = AppContext.getAppContext();
    String filename = "goaltime.txt";
    File file = new File(context.getFilesDir(), filename);

    public String readFile() {
        String text = null;
        //InputStream is = null;
        //BufferedReader br = null;
        try {
            Log.d("readfile", "入った");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));
            /*AssetManager assets = context.getResources().getAssets();
            is = assets.open(filename);
            br = new BufferedReader(new InputStreamReader(is));*/
            text = br.readLine();
            br.close();
            //Log.d("readfile", text);
        } catch (IOException e) {
            //e.printStackTrace();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("0");
                text = "0";
            }catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return text;
    }
    public void saveTime(String str) {//入力された値をファイルに保存する
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resetTime(String str) {//入力された値をファイルに保存する
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}