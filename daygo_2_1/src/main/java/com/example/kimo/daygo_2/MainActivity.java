/*
 * Copyright (c) 2016.  weiZhang <ohhecool@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kimo.daygo_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kimo.daygo_2.activity.AboutActivity;
import com.example.kimo.daygo_2.activity.VideoActivity;
import com.example.kimo.daygo_2.activity.WebActivity;
import com.example.kimo.daygo_2.adapter.MyPagerAdapter;
import com.example.kimo.daygo_2.fragment.RecyclerFragmentFactory;
import com.example.kimo.daygo_2.fragment.RecyclerFragmentFactory_new;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager vp;

    private String[] titles = {"资料","录制","记录"};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        initFragment();
        initViewPager();
        initTablayout();
        initFloatButton();

    }

    private void initFragment() {
//        fragments.add(RecyclerFragmentFactory.getInstance(titles[0]));
//        fragments.add(RecyclerFragmentFactory.getInstance(titles[1]));
//        fragments.add(RecyclerFragmentFactory.getInstance(titles[2]));
        fragments.add(RecyclerFragmentFactory_new.getInstance(titles[0]));
        fragments.add(RecyclerFragmentFactory_new.getInstance(titles[1]));
        fragments.add(RecyclerFragmentFactory_new.getInstance(titles[2]));
    }

    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments, titles));
    }

    private void initTablayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(vp);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void testClick(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebActivity.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_daygo) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        if (id == R.id.about_me) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //在悬浮按钮中添加保存图片的事件
    //点击开始录制视频
    private void initFloatButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "录制短视频？", Snackbar.LENGTH_LONG)
                        .setAction("当然！", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //saveImageView();
                                startActivity(new Intent(MainActivity.this,VideoActivity.class));
                            }
                        }).show();

//                Log.d(TAG, "***Video Start***");
//                startActivity(new Intent(MainActivity.this, VideoActivity.class));
            }
        });
    }
}
