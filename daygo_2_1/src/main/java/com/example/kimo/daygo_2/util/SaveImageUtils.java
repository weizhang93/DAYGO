package com.example.kimo.daygo_2.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.kimo.daygo_2.activity.ImageActivity;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by Administrator on 2016/3/7 0007.
 */
public class SaveImageUtils {

    public static Uri uri;

    public static Uri saveImage(final String url, final String desc, final Context context) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        com.facebook.datasource.DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest,context);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (bitmap == null) {
                    Toast.makeText(context, "保存图片失败啦,无法下载图片", Toast.LENGTH_SHORT).show();
                }
                File appDir = new File(Environment.getExternalStorageDirectory(),"DayGo_2");
                if(!appDir.exists()){
                    appDir.mkdir();
                }
                String fileName = desc+".jpeg";
                File file = new File(appDir,fileName);
                try{
                    FileOutputStream fos = new FileOutputStream(file);
                    assert  bitmap != null ;
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                    fos.flush();
                    fos.close();
                }catch (IOException io){
                    io.printStackTrace();
                }

                uri = Uri.fromFile(file);
                //通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri);
                context.sendBroadcast(scannerIntent);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        }, CallerThreadExecutor.getInstance());
        return uri;
    }
}
