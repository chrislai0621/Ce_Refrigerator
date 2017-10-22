package com.example.student.ce_refrigerator;

import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.EmptyData.category;
import com.example.student.ce_refrigerator.EmptyData.food_list;

import java.util.ArrayList;
import java.util.List;

public class Refrigerator extends AppCompatActivity {
    private Button btnAction;
    private CategoryDao categoryDao;
    private  FoodListDao foodListDao;
    private    FoodDao foodDao;
    private List<category> listCategory = new ArrayList<>();
    private List<food_list> listfoodlist = new ArrayList<>();
    private  RefrigeratorFragment refrigeratorFragment1 ;
    private  RefrigeratorFragment refrigeratorFragment2 ;
    private FragmentManager mFragmentMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerator);
        refrigeratorFragment1 = new RefrigeratorFragment(0);
        refrigeratorFragment2 = new RefrigeratorFragment(1);

        mFragmentMgr =getFragmentManager();
        mFragmentMgr.beginTransaction()
                .replace(R.id.main_layout,refrigeratorFragment1)
                .commit();
        btnAction =(Button)findViewById(R.id.btnAction);
        btnAction.setVisibility(View.GONE);

    }
    public void BackPage(View v) {
        finish();
    }

    public  void onClickFoodList(View v)
    {


        switch (v.getId())
        {
            case R.id.btnCold:


                mFragmentMgr.beginTransaction()
                        .replace(R.id.main_layout,refrigeratorFragment1)
                        .commit();
                break;
            case R.id.btnFreezing:


                mFragmentMgr.beginTransaction()
                        .replace(R.id.main_layout,refrigeratorFragment2)
                        .commit();
                break;
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refrigeratorFragment1 = new RefrigeratorFragment(0);
        mFragmentMgr.beginTransaction()
                .replace(R.id.main_layout,refrigeratorFragment1)
                .commit();
    }
}
