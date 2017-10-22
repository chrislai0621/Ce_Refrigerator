package com.example.student.ce_refrigerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.EmptyData.category;
import com.example.student.ce_refrigerator.EmptyData.food;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Item extends AppCompatActivity {
    Button btnAction;
    EditText etExpiredDate, etPurchaseDate;
    Spinner spCategory, spFood;
    private int myYear, myMonth, myDay, myHour, myMinute;
    static final int ID_DATEPICKER = 0;
    static final int ID_TIMEPICKER = 1;
    static String mDateItem = "purchase";
    List<category> listCategory = new ArrayList<>();
    List<food> listfood = new ArrayList<>();
    ArrayList<String> arrayCategory = new ArrayList<>();
    ArrayList<String> arrayFood = new ArrayList<>();
    CategoryDao categoryDao ;
    FoodDao foodDao;
    ArrayAdapter<String> _AdapterCategory ;
    ArrayAdapter<String> _AdapterFood ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        categoryDao = new CategoryDao(getApplicationContext());
        foodDao = new FoodDao(getApplicationContext());

        initView();
        setSpinnerAdapter();


    }

    private void initView() {
        btnAction = (Button) findViewById(R.id.btnAction);
        btnAction.setText("OK");
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        etExpiredDate = (EditText) findViewById(R.id.etExpiredDate);
        etPurchaseDate = (EditText) findViewById(R.id.etPurchaseDate);
        etExpiredDate.setOnClickListener(datePickerOnClickListener);
        etPurchaseDate.setOnClickListener(datePickerOnClickListener);
        Calendar mCal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy/MM/dd", mCal.getTime());
        etPurchaseDate.setText(s.toString());
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spFood = (Spinner) findViewById(R.id.spFood);
    }
    private  void setSpinnerAdapter()
    {
        _AdapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayCategory);
        _AdapterFood = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayFood);
        listCategory = categoryDao.getAll();
        for(category o :listCategory)
        {
            arrayCategory.add(o.getName());
        }


        spCategory.setAdapter(_AdapterCategory);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                long categoryid=listCategory.get(i).getId();
                listfood =foodDao.getAll(categoryid);
                arrayFood.clear();
                for(food o :listfood)
                {
                    arrayFood.add(o.getName());
                }
                spFood.setAdapter(_AdapterFood);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void BackPage(View v) {
        finish();
    }


    //觸發採購日期與到期日期的日期視窗
    private EditText.OnClickListener datePickerOnClickListener
            = new EditText.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.etPurchaseDate:
                    mDateItem = "purchase";
                    break;
                case R.id.etExpiredDate:
                    mDateItem = "expired";
                    break;
            }
            DateFragment newFragment = new DateFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }
    };

    //供DateFragment傳回date值
    public void setDate(String date) {
        switch (mDateItem) {
            case "purchase":
                etPurchaseDate.setText(date.toString());
                break;
            case "expired":
                etExpiredDate.setText(date.toString());
                break;
        }
    }

}
