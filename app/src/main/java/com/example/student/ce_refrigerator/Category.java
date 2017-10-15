package com.example.student.ce_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.EmptyData.category;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {
    List<category> listCategory = new ArrayList<>();
    ArrayAdapter<String> adapter;
    List<String> forAdapter = new ArrayList<String>();
    ListView listview;
    Button btnAction;
    CategoryFragment Categorydialog;
    static String  mStatus ="Add";
    static int selectedIndex =0;
// 建立資料庫物件
    CategoryDao categoryDao;
    FoodDao foodDao;
    FoodListDao foodListDao;
    public List<category> getListCategory() {
        return listCategory;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryDao = new CategoryDao(getApplicationContext());
        foodDao = new FoodDao(getApplicationContext());
        foodListDao = new FoodListDao(getApplicationContext());
        btnAction = (Button)findViewById(R.id.btnAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStatus ="Add";
                Categorydialog.show(getSupportFragmentManager(), "CategoryFragment");
            }
        });
        getData();

        initListView();
        Categorydialog = new CategoryFragment();

    }



public void BackPage(View v)
{
    Intent it  = new Intent(Category.this,MainActivity.class);
    startActivity(it);

}

    /**
     * 取得食材大類資料
     */
    public void getData() {
        listCategory =categoryDao.getAll();
        forAdapter.clear();
        for(category o:listCategory)
        {
            forAdapter.add(o.getName());
        }
    }

    public void initListView() {
        listview = (ListView) findViewById(R.id.lvCategory);
        registerForContextMenu(listview);
        adapter = new ArrayAdapter<String>(Category.this, android.R.layout.simple_list_item_1, forAdapter);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(Category.this, SubCategory.class);

                it.putExtra("CATEGORY_ID", listCategory.get(i).getId());
                it.putExtra("CATEGORY_NAME", listCategory.get(i).getName());
                startActivity(it);
                selectedIndex =i;
            }
        });

    }


    /**
     * 取得大類最新id
     *
     * @return
     */
    public int getMaxId() {
        int id = listCategory.size() + 1;
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
                Categorydialog.show(getSupportFragmentManager(), "CategoryFragment");

                break;
            case 2://刪涂
                category category1 = listCategory.get(selectedIndex);
                foodDao.deleteCategory(category1.getId());//刪掉食物名稱清單
                categoryDao.delete(category1.getId());//刪掉食品大類清單
                foodListDao.deleteCategory(category1.getId());//刪掉食物清單
                getData();
                adapter = (ArrayAdapter<String>) listview.getAdapter();
                adapter.notifyDataSetChanged();

                //還需要刪除子目錄的資料，與曾經建過的foodlist
                break;
        }
        return super.onContextItemSelected(item);
    }

    //endregion
    //region 新增、蝙輯、刪除事件


    /**
     * 按下新增按扭對話窗後的ok按扭事件
     *
     * @param Name
     */
    public void setOKMessage(CharSequence Name) {

        categoryDao.insert(new category(Name.toString()));
        getData();
        adapter = (ArrayAdapter<String>) listview.getAdapter();
        adapter.notifyDataSetChanged();

    }
    /**
     * 按下修改按扭對話窗的ok按扭事件
     *
     * @param Name
     */
    public void setEditMessage(CharSequence Name ) {

        category category1 = listCategory.get(selectedIndex);
        category1.setName(Name.toString());
        forAdapter.set(selectedIndex,Name.toString());
        adapter = (ArrayAdapter<String>) listview.getAdapter();
        adapter.notifyDataSetChanged();
        categoryDao.update(category1);


    }
//endregion
}
