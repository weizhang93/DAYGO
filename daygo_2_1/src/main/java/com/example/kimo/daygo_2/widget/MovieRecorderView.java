package com.example.kimo.daygo_2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/12/25 0025.
 * 自定义小视频控件
 */
public class MovieRecorderView extends LinearLayout implements MediaRecorder.OnErrorListener {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private ProgressBar mProgressBar;

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private Timer mTimer;// 计时器
    private OnRecordFinishListener mOnRecordFinishListener;// 录制完成回调接口

    private int mWidth;// 视频分辨率宽度
    private int mHeight;// 视频分辨率高度
    private boolean isOpenCamera;// 是否一开始就打开摄像头
    private int mCamDir;//相机方向
    private int mRecordMaxTime;// 一次拍摄最长时间
    private int mTimeCount;// 时间计数
    private File mVecordFile = null;// 文件

    public MovieRecorderView(Context context) {
        this(context, null);
    }

    public MovieRecorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public int getmCamDir() {
        return mCamDir;
    }

    public void setmCamDir(int mCamDir) {
        this.mCamDir = mCamDir;
        try {
            initCamera();//刷新相机
        } catch (IOException e) {
            e.printStackTrace();
        }
        invalidate();
        requestLayout();
    }

    public MovieRecorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MovieRecorderView, defStyleAttr, 0);
        mWidth = a.getInteger(R.styleable.MovieRecorderView_width, 1280);// 默认320
        mHeight = a.getInteger(R.styleable.MovieRecorderView_height, 960);// 默认240s
        mCamDir = a.getInt(R.styleable.MovieRecorderView_cam_direction,0);

        isOpenCamera = a.getBoolean(R.styleable.MovieRecorderView_is_open_camera, true);// 默认打开
        mRecordMaxTime = a.getInteger(R.styleable.MovieRecorderView_record_max_time, 30);// 默认为10

        LayoutInflater.from(context).inflate(R.layout.movie_recorder_view, this);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(mRecordMaxTime);// 设置进度条最大量

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new CustomCallBack());
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        a.recycle();
    }


    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        try {
            if (mr != null)
                mr.reset();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author liuyinjun
     * @date 2015-2-5
     */
    private class CustomCallBack implements Callback {


        public void surfaceCreated(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            try {
                initCamera();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }


        public void surfaceDestroyed(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            freeCameraResource();
        }

    }

    /**
     * 初始化摄像头
     *
     * @throws IOException
     * @author weiZhang
     * @date 2016-3-24
     * 选择前后摄像头，默认为前
     */
    private void initCamera() throws IOException {
        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras(); // get cameras number
            switch (mCamDir){
                case 0:
                    for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                        Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) { //
                            // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                            mCamera = Camera.open(camIdx);
                        }
                    }
                    break;
                case 1:
                    for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                        Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) { //
                            // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                            mCamera = Camera.open(camIdx);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }
        if (mCamera == null)
            return;

        setCameraParams();
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewDisplay(mSurfaceHolder);
        mCamera.startPreview();
        mCamera.unlock();
    }

    /**
     * 释放摄像头资源
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 设置摄像头为竖屏
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    private void setCameraParams() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            params.set("orientation", "portrait");
            mCamera.setParameters(params);
        }
    }

    /**
     * 创建一个存放视频的目录
     */
    private void createRecordDir() {
        File sampleDir = new File(Environment.getExternalStorageDirectory() + File.separator +
                "DAYGO_2/video");
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        File vecordDir = sampleDir;
        // 创建文件
        String date = Utils.dataFormat("yy_MM_dd_ss");//1.5 yy_MM_dd
        //mVecordFile = File.createTempFile("DG_" +date, ".mp4", vecordDir);//mp4格式
        mVecordFile = new File(vecordDir, "DG_" + date + ".mp4");
        Log.d("TAG", mVecordFile.getAbsolutePath());
    }

    /**
     * 初始化
     *
     * @throws IOException
     * @author liuyinjun
     * @date 2015-2-5
     */
    private void initRecord() throws IOException {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        if (mCamera != null)
            mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setOnErrorListener(this);
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 视频输出格式
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 音频格式
        mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率：
        // mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用
        mMediaRecorder.setVideoEncodingBitRate(2 * 1024 * 512);// 设置帧频率，然后就清晰了
        mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);// 视频录制格式
        // mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
        mMediaRecorder.setOutputFile(mVecordFile.getAbsolutePath());
        mMediaRecorder.prepare();
        try {
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始录制视频
     *
     * @param
     * @param onRecordFinishListener 达到指定时间之后回调接口
     * @author liuyinjun
     * @date 2015-2-5
     */
    public void record(final OnRecordFinishListener onRecordFinishListener) {
        this.mOnRecordFinishListener = onRecordFinishListener;
        createRecordDir();
        try {
            if (!isOpenCamera)// 如果未打开摄像头，则打开
                initCamera();
            initRecord();
            mTimeCount = 0;// 时间计数器重新赋值
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mTimeCount++;
                    mProgressBar.setProgress(mTimeCount);// 设置进度条
                    if (mTimeCount == mRecordMaxTime) {// 达到指定时间，停止拍摄
                        stop();
                        if (mOnRecordFinishListener != null)
                            mOnRecordFinishListener.onRecordFinish();
                    }
                }
            }, 0, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止拍摄
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    public void stop() {
        stopRecord();
        releaseRecord();
        freeCameraResource();
    }

    /**
     * 停止录制
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    public void stopRecord() {
        mProgressBar.setProgress(0);
        if (mTimer != null)
            mTimer.cancel();
        if (mMediaRecorder != null) {
            // 设置后不会崩
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            try {
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    private void releaseRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder.setOnErrorListener(null);
            try {
                mMediaRecorder.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mMediaRecorder = null;
    }

    public int getTimeCount() {
        return mTimeCount;
    }

    /**
     * @return the mVecordFile
     */
    public File getmVecordFile() {
        return mVecordFile;
    }

    /**
     * 录制完成回调接口
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    public interface OnRecordFinishListener {
        public void onRecordFinish();
    }

}
