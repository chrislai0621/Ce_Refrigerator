package com.example.student.ce_refrigerator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.student.ce_refrigerator.Dao.ShoppingListDao;
import com.example.student.ce_refrigerator.EmptyData.shopping_list;

import java.util.List;

import static com.example.student.ce_refrigerator.R.id.listview;

public class Shopping_cart_list extends AppCompatActivity {
    private ListView mListView;
    private ShoppingListDao shoppingListDao;
    private List<shopping_list> listShoppingList;
    private Button btnAction;
    private int selectedIndex;
    private ShoppingListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_list);
        shoppingListDao = new ShoppingListDao(getApplicationContext());
        listShoppingList =shoppingListDao.getAll();
        initListView();
        btnAction = (Button)findViewById(R.id.btnAction);
        btnAction.setText("-");
        //刪除已勾選的資料
        btnAction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Shopping_cart_list.this);
                        builder.setTitle("確認刪除");
                        builder.setMessage("請確認是否刪除已勾選資料?");
                        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for(int j=0 ; j< listShoppingList.size();j++)
                                {
                                    shopping_list o=listShoppingList.get(j);
                                    if(o.is_check())
                                    {
                                        shoppingListDao.delete(o.getId());

                                    }
                                }
                                getData();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();


                    }
                }
        );
    }
    public  List<shopping_list> getShoppingList()
    {
        return  listShoppingList;
    }
    public void updateShoppingList(shopping_list s)
    {
        shoppingListDao.update(s);
        getData();
    }
    private void initListView() {

        adapter= new ShoppingListAdapter(this);
        mListView = (ListView) findViewById(listview);
        mListView.setEmptyView(findViewById(R.id.empty));
        mListView.setAdapter(adapter);



    }


    public void BackPage(View v)
    {
        finish();
    }



    public void getData() {

        listShoppingList =shoppingListDao.getAll();
        adapter=(ShoppingListAdapter)mListView.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
