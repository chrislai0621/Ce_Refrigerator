package com.example.student.ce_refrigerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.Dao.ShoppingListDao;
import com.example.student.ce_refrigerator.EmptyData.category;
import com.example.student.ce_refrigerator.EmptyData.food;
import com.example.student.ce_refrigerator.EmptyData.food_list;
import com.example.student.ce_refrigerator.EmptyData.shopping_list;

import static com.example.student.ce_refrigerator.SharingMethod.getBitmapFromSDCard;
import static com.example.student.ce_refrigerator.SharingMethod.getPicWidth;

public class item2 extends AppCompatActivity {
    private FoodListDao foodListDao;
    private CategoryDao categoryDao;
    private ShoppingListDao shoppingListDao;
    private FoodDao foodDao;
    private food_list foodList;
    private long foodListid = 0;
    private TextView tvCategory1, tvFood1, tvPurchaseDate1, tvExpiredDate1, tvRb, tvRemark, tvPurchaseAmt, textView2;

    private Button btnAction;
    private category c;
    private food f1;
    private String picPath;
    private int picWidth;

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

        btnAction = (Button) findViewById(R.id.btnAction);
        btnAction.setVisibility(View.GONE);
        picWidth = getPicWidth(item2.this);
        textView2=(TextView) findViewById(R.id.textView2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(picWidth, picWidth);//設定寬高
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(15,15,15,15);
        textView2.setLayoutParams(params);

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
            Bitmap bitmap = getBitmapFromSDCard(foodList.getImg_id());

            if (bitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                textView2.setBackground(bitmapDrawable);
            } else {

                textView2.setBackgroundResource(R.drawable.camera);
            }


        } else {

            textView2.setBackgroundResource(R.drawable.camera);
        }


    }

    public void BackPage(View v) {
        finish();
    }

    public void onclick_delete(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(item2.this);
        builder.setTitle("確認刪除");
        builder.setMessage("請確認是否刪除資料?");
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                foodListDao.delete(foodListid);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    public void onclick_shopping_list(View v) {
        shopping_list sl = new shopping_list(f1.getName(), foodList.getPurchase_amount(), false);
        shoppingListDao.insert(sl);
        Intent it = new Intent(item2.this, Shopping_cart_list.class);
        startActivity(it);
    }


}
