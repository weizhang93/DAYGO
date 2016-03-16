package com.example.kimo.daygo_2.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.kimo.daygo_2.util.Utils;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Utils.TAG, "打开了-->" + getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Utils.TAG, "关闭了-->" + getClass().getSimpleName());
    }
}