package com.example.kimo.daygo_2.util;

import com.example.kimo.daygo_2.model.bean.Result;
import com.example.kimo.daygo_2.model.db.ArticleBean;
import com.example.kimo.daygo_2.model.db.GirlBean;
import com.example.kimo.daygo_2.model.db.NoteBean;
import com.example.kimo.daygo_2.model.db.NoteBean_Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class DaoUtils {
    public static void addGirl_list(List<Result> girl_list) {
        Delete.table(GirlBean.class);
        for (Result result : girl_list) {
            GirlBean girlBean = new GirlBean();
            girlBean.who = result.getWho();
            girlBean.publishedAt = result.getPublishedAt();
            girlBean.desc = result.getDesc();
            girlBean.type = result.getType();
            girlBean.url = result.getUrl();
            girlBean.used = result.getUsed();
            girlBean.objectId = result.getObjectId();
            girlBean.createdAt = result.getCreatedAt();
            girlBean.updatedAt = result.getUpdatedAt();
            girlBean.save();
        }
    }

    public static List<Result> getGirl_list() {
        List<Result> results = new ArrayList<>();
        List<GirlBean> girlBeans = new Select().from(GirlBean.class).queryList();
        for (int i = 0; i < girlBeans.size(); i++) {
            Result result = new Result();
            result.setWho(girlBeans.get(i).who);
            result.setPublishedAt(girlBeans.get(i).who);
            result.setDesc(girlBeans.get(i).desc);
            result.setType(girlBeans.get(i).type);
            result.setUrl(girlBeans.get(i).url);
            result.setUsed(girlBeans.get(i).used);
            result.setObjectId(girlBeans.get(i).objectId);
            result.setCreatedAt(girlBeans.get(i).createdAt);
            result.setUpdatedAt(girlBeans.get(i).updatedAt);
            results.add(result);
        }
        return results;
    }


    public static void addArticle_list(List<Result> results) {
        Delete.table(ArticleBean.class);
        for (Result result : results) {
            ArticleBean partDbBean = new ArticleBean();
            partDbBean.who = result.getWho();
            partDbBean.publishedAt = result.getPublishedAt();
            partDbBean.desc = result.getDesc();
            partDbBean.type = result.getType();
            partDbBean.url = result.getUrl();
            partDbBean.used = result.getUsed();
            partDbBean.objectId = result.getObjectId();
            partDbBean.createdAt = result.getCreatedAt();
            partDbBean.updatedAt = result.getUpdatedAt();
            partDbBean.save();
        }

    }

    public static List<Result> getArticle_list() {
        List<Result> results = new ArrayList<>();
        List<ArticleBean> partDbBeen = new Select().from(ArticleBean.class).queryList();
        for (int i = 0; i < partDbBeen.size(); i++) {
            Result result = new Result();
            result.setWho(partDbBeen.get(i).who);
            result.setPublishedAt(partDbBeen.get(i).publishedAt);
            result.setDesc(partDbBeen.get(i).desc);
            result.setType(partDbBeen.get(i).type);
            result.setUrl(partDbBeen.get(i).url);
            result.setUsed(partDbBeen.get(i).used);
            result.setObjectId(partDbBeen.get(i).objectId);
            result.setCreatedAt(partDbBeen.get(i).createdAt);
            result.setUpdatedAt(partDbBeen.get(i).updatedAt);
            results.add(result);
        }
        return results;
    }

    /**
     * @param note_list 保存便签内容
     */
    public static void addNote_list(List<Result> note_list) {
        Delete.table(NoteBean.class);
        for (Result result : note_list) {
            NoteBean girlBean = new NoteBean();
            girlBean.who = result.getWho();
            girlBean.publishedAt = result.getPublishedAt();
            girlBean.desc = result.getDesc();
            girlBean.type = result.getType();
            girlBean.url = result.getUrl();
            girlBean.used = result.getUsed();
            girlBean.objectId = result.getObjectId();
            girlBean.createdAt = result.getCreatedAt();
            girlBean.updatedAt = result.getUpdatedAt();
            girlBean.save();
        }
    }

    public static List<Result> getNote_list() {
        List<Result> results = new ArrayList<>();
        List<NoteBean> girlBeans = new Select().from(NoteBean.class).orderBy(NoteBean_Table.id,false)
                .queryList();
        for (int i = 0; i < girlBeans.size(); i++) {
            Result result = new Result();
            result.setWho(girlBeans.get(i).who);
            result.setPublishedAt(girlBeans.get(i).who);
            result.setDesc(girlBeans.get(i).desc);
            result.setType(girlBeans.get(i).type);
            result.setUrl(girlBeans.get(i).url);
            result.setUsed(girlBeans.get(i).used);
            result.setObjectId(girlBeans.get(i).objectId);
            result.setCreatedAt(girlBeans.get(i).createdAt);
            result.setUpdatedAt(girlBeans.get(i).updatedAt);
            results.add(result);
        }
        return results;
    }

    public static void addNote(Result result) {
        //Delete.table(NoteBean.class);
        NoteBean girlBean = new NoteBean();
        girlBean.who = result.getWho();
        girlBean.publishedAt = result.getPublishedAt();
        girlBean.desc = result.getDesc();
        girlBean.type = result.getType();
        girlBean.url = result.getUrl();
        girlBean.used = result.getUsed();
        girlBean.objectId = result.getObjectId();
        girlBean.createdAt = result.getCreatedAt();
        girlBean.updatedAt = result.getUpdatedAt();
        girlBean.save();
    }

}
