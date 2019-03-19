package com.example.coffee.testtimer;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_select_class);
//        isLeaderDialog();
    }

    //班主任提示框ui界面
    private void isLeaderDialog() {
        AlertDialog IsHeadmasterDialog = new AlertDialog.Builder(this, R.style.common_dialog_style).create();
        IsHeadmasterDialog.show();
        Window window = IsHeadmasterDialog.getWindow();
        window.setContentView(R.layout.dialog_class_info);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        android.view.WindowManager.LayoutParams p = IsHeadmasterDialog.getWindow().getAttributes();
        p.height = (int) (displayHeight * 0.6);    //宽度设置为屏幕的0.5
//        dlg.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        window.setAttributes(p);     //设置生效
    }
}
