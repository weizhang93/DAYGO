package com.example.kimo.daygo_2.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kimo.daygo_2.DayGoCache;
import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.util.SaveImageUtils;
import com.example.kimo.daygo_2.util.ShareUtils;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class ImageActivity extends ToolbarActivity {

    private SimpleDraweeView simpleDraweeView;
    private Toolbar toolbar;

    private static boolean flag = true;
    private String url;
    private String desc;
    private Uri uri;
    private Uri localUri;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DayGoCache.isBackFromWeborImage = true;
        url = getIntent().getStringExtra("url");
        desc = getIntent().getStringExtra("desc");

        initToolbar();
        setAppBarAlpht(0.7f);

        initFloatButton();
        initImageView();
    }

    private void initImageView() {
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.draweview);
        GenericDraweeHierarchy genericDraweeHierarchy = simpleDraweeView.getHierarchy();
        genericDraweeHierarchy.setProgressBarImage(new ProgressBarDrawable());
        uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOrShowToolbar();
            }
        });
    }

    //在悬浮按钮中添加保存图片的事件
    private void initFloatButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "保存图片？", Snackbar.LENGTH_LONG)
                        .setAction("当然！", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                saveImageView();
                            }
                        }).show();
            }
        });
    }

    private void saveImageView() {
        localUri = SaveImageUtils.saveImage(url, desc, this);
        Toast.makeText(this, "已经保存图片啦", Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(desc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                if(!flag){
                    ShareUtils.shareImage(this,localUri);
                }else{
                    Toast.makeText(ImageActivity.this, "你需要先保存图片！！！", Toast
                            .LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.action_save:
                saveImageView();
                flag = false;
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
