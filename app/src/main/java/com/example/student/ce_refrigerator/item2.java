package com.example.student.ce_refrigerator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.Dao.ShoppingListDao;
import com.example.student.ce_refrigerator.EmptyData.category;
import com.example.student.ce_refrigerator.EmptyData.food;
import com.example.student.ce_refrigerator.EmptyData.food_list;
import com.example.student.ce_refrigerator.EmptyData.shopping_list;

import java.io.File;

import static com.example.student.ce_refrigerator.R.id.ImageView1;

public class item2 extends AppCompatActivity {
    private FoodListDao foodListDao;
    private CategoryDao categoryDao;
    private ShoppingListDao shoppingListDao;
    private FoodDao foodDao;
    private food_list foodList;
    private long foodListid = 0;
    private TextView tvCategory1, tvFood1, tvPurchaseDate1, tvExpiredDate1, tvRb, tvRemark, tvPurchaseAmt;
    private ImageView imageView;
    private Button btnAction;
    private category c;
    private food f1;
    private String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item2);
        initView();
        Intent it = getIntent();
        foodListid = it.getLongExtra("FOOD_LIST_ID", 0);
        foodListDao = new FoodListDao(getApplicationContext());
        categoryDao = new CategoryDao(getApplicationContext());
        foodDao = new FoodDao(getApplicationContext());
        shoppingListDao = new ShoppingListDao(getApplicationContext());
        foodList = foodListDao.get(foodListid);
        setValue();
    }

    private void initView() {
        tvCategory1 = (TextView) findViewById(R.id.tvCategory1);
        tvFood1 = (TextView) findViewById(R.id.tvFood1);
        tvPurchaseDate1 = (TextView) findViewById(R.id.tvPurchaseDate1);
        tvExpiredDate1 = (TextView) findViewById(R.id.tvExpiredDate1);
        tvRb = (TextView) findViewById(R.id.tvRb);
        tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvPurchaseAmt = (TextView) findViewById(R.id.tvPurchaseAmt);
        imageView = (ImageView) findViewById(ImageView1);
        btnAction = (Button) findViewById(R.id.btnAction);
        btnAction.setVisibility(View.GONE);
    }

    private void setValue() {
        c = categoryDao.get(foodList.getCategory_id());
        f1 = foodDao.get(foodList.getFood_id());
        tvCategory1.setText(c.getName());
        tvFood1.setText(f1.getName());
        tvPurchaseDate1.setText(foodList.getPurchase_date());
        tvExpiredDate1.setText(foodList.getExpired_date());
        switch (foodList.getStorage()) {
            case 0:
                tvRb.setText("冷藏");
                break;
            case 1:
                tvRb.setText("冷凍");
                break;
        }
        tvRemark.setText(foodList.getRemark());
        tvPurchaseAmt.setText(String.valueOf(foodList.getPurchase_amount()));
        if (!foodList.getImg_id().isEmpty()) {
            imageView.setImageBitmap(getBitmapFromSDCard(foodList.getImg_id()));
        } else {
            imageView.setImageResource(R.drawable.camera);
        }

    }

    public void BackPage(View v) {
        finish();
    }

    public void onclick_delete(View v) {
        foodListDao.delete(foodListid);
        finish();
    }

    public void onclick_shopping_list(View v) {
        shopping_list sl = new shopping_list(f1.getName(), foodList.getPurchase_amount(), false);
        shoppingListDao.insert(sl);
        Intent it = new Intent(item2.this, Shopping_cart_list.class);
        startActivity(it);
    }

    //讀取SDCard圖片，型態為Bitmap
    private static Bitmap getBitmapFromSDCard(String file) {
        try {
            String sd = Environment.getExternalStorageDirectory().toString();
            sd = sd + File.separator + "CeRefrigerator" + File.separator;
            Bitmap bitmap = BitmapFactory.decodeFile(sd + file);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
