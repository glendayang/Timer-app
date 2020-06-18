package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Boolean pause = true;
    Timer timer = new Timer();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题栏
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));//设置状态栏颜色
        //状态栏中字变成深色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //getWindow().setStatusBarColor(Color.TRANSPARENT);//设置状态栏为透明
        setContentView(R.layout.activity_main);
        final Button startStop = (Button) findViewById(R.id.startStop);
        Button temp = (Button) findViewById(R.id.temp);
        final TextView time = (TextView) findViewById(R.id.time);
        time.setText("00:00:00");


        startStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(startStop.getText()=="暂停"){//已经开始计时了，此时要停止计时了
                    startStop.setText("继续");
                    /**这里要写Timer暂停的逻辑*/
                    pause = !pause;
                }
                else if(startStop.getText()=="继续"){
                    pause = !pause;
                    startStop.setText("暂停");
                }
                else{
                    //这里获取当前时间,重新继续开始计时
                    //timer = new Timer();
                    pause = !pause;
                    startStop.setText("暂停");
                    TimerTask timerTask = new TimerTask(){
                        int sec =0;
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(!pause) {
                                        time.setText(getStringTime(sec++));
                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(timerTask,0,1000);

                }
            }
        });


        temp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time.setText(getStringTime(0));
                startStop.setText("开始");
                timer.cancel();
                timer = new Timer();
                pause = true;
            }
        });
    }

    private String getStringTime(int time){
        int hour = time/3600;
        int min = time%3600/60;
        int second = time%60;
        if(hour==0)
            return String.format("00:%02d:%02d",min,second);
        return String.format("%02d:%02d:%02d",hour,min,second);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //开启一个服务
        Intent intent = new Intent(this,ForegroundService.class);
        startService(intent);
    }
}
