package com.example.student.ce_refrigerator.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * Created by Chris on 2017/10/14.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    // 資料庫名稱
    public static final String DATABASE_NAME = "ce_refrigerator_data.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 2;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;

    // 建構子，在一般的應用都不需要修改
    public MyDBHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 建立應用程式需要的表格
        //如果沒有資料表的話，就會執行這
        db.execSQL(CategoryDao.CREATE_TABLE);
        db.execSQL(FoodDao.CREATE_TABLE);
        db.execSQL(FoodListDao.CREATE_TABLE);
        db.execSQL(ShoppingListDao.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //表格有異動時侯執行
        // 刪除原有的表格
        // 刪除原有的表格
        db.execSQL("DROP TABLE IF EXISTS " + CategoryDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FoodDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FoodListDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ShoppingListDao.TABLE_NAME);
        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }
}
