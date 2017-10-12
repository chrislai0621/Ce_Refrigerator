package com.example.student.ce_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        //Intent it = new Intent(this,Item.class);
      //  startActivity(it);
    }
    public void goShoppingList(View v)
    {
       // Intent it = new Intent(this,Item.class);
       // startActivity(it);
    }

}
