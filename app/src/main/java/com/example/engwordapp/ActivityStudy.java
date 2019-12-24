package com.example.engwordapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityStudy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 스마트폰 기기의 menu 키를 눌렀을 때 아래에서 위로 올라오는 메뉴
        menu.add(0, 1, 0, "sound on");
        menu.add(0,2,0,"sound off");
        menu.add(0,3,0,"");
        menu.add(0,4,0,"");
        return true;
    }

    //--------------------------------------------
    //  onOptions ItemSelected
    //--------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   // MenuItem 중 하나를 눌렀을 때 자동으로 호출되는 메소드
        switch (item.getItemId()) {
            case 1:
                StudyView.soundOk = 1;
                break;
            case 2:
                StudyView.soundOk = 0;
                break;
            case 3:
                break;
            case 4:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // 사용자가 키를 누르면 호출
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 이전으로 돌아가기 키
            // System.exit(0);  // 메인화면으로 돌아가기
            finish();
            return false;
        }
        return false;
    }

    public void exitProgram() {
        finish();
    }
}
