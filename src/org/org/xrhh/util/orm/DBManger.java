package org.org.xrhh.util.orm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.kj.mode.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库管理类
 * Created by kuajie on 15/7/24.
 */
public class DBManger {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManger(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void add(String tabname, List<List<String>> itemList) {
        db.beginTransaction();
        db.delete("person", null, null);
        try {
            StringBuffer sqlStr;

            for (List<String> item : itemList) {
                sqlStr = new StringBuffer();
                sqlStr.append(" insert into ").append(tabname).append(" values(null,\"").append(item.get(0)).append("\" ,")
                        .append(item.get(1)).append(") ");

                //db.execSQL("INSERT INTO " + tabname + " Values" + sql_Params.toString(), params);
                db.execSQL(sqlStr.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public List<Person> query() {
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Person person = new Person();
            person.id = c.getInt(c.getColumnIndex("personid"));
            person.name = c.getString(c.getColumnIndex("name"));
            person.age = c.getInt(c.getColumnIndex("age"));
            // person.info = c.getString(c.getColumnIndex("info"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        return c;
    }


    public void closeDB() {
        db.close();
    }

}
