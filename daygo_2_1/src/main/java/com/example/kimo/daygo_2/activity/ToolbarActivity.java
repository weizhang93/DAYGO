package com.example.kimo.daygo_2.activity;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.kimo.daygo_2.R;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public abstract class ToolbarActivity extends BaseActivity {

    abstract protected int provideContentViewId();

    public void onToolbarClick() {}

    public boolean canBack() {
        return false;
    }

    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());

        mAppBar = (AppBarLayout) findViewById(R.id.appbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException("No toolbar");
        }

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarClick();
            }
        });

        setSupportActionBar(mToolbar);

        if(canBack()){
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if(actionBar!=null){
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void setAppBarAlpht(float alpht){
        mAppBar.setAlpha(alpht);
    }

    protected void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mIsHidden = !mIsHidden;
    }
}
