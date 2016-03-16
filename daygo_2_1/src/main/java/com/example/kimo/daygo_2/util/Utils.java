package com.example.kimo.daygo_2.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
public class Utils {
    public static String TAG = "这是log--->";

    /**
     * 当前时间戳
     */
    public static String dataFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = null ;
        try {
            dateStr = sdf.format(new Date(System.currentTimeMillis()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return dateStr;
    }
}
