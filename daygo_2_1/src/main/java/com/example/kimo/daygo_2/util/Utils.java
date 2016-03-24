package com.example.kimo.daygo_2.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import com.example.kimo.daygo_2.model.db.RecordBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
public class Utils {
    public static String TAG = "这是log--->";
    public static String filePath = Environment.getExternalStorageDirectory() + File.separator +
            "DAYGO_2/video/";//当前保存短视频的地址
    public static String bitmapPath = Environment.getExternalStorageDirectory() + File.separator +
            "DAYGO_2/bitmap/";//当前保存短视频的地址

    /**
     * 当前时间戳
     */
    public static String dataFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = null;
        try {
            dateStr = sdf.format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    public static List<String> getAllFiles(List<String> list, File root) {
        File files[] = root.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFiles(list, f);
                } else {
                    list.add(f.getName());
                }
            }
        }
        return list;
    }

    // 遍历短视频的路径，然后把所有短视频遍历并输出来
    public static List<RecordBean> getRecordList() {
        List<String> list = new ArrayList<>();
        List<RecordBean> recordBeanList = new ArrayList<>();
        File file = new File(filePath);
        File files[] = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFiles(list, f);
                } else {
                    list.add(f.getName());
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {//将文件名传到数据源里
            //Record record = new Record(mList1.get(i), LocationUtils.getCurLocationGPS());
            RecordBean record = new RecordBean(list.get(i), "nanchang");//getLocationByRecName());
            recordBeanList.add(record);
            Log.i(TAG, "FileName: " + list.get(i));
        }

        return recordBeanList;
    }

    /**
     * 获取视频第十帧作为封面
     *
     * @return
     */
    public static Bitmap gitBitMap(String title) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(filePath + title);
        Bitmap bitmap = mmr.getFrameAtTime(10000);
//        System.out.println(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)+);
//        System.out.println(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)+);
        mmr.release();
        return bitmap;
    }

    public static String saveBitmap(String title) {
        Log.e(TAG, "保存图片");
        File parentFile = new File(bitmapPath);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        File f = new File(bitmapPath, title+".jpg");
        if (f.exists()) {
            //f.delete();
            return f.getAbsolutePath();
        }
        Bitmap bitmap = gitBitMap(title);
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存"+f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}
