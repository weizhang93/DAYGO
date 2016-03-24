package com.example.kimo.daygo_2.fragment;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimo.daygo_2.DayGoCache;
import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.activity.NoteActivity;
import com.example.kimo.daygo_2.adapter.ArticleAdapter;
import com.example.kimo.daygo_2.adapter.GirlAdapter;
import com.example.kimo.daygo_2.adapter.NoteAdapter;
import com.example.kimo.daygo_2.adapter.RecordAdapter;
import com.example.kimo.daygo_2.model.bean.DataResult;
import com.example.kimo.daygo_2.model.bean.Result;
import com.example.kimo.daygo_2.model.db.RecordBean;
import com.example.kimo.daygo_2.retrofit.DayGoSingle;
import com.example.kimo.daygo_2.util.DaoUtils;
import com.example.kimo.daygo_2.util.Utils;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class RecyclerFragmentFactory_new extends Fragment implements View.OnClickListener {

    private View view;
    private SwipyRefreshLayout swipyRefreshLayout;
    private RecyclerView recyclerView;
    private GirlAdapter girlAdapter;
    private ArticleAdapter articleAdapter;
    private NoteAdapter noteAdapter;
    private RecordAdapter recordAdapter;

    private LinearLayout linearLayout_1, linearLayout_2;
    private ImageView imageView_1, imageView_2;
    private TextView textView_1, textView_2, textView_3, textView_4, textView_5, textView_6;
    private CardView cardView;

    private List<Result> girl_list = new ArrayList<>();
    private List<Result> article_list = new ArrayList<>();
    private List<Result> note_list = new ArrayList<>();
    private List<RecordBean> recordBean_list = new ArrayList<>();

    private static final String TAG_TITLE = "title";
    private String mTitle;
    private static int NOW_PAGE_GIRL = 1;
    private static int NOW_PAGE_ART = 1;
    private static final int girl_num = 20;
    public static String mType = "Android";

    public static RecyclerFragmentFactory_new getInstance(String title) {
        RecyclerFragmentFactory_new factory = new RecyclerFragmentFactory_new();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_TITLE, title);
        factory.setArguments(bundle);
        return factory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = (String) bundle.get(TAG_TITLE);
        DayGoCache.isBackFromWeborImage = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        initView(view);
        initRecyclerView(view);
        initRefreshLayout(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clearAllMenu();
        loadDbData();
        if (DayGoCache.isBackFromWeborImage == false) {
            loadData(true, mType);
        }
    }

    //初始化各个Fragment
    private void initView(View view) {
        initView_a(view);
        switch (mTitle) {
            case "资料":
                showMenu();
                break;
            case "录制":
                hideMenu();
                break;
            case "记录":
                hideMenu();
                cardView.setVisibility(View.VISIBLE);
                break;
        }
    }



    //初始化刷新框架
    private void initRefreshLayout(View view) {
        swipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark
        );

        swipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);

        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Observable.timer(3, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                swipyRefreshLayout.setRefreshing(false);
                            }
                        });
                loadData(direction == SwipyRefreshLayoutDirection.TOP ? true : false, mType);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        switch (mTitle) {
            case "资料":
                //设置摆放模式
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                //先出入空值
                articleAdapter = new ArticleAdapter(getActivity(), null);
                recyclerView.setAdapter(articleAdapter);
                break;
            case "录制":
//                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                girlAdapter = new GirlAdapter(getActivity(), null);
//                recyclerView.setAdapter(girlAdapter);
//                break;
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recordAdapter = new RecordAdapter(getActivity(),null);
                recyclerView.setAdapter(recordAdapter);
                break;
            case "记录":
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                noteAdapter = new NoteAdapter(getActivity(), null);
                recyclerView.setAdapter(noteAdapter);
                break;
        }
    }

    private void loadDbData() {
        clearAdapterResult();
        switch (mTitle) {
            case "资料":
                article_list.clear();
                article_list.addAll(DaoUtils.getArticle_list());
                break;
            case "录制":
                recordBean_list.clear();
                recordBean_list.addAll(Utils.getRecordList());
                Log.d("FileNamea", "dealDataInRecyclerView: " + recordBean_list.size());
                break;
            case "记录":
                note_list.clear();
                note_list.addAll(DaoUtils.getNote_list());
                break;
        }
        dealDataInRecyclerView(article_list, recordBean_list);
    }

    private void dealDataInRecyclerView(List<Result> article_list, List<RecordBean> results) {
        switch (mTitle) {
            case "资料":
                articleAdapter.getResults().addAll(article_list);
                articleAdapter.notifyDataSetChanged();
                NOW_PAGE_ART++;
                break;
            case "录制":
                recordAdapter.getResults().addAll(results);
                recordAdapter.notifyDataSetChanged();
                break;
            case "记录":
                noteAdapter.getResults().addAll(note_list);
                noteAdapter.notifyDataSetChanged();
                NOW_PAGE_GIRL++;
                break;
        }
    }

    private void clearAdapterResult() {
        switch (mTitle) {
            case "资料":
                articleAdapter.getResults().clear();
                break;
            case "录制":
                recordAdapter.getResults().clear();
                break;
            case "记录":
                noteAdapter.getResults().clear();
                break;
        }
    }

    private void loadData(boolean isTop, String type) {
        switch (mTitle) {
            case "资料":
                if (isTop) {
                    NOW_PAGE_ART = 1;
                }
                getDataResults(type, girl_num, NOW_PAGE_ART, isTop);
                break;
            case "录制":
                break;
            case "记录":
                //直接从数据库中取数据
                break;
        }
    }

    private void getDataResults(String type, int number, int page, final boolean isTop) {
        Log.d("kimo", "getDataResults: " + type);
        DayGoSingle.getInstance().getDataResults(type, number, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataResult>() {
                    @Override
                    public void onCompleted() {
                        Log.d("frag", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("frag", "onError: " + e.getMessage(), e);
                        Toast.makeText(getActivity(), "请先选择要更新的模块...\n网络不顺畅嘞,更新不了数据啦", Toast
                                .LENGTH_SHORT)
                                .show();
                    }
                    @Override
                    public void onNext(DataResult dataResult) {
                        if (dataResult.isError() == true) {
                            Toast.makeText(getActivity(), "啊擦，服务器出问题啦", Toast.LENGTH_SHORT).show();
                        } else {
                            if(isTop){
                                if(mType=="福利"){
                                    girl_list =dataResult.getResults();
                                    saveGirlInDb(girl_list);
                                }else{
                                    article_list =dataResult.getResults();
                                    saveDataInDb(article_list);
                                }
                                clearAdapterResult();
                            }
                            dealDataInRecyclerView(dataResult.getResults(), recordBean_list);
                        }
                    }
                });
    }

    private void saveDataInDb(List<Result> results) {
        DaoUtils.addArticle_list(results);
    }

    private void saveGirlInDb(List<Result> results) {
        DaoUtils.addGirl_list(results);
    }

    @Override
    public void onClick(View v) {
        clearAllMenu();
        NOW_PAGE_ART = 1;
        switch (v.getId()) {
            case R.id.tv_1:
                textView_1.setBackgroundResource(R.color.menu_pressed);
                mType = "Android";
                loadData(true, "Android");
                break;
            case R.id.tv_2:
                textView_2.setBackgroundResource(R.color.menu_pressed);
                mType = "iOS";
                loadData(true, "iOS");
                break;
            case R.id.tv_3:
                textView_3.setBackgroundResource(R.color.menu_pressed);
                mType = "前端";
                loadData(true, "前端");
                break;
            case R.id.tv_4:
                textView_4.setBackgroundResource(R.color.menu_pressed);
                mType = "拓展资源";
                loadData(true, "拓展资源");
                break;
            case R.id.tv_5:
                textView_5.setBackgroundResource(R.color.menu_pressed);
                mType = "瞎推荐";
                loadData(true, "瞎推荐");
                break;
            case R.id.tv_6:
                textView_6.setBackgroundResource(R.color.menu_pressed);
                mType = "福利";
                loadData(true, "福利");
                break;
            case R.id.cv_add:
                startActivity(new Intent(getContext(), NoteActivity.class));
                break;
        }
    }

    //将按钮颜色统一更换为白色
    private void clearAllMenu() {
        textView_1.setBackgroundResource(R.color.menu_open);
        textView_2.setBackgroundResource(R.color.menu_open);
        textView_3.setBackgroundResource(R.color.menu_open);
        textView_4.setBackgroundResource(R.color.menu_open);
        textView_5.setBackgroundResource(R.color.menu_open);
        textView_6.setBackgroundResource(R.color.menu_open);
    }

    private void initView_a(View view) {//FFEDEDED
        linearLayout_1 = (LinearLayout) view.findViewById(R.id.ll_1);
        linearLayout_2 = (LinearLayout) view.findViewById(R.id.ll_2);
        imageView_1 = (ImageView) view.findViewById(R.id.iv_1);
        imageView_2 = (ImageView) view.findViewById(R.id.iv_2);
        textView_1 = (TextView) view.findViewById(R.id.tv_1);
        textView_2 = (TextView) view.findViewById(R.id.tv_2);
        textView_3 = (TextView) view.findViewById(R.id.tv_3);
        textView_4 = (TextView) view.findViewById(R.id.tv_4);
        textView_5 = (TextView) view.findViewById(R.id.tv_5);
        textView_6 = (TextView) view.findViewById(R.id.tv_6);
        cardView = (CardView) view.findViewById(R.id.cv_add);

        textView_1.setOnClickListener(this);
        textView_2.setOnClickListener(this);
        textView_3.setOnClickListener(this);
        textView_4.setOnClickListener(this);
        textView_5.setOnClickListener(this);
        textView_6.setOnClickListener(this);
        cardView.setOnClickListener(this);

    }

    private void hideMenu() {
        linearLayout_1.setVisibility(View.GONE);
        linearLayout_2.setVisibility(View.GONE);
        imageView_1.setVisibility(View.GONE);
        imageView_2.setVisibility(View.GONE);
    }

    private void showMenu() {
        linearLayout_1.setVisibility(View.VISIBLE);
        linearLayout_2.setVisibility(View.VISIBLE);
        imageView_1.setVisibility(View.VISIBLE);
        imageView_2.setVisibility(View.VISIBLE);
    }
}
