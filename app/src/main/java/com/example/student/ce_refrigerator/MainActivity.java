package com.example.student.ce_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.EmptyData.food;
import com.example.student.ce_refrigerator.EmptyData.food_list;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private FoodListDao foodListDao;
    private  FoodDao foodDao;
    private List<food_list> list_foodlist ;
    private MainListAdapter adapter;
    private Intent it ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodListDao= new FoodListDao(getApplicationContext());
        foodDao= new FoodDao(getApplicationContext());
        list_foodlist = foodListDao.getAllOrderbyExpiredDate();
        initListView();
        getData();
        it = new Intent(this,item2.class);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    private void initListView() {

        adapter= new MainListAdapter(this);
        mListView = (ListView) findViewById(R.id.listviewMainContent);
        mListView.setEmptyView(findViewById(R.id.empty1));
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final long fid=list_foodlist.get(i).getId();
                it.removeExtra("FOOD_LIST_ID");
                it.putExtra("FOOD_LIST_ID", fid);
                startActivity(it);
            }
        });



    }
    public  List<food_list> getfood_list()
    {
        return  list_foodlist;
    }
    public String getFoodName(long foodId)
    {
        food f = foodDao.get(foodId);
        return f.getName();
    }
    public void getData() {

        list_foodlist = foodListDao.getAllOrderbyExpiredDate();
        adapter=(MainListAdapter) mListView.getAdapter();
        adapter.notifyDataSetChanged();
    }
    public void goFood(View v)
    {
        Intent it = new Intent(this,Item.class);
        startActivity(it);
    }
    public void goCategory(View v)
    {
        Intent it = new Intent(this,Category.class);
        startActivity(it);
    }
    public void goRefrigerator(View v)
    {
        Intent it = new Intent(this,Refrigerator.class);
        startActivity(it);
    }
    public void goShoppingList(View v)
    {
        Intent it = new Intent(this,Shopping_cart_list.class);
        startActivity(it);
    }


}
