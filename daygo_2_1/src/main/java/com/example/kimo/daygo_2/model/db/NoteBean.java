package com.example.kimo.daygo_2.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Administrator on 2016/3/13 0013.
 */
@Table(database = DayGoDB.class)
public class NoteBean extends BaseModel {
    @PrimaryKey(autoincrement = true)
    int id;
    @Column
    public String who;//标题
    @Column
    public String publishedAt;
    @Column
    public String desc;//note内容
    @Column
    public String type;
    @Column
    public String url;
    @Column
    public String used;
    @Column
    public String objectId;
    @Column
    public String createdAt;
    @Column
    public String updatedAt;
}
