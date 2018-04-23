package com.example.cathleen.testhandler;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button startThread;
    private TextView show;
    private MyHandler handler;
    private MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new MyHandler();
        thread = new MyThread();

        startThread = (Button) findViewById(R.id.startThread);
        show = (TextView) findViewById(R.id.show);

        startThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(thread).start();
            }
        });

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            show.setText(msg.arg1 + "");
        }
    }

    private class MyThread implements Runnable {
        @Override
        public void run() {
            int progress = 0;
            Message msg;
            while (progress <= 100) {
                msg = new Message();
                msg.arg1 = progress;
                handler.sendMessage(msg);
                progress += 10;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            msg = handler.obtainMessage();
            msg.arg1 = 0;
            handler.sendMessage(msg);
        }
    }
}
