package com.example.student.ce_refrigerator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student.ce_refrigerator.EmptyData.food_list;

/**
 * Created by student on 2017/10/2.
 */

public class MainListAdapter extends BaseAdapter {

    private MainActivity mainActivity;

    public MainListAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        
        return mainActivity.getfood_list().size();//改成4就有;list view就有4項資料
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v=mainActivity.getLayoutInflater().inflate(R.layout.listview_main,null);
        TextView tvItemid  = (TextView)v.findViewById(R.id.itemid);
        TextView tvFoodName3  = (TextView)v.findViewById(R.id.tvFoodName3);
        TextView tvPurchasedDate3  = (TextView)v.findViewById(R.id.tvPurchasedDate3);
        TextView tvExpiredDate3  = (TextView)v.findViewById(R.id.tvExpiredDate3);
        TextView tvStoragy  = (TextView)v.findViewById(R.id.tvStoragy);

       final food_list f =mainActivity.getfood_list().get(i);

        tvItemid.setText(String.valueOf(f.getId()));
        tvFoodName3.setText(mainActivity.getFoodName(f.getFood_id()));
        tvPurchasedDate3.setText(f.getPurchase_date());
        tvExpiredDate3.setText(f.getExpired_date());
        if(f.getStorage()==0) tvStoragy.setText("冷藏");
        else tvStoragy.setText("冷凍");


        return v;
    }

}
