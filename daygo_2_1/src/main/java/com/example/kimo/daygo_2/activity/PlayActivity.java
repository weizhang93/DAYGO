package com.example.kimo.daygo_2.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;


import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.util.ShareUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/12/18 0018.
 * 视频播放类
 */
public class PlayActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnPlay;
    private Button btnPause;
    private Button btnStop;
    private VideoView mVideoView;
    private File mfile;
    private String s = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initView();
        initToolbar();

        mVideoView.start();
    }

    private void initView() {
        btnPlay = (Button) findViewById(R.id.btn_play_play);
        btnPause = (Button) findViewById(R.id.btn_pause_play);
        btnStop = (Button) findViewById(R.id.btn_stop_play);
        mVideoView = (VideoView) findViewById(R.id.vv_play);

        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        initVideoPath();
    }

    private void initVideoPath() {
        Intent i = getIntent();
        s = i.getExtras().getString("wei");
        if(s!=null){
            mfile = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "DayGo_2/video/"+s);
        }
        mVideoView.setVideoPath(mfile.getPath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play_play:
                if(!mVideoView.isPlaying()){
                    mVideoView.start();
                }
                break;
            case R.id.btn_pause_play:
                if(mVideoView.isPlaying()){
                    mVideoView.pause();
                }
                break;
            case R.id.btn_stop_play:
                if(mVideoView.isPlaying()){
                    mVideoView.resume();
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVideoView!=null){
            mVideoView.suspend();//
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                //String shareMessage = webView.getTitle() + ":" + webView.getUrl() + "--daygo";
                //ShareUtils.shareText(this, shareMessage);
                ShareUtils.shareVideo(this, Uri.fromFile(mfile));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //并不好用，它只会返回到最初的Activity状态
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(s);
    }
}
