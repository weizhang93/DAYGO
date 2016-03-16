package com.example.kimo.daygo_2.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.kimo.daygo_2.R;

/**
 * Created by weiZhang on 2016/3/7 0007.
 * WebActivity 配套使用
 * 浏览器工具类
 *  *复制链接--copyToClipBoart
 *  *在浏览器中打开链接--openInBrowser
 */
public class BrowserUtils {
    public static void copyToClipBoart(Context context,String text){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("daygo",text));
        Toast.makeText(context, R.string.copy_success, Toast.LENGTH_SHORT).show();
    }

    public static void openInBrowser(Context context,String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if(intent.resolveActivity(context.getPackageManager())!=null){
            context.startActivity(intent);
        }else{
            Toast.makeText(context, R.string.open_fail, Toast.LENGTH_SHORT).show();
        }
    }
}
