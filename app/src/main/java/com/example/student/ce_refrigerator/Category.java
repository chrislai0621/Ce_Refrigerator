package com.example.student.ce_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.student.ce_refrigerator.EmptyData.category;

import java.util.ArrayList;

public class Category extends AppCompatActivity {
    ArrayList<category> listCategory = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> forAdapter = new ArrayList<String>();
    ListView listview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getData();
        initListView();
    }

    /**
     * 取得大類資料
     */
    public  void getData()
    {
        for(int i=0 ; i<20;i++)
        {
            category AddCategory = new category(i,"名稱"+i);
            listCategory.add(AddCategory);
            forAdapter.add("名稱"+i);
        }

    }
    public  void initListView()
    {
        listview =(ListView)findViewById(R.id.lvCategory);
        adapter = new ArrayAdapter<String>(Category.this, android.R.layout.simple_list_item_1, forAdapter);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it= new Intent(Category.this   ,SubCategory.class);
                it.putExtra("ID",listCategory.get(i).getId());
                startActivity(it);
            }
        });
    }
}
