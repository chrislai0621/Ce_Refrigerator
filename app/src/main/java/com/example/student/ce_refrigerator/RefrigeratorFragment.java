package com.example.student.ce_refrigerator;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.student.ce_refrigerator.SharingMethod;
import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.EmptyData.category;
import com.example.student.ce_refrigerator.EmptyData.food;
import com.example.student.ce_refrigerator.EmptyData.food_list;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.student.ce_refrigerator.R.id.textView2;
import static com.example.student.ce_refrigerator.SharingMethod.getBitmapFromSDCard;
import static com.example.student.ce_refrigerator.SharingMethod.getPicWidth;


/**
 * A simple {@link Fragment} subclass.
 */
public class RefrigeratorFragment extends android.app.Fragment {
    private Intent it;
    private int storage = 0;
    private int i = 1;
    private CategoryDao categoryDao;
    private FoodListDao foodListDao;
    private FoodDao foodDao;
    private List<category> listCategory = new ArrayList<>();
    private List<food_list> listfoodlist = new ArrayList<>();

    public RefrigeratorFragment() {
        // Required empty public constructor
    }

    public RefrigeratorFragment(int storage) {
        this.storage = storage;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = new View(getActivity());
        v = inflater.inflate(R.layout.fragment_refrigerator, container, false);
        View ll = v.findViewById(R.id.linearLayoutRefrigerator);
        categoryDao = new CategoryDao(getActivity());

        foodListDao = new FoodListDao(getActivity());
        foodDao = new FoodDao(getActivity());
        listCategory = categoryDao.getAll();
        it = new Intent((Refrigerator) getActivity(), item2.class);

        // 取得螢幕解析度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        int vWidth = getPicWidth(getActivity());

        for (category c : listCategory) {
            listfoodlist = foodListDao.getCategory(c.getId(), storage);
            if (listfoodlist.size() > 0) {
                TextView tvName = new TextView(v.getContext());
                tvName.setText("食品類別：" + c.getName());
                tvName.setTextColor(Color.parseColor("#245883")); //設定文字顏色
                tvName.setTextSize(20); //設定文字大小
                //ll.addView(tvName);
                ((LinearLayout) ll).addView(tvName);
                int j = 1;

                LinearLayout l2 = new LinearLayout(v.getContext());
                l2.setOrientation(0);//0水平排列，1垂直排列
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//設定寬
                l2.setLayoutParams(params2);
                for (food_list f : listfoodlist) {


                    final long fid = f.getId();
                    food f2 = foodDao.get(f.getFood_id());
                    TextView imgTextView = new TextView(v.getContext());
                    imgTextView.setText(f2.getName());
                    imgTextView.setTextSize(18);//設定文字大小

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(vWidth, vWidth);//設定寬高
                    params.setMargins(15, 15, 15, 15); // llp.setMargins(left, top, right, bottom);
                    imgTextView.setLayoutParams(params);
                    imgTextView.setClickable(true);
                    imgTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            it.removeExtra("FOOD_LIST_ID");
                            it.putExtra("FOOD_LIST_ID", fid);
                            startActivity(it);
                        }
                    });

                    //判斷如果有上傳照片的話，有照片就顯示上傳的照片，若沒有的話就顯示預設的camera的照片
                    if (!f.getImg_id().isEmpty()) {
                        imgTextView.setTextColor(Color.parseColor("#FFFFFF"));   //設定文字顏色
                        Bitmap bitmap=getBitmapFromSDCard(f.getImg_id());

                        if(bitmap != null)
                        {
                            BitmapDrawable bitmapDrawable=new BitmapDrawable(getResources(), bitmap);
                            imgTextView.setBackground(bitmapDrawable);
                        }
                        else
                        {
                            imgTextView.setTextColor(Color.parseColor("#666666"));   //設定文字顏色
                            imgTextView.setBackgroundResource(R.drawable.camera);
                        }


                    } else {
                        imgTextView.setTextColor(Color.parseColor("#666666"));   //設定文字顏色
                        imgTextView.setBackgroundResource(R.drawable.camera);
                    }

                    l2.addView(imgTextView);
                    j++;
                    if (j % 4 == 0) {
                        //ll.addView(l2);
                        ((LinearLayout) ll).addView(l2);
                        l2 = new LinearLayout(v.getContext());
                        l2.setOrientation(0);//0水平排列，1垂直排列
                        l2.setLayoutParams(params2);
                        j = 1;
                    }


                }
                ((LinearLayout) ll).addView(l2);

            }
        }

        return v;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }



}
