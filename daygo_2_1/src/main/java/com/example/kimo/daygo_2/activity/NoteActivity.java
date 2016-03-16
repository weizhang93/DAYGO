package com.example.kimo.daygo_2.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kimo.daygo_2.DayGoCache;
import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.model.bean.Result;
import com.example.kimo.daygo_2.util.DaoUtils;

public class NoteActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDesc;

    private String mTitle;
    private String mDesc;

    private Result mResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        DayGoCache.isBackFromWeborImage = true;

        initView();
        initToolbar();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initView() {
        etTitle = (EditText) findViewById(R.id.et_title);
        etDesc = (EditText) findViewById(R.id.et_desc);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return false;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this).setTitle(R.string.sure_to_save)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteActivity.this.finish();
                    }
                })
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save();
                        NoteActivity.this.finish();
                        //MyApplication.removeAllActivity();
                    }
                })
                .show();
    }

    private void save() {
        mTitle = etTitle.getText().toString();
        mDesc  = etDesc.getText().toString();

        if(!checkNull(mTitle,mDesc)){
            mResult = new Result();
            //Toast.makeText(NoteActivity.this, "test success!", Toast.LENGTH_SHORT).show();
            mResult.setWho(mTitle);
            mResult.setDesc(mDesc);

            DaoUtils.addNote(mResult);//保存数据到表中
        }

    }

    /**
     * 判断标题以及内容是否为空，如果都为空，返回true
     * @param mTitle
     * @param mDesc
     * @return
     */
    private boolean checkNull(String mTitle, String mDesc) {
        if((mTitle.equals("")||mTitle==null)&&(mDesc.equals("")||mDesc==null)){
            return true;
        }else{
            return false;
        }
    }
}
