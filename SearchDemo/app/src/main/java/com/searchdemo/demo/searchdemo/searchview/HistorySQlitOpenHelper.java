package com.searchdemo.demo.searchdemo.searchview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/3/19 0019 17:17
 * @ Description :     历史搜索记录数据库
 */

public class HistorySQlitOpenHelper extends SQLiteOpenHelper {
    public static String dbName="history.db";
    public HistorySQlitOpenHelper(Context context) {
        this(context, dbName, null, 1);
    }
    public HistorySQlitOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table records(id integer primary key autoincrement,name varchar(200))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
