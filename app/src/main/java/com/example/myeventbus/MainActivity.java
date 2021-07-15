package com.example.myeventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lib.Subscribe;
import com.example.lib.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        tvContent = findViewById(R.id.tvContent);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("WWS", "发布事件的线程为：" + Thread.currentThread().getName());
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Log.e("WWS", "发布事件的线程为：" + Thread.currentThread().getName());
                        EventBus.getInstance().post("发送一条事件");
                    }
                }.start();

            }
        });
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @Subscribe(mode = ThreadMode.MAIN)
    public void getEvent(String str){
        Log.e(getClass().getSimpleName(), "WWS getEvent = " + str + " threadName = " + Thread.currentThread().getName());
        tvContent.setText(str);
    }
}