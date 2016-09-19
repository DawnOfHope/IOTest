package com.example.zorahu.iotest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private TextView info;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView)findViewById(R.id.testInfo);

        sp = getSharedPreferences("game.data",MODE_PRIVATE);
        //sp = getPreferences(MODE_PRIVATE);
        editor = sp.edit();
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
            fout.flush();
            fout.close();
        } catch (Exception e) {
            Toast.makeText(this,"Exception:" +e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    public void test4(View v){

    }
}
