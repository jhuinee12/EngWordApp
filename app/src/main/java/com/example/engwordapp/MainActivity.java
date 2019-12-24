package com.example.engwordapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main1).setOnClickListener(myClick);
        findViewById(R.id.main2).setOnClickListener(myClick);
        findViewById(R.id.main3).setOnClickListener(myClick);
        findViewById(R.id.main4).setOnClickListener(myClick);
        findViewById(R.id.main5).setOnClickListener(myClick);
        findViewById(R.id.main6).setOnClickListener(myClick);
        findViewById(R.id.main7).setOnClickListener(myClick);
    }

    Button.OnClickListener myClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main1:    // 수능단어
                    startActivity(new Intent(MainActivity.this, ActivityStudy.class));
                    break;
                case R.id.main2:    // 토인단어
                    startActivity(new Intent(MainActivity.this, ActivityStudy2.class));
                    break;
                case R.id.main3:    // 공무원단어
                    startActivity(new Intent(MainActivity.this, ActivityStudy3.class));
                    break;
                case R.id.main4:    // 토플단어
                    startActivity(new Intent(MainActivity.this, ActivityStudy4.class));
                    break;
                case R.id.main5:    // 평가
                    startActivity(new Intent(MainActivity.this, ActivityStudy5.class));
                    break;
                case R.id.main6:    // 도움말
                    startActivity(new Intent(MainActivity.this, About.class));
                    break;
                case R.id.main7:    // 나가기
                    finish();
                    break;
            }
        }
    };
}

