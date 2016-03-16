package com.example.kimo.daygo_2.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.widget.MovieRecorderView;

public class VideoActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finishActivity();
        }
    };

    private MovieRecorderView mRecorderView;//
    private Button mShootBtn;
    private boolean isFinish = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFinish = true;
    }

    private void initView() {
        mRecorderView = (MovieRecorderView) findViewById(R.id.movieRecorderView);
        mShootBtn = (Button) findViewById(R.id.shoot_button);

        mShootBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {

                        @Override
                        public void onRecordFinish() {
                            handler.sendEmptyMessage(1);
                        }
                    });
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mRecorderView.getTimeCount() > 1)
                        handler.sendEmptyMessage(1);
                    else {
                        if (mRecorderView.getmVecordFile() != null)
                            mRecorderView.getmVecordFile().delete();
                        mRecorderView.stop();
                        Toast.makeText(VideoActivity.this, "视频录制时间太短", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isFinish = false;
        mRecorderView.stop();
    }

    private void finishActivity() {
        if (isFinish) {
            mRecorderView.stop();
            Intent i = new Intent(this,PlayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("wei", mRecorderView.getmVecordFile().getName());
            i.putExtras(bundle);
            startActivity(i);
        }
    }

    /**
     * 录制完成回调
     *
     * @author liuyinjun
     * @date 2015-2-9
     */
    public interface OnShootCompletionListener {
        public void OnShootSuccess(String path, int second);

        public void OnShootFailure();
    }
}
