package com.example.zorahu.iotest;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private TextView info;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private File sdroot,app1root,app2root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView)findViewById(R.id.testInfo);

        sp = getSharedPreferences("game.data",MODE_PRIVATE);
        //sp = getPreferences(MODE_PRIVATE);
        editor = sp.edit();

        String state =
                Environment.getExternalStorageState();
        Log.d("test",state);

        if (isExternalStorageReadable()){
            Log.d("test","r");
        }
        if (isExternalStorageWritable()){
            Log.d("test","w");
        }
        /*
        希望將公用檔案儲存在外部儲存空間
        使用 getExternalStoragePublicDirectory() 方法
        取得代表外部儲存空間內相應目錄的 File
        */
        sdroot = Environment.getExternalStorageDirectory();
        app1root = new File(sdroot,"zora");
        app2root = new File(sdroot,"Android/data/" + getPackageName());
        if (!app1root.exists()){
            if (app1root.mkdirs()){
                Log.d("test","Create dir1 Ok");
            }else {
                Log.d("test","Create dir1 Fail");
            }
        }else {
            Log.d("test","app1root exist");
        }
        if (!app2root.exists()){
            if (app2root.mkdirs()){
                Log.d("test","Create dir2 Ok");
            }else {
                Log.d("test","Create dir2 Fail");
            }
        }else {
            Log.d("test","app2root exist");
        }
    }

    /*
    這一組的寫法user按下自身手機設定的清除資料，儲存內容的資料夾就會被砍掉
    */
    public void test1(View v){
        editor.putString("user","Zora");
        editor.putInt("stage",5);
        editor.putBoolean("sound",true);
        editor.commit();
        Toast.makeText(this,"Save OK",Toast.LENGTH_SHORT).show();

    }
    public void test2(View v){
        int stage = sp.getInt("stage",0);
        String user = sp.getString("user","nobody");
        info.setText(user + ":" + stage );

    }

    /*
    這一組將資料存在手機內存空間
     */
    public void test3(View v){
        try {
            FileOutputStream fout = openFileOutput("file.txt",MODE_PRIVATE);
            fout.write("Hello,World\nHello,Zora\n".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this,"Save OK 2 :" ,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,"Exception:" +e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    public void test4(View v){
        info.setText("");
        try {
            FileInputStream fin = openFileInput("file.txt");
            int c;
            while ((c = fin.read())!= -1){
                info.append("" + (char)c);
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void test5(View v){
        File file1 = new File(app1root,"file1.txt");
        try{
            FileOutputStream fout = new FileOutputStream(file1);
            fout.write("test5".getBytes());
            fout.flush();
            fout.close();
        }catch (Exception ee){
            Log.d("test",ee.toString());
        }
    }
    public void test6(View v){
        File file1 = new File(app2root,"file1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file1);
            fout.write("test6".getBytes());
            fout.flush();
            fout.close();

        }catch (Exception ee){
            Log.d("test",ee.toString());
        }

    }
    public void test7(View v){
        info.setText("");
        File file1 = new File(app1root,"file.txt");
        try{
            FileInputStream fin = new FileInputStream(file1);
            int c;
            while ((c = fin.read()) != -1){
                info.append("" + (char)c);
            }

        }catch (Exception ee){

        }

    }
    public void test8(View v){
        info.setText("");
        File file1 = new File(app2root,"file1.txt");
        try{
            FileInputStream fin = new FileInputStream(file1);
            int c;
            while ((c = fin.read()) != -1){
                info.append("" + (char)c);
            }
        }catch (Exception ee){

        }

    }

    /*
    Checks if external storage is available to at least read
     */
    public static boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }
     /*
     Checks if external storage is available for read and write
     */
    public static boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

}
