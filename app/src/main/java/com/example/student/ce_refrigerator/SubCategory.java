
package com.example.student.ce_refrigerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.EmptyData.food;

import java.util.ArrayList;
import java.util.List;

public class SubCategory extends AppCompatActivity {
    long categoryId;
    private String categoryName;
    private List<food> listSubCategory = new ArrayList<>();
    private  ArrayAdapter<String> adapter;
    private  ArrayList<String> forAdapter = new ArrayList<String>();
    private  ListView listview;
    private  Button btnAction;
    private    FoodCategoryFragment SubCategorydialog;
    static String  mStatus ="Add";
    static int selectedIndex =0;
TextView tvCategory;
    // 建立資料庫物件
    FoodDao foodDao  ;
    FoodListDao foodListDao;
    public List<food> getListSubCategory() {
        return listSubCategory;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        foodDao = new FoodDao(getApplicationContext());
        foodListDao = new FoodListDao(getApplicationContext());
        Intent it =getIntent();
        categoryId=it.getLongExtra("CATEGORY_ID",0);
        categoryName =it.getStringExtra("CATEGORY_NAME");
        btnAction = (Button)findViewById(R.id.btnAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStatus ="Add";
                SubCategorydialog.show(getSupportFragmentManager(), "FoodCategoryFragment");

            }
        });
        getData();
        initListView();
        tvCategory  =(TextView)findViewById(R.id.tvCategory);
        SubCategorydialog = new FoodCategoryFragment();
        registerForContextMenu(listview);//設定listview有menu
        tvCategory.setText("食材類別："+ categoryName);

    }


    public void BackPage(View v)
    {
        finish();
    }

    /**
     * 取得食物資料
     */
    public void getData() {

        listSubCategory =foodDao.getAll(categoryId);
        forAdapter.clear();
        for(food o:listSubCategory)
        {
            forAdapter.add(o.getName());
        }
    }

    public void initListView() {
        listview = (ListView) findViewById(R.id.lvSubCategory);
        adapter = new ArrayAdapter<String>(SubCategory.this, android.R.layout.simple_list_item_1, forAdapter);
        listview.setAdapter(adapter);


    }


    /**
     * 取得大類最新id
     *
     * @return
     */
    public int getMaxId() {
        int id = listSubCategory.size() + 1;
        return id;

    }
    //region listview長按後產生的menu(修改與刪除)

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(0, 1, 0, "修改");//groupid,itemid,order,選單名稱
        menu.add(0, 2, 0, "刪除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //取得listview選取資訊
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        selectedIndex=  info.position;
        switch (item.getItemId()) {
            case 1://修改
                mStatus ="Edit";
                SubCategorydialog.show(getSupportFragmentManager(), "FoodCategoryFragment");

                break;
            case 2://刪涂

                AlertDialog.Builder builder = new AlertDialog.Builder(SubCategory.this);
                builder.setTitle("確認刪除");
                builder.setMessage("請確認是否刪除本筆資料,建過的食材資料也會一併刪除?");
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        food food1 = listSubCategory.get(selectedIndex);
                        foodDao.delete(food1.getId());
                        foodListDao.deleteSubCategory(food1.getId());
                        getData();

                        adapter = (ArrayAdapter<String>) listview.getAdapter();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //endregion
    //region 新增、蝙輯、刪除事件


//    /**
//     * 按下新增按扭，顯示新增的對話框
//     *
//     * @param v
//     */
//    public void clickFoodFragment(View v) {
//        mStatus ="Add";
//        SubCategorydialog.show(getSupportFragmentManager(), "FoodCategoryFragment");
//
//    }
    /**
     * 按下新增按扭對話窗後的ok按扭事件
     *
     * @param Name
     */
    public void setOKMessage(CharSequence Name) {
        int id = getMaxId();
        foodDao.insert(new food(categoryId, Name.toString()));
        getData();
        adapter = (ArrayAdapter<String>) listview.getAdapter();
        adapter.notifyDataSetChanged();

        //新增資料到資料庫

    }
    /**
     * 按下修改按扭對話窗的ok按扭事件
     *
     * @param Name
     */
    public void setEditMessage(CharSequence Name ) {

        food food1 = listSubCategory.get(selectedIndex);
        food1.setName(Name.toString());
        forAdapter.set(selectedIndex,Name.toString());
        adapter = (ArrayAdapter<String>) listview.getAdapter();
        adapter.notifyDataSetChanged();
        foodDao.update(food1);
    }
//endregion
}
