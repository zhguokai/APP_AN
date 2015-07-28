package org.org.xrhh.util.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOPenHelper
 */
public class DBHelper extends SQLiteOpenHelper {
    //数据库名称
    private static final String DB_NAME = "kjshop.db";
    //数据库版本
    private static final int version = 1;

    //构造函数

    public DBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    //创建数据库时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS person (personid integer primary key autoincrement, name varchar(20), age INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //更新数据库时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //根据版本号升级数据库
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)"); //往表中增加一列


    }


}
