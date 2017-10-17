package com.example.student.ce_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Calendar;
public class Item extends AppCompatActivity {
    Button btnAction;
    EditText etExpiredDate, etPurchaseDate;
    private int myYear, myMonth, myDay, myHour, myMinute;
    static final int ID_DATEPICKER = 0;
    static final int ID_TIMEPICKER = 1;
    static String mDateItem = "purchase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
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
    }

    public void BackPage(View v) {
        Intent it = new Intent(Item.this, MainActivity.class);
        startActivity(it);

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
