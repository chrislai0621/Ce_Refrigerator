package com.example.student.ce_refrigerator.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.student.ce_refrigerator.EmptyData.shopping_list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2017/10/14.
 */

public class ShoppingListDao {
    // 表格名稱
    public static final String TABLE_NAME = "shopping_list";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String FOOD_NAME_COLUMN = "food_name";
    public static final String PURCHASE_AMOUNT_COLUMN = "purchase_amount";
    public static final String IS_CHECK_COLUMN = "is_check";


    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FOOD_NAME_COLUMN + " TEXT NOT NULL ," +
                    PURCHASE_AMOUNT_COLUMN + " REAL  NOT  NULL ," +
                    IS_CHECK_COLUMN + " INTEGER DEFAULT 0"+
                    ")";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public ShoppingListDao(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public shopping_list insert(shopping_list item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(FOOD_NAME_COLUMN, item.getFood_name());
        cv.put(PURCHASE_AMOUNT_COLUMN, item.getPurchase_amount());
        cv.put(IS_CHECK_COLUMN, item.is_check());


        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        item.setId(id);
        // 回傳結果
        return item;
    }

    // 修改參數指定的物件
    public boolean update(shopping_list item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(FOOD_NAME_COLUMN, item.getFood_name());
        cv.put(PURCHASE_AMOUNT_COLUMN, item.getPurchase_amount());
        cv.put(IS_CHECK_COLUMN, item.is_check());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<shopping_list> getAll() {
        List<shopping_list> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {


            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public shopping_list get(long id) {
        // 準備回傳結果用的物件
        shopping_list item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public shopping_list getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Boolean isCheck =false;
        if(cursor.getInt(3)==1)
        {
            isCheck = true;
        }

        shopping_list result = new shopping_list(cursor.getLong(0),cursor.getString(1),cursor.getDouble(2)
                ,isCheck);

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }


}
