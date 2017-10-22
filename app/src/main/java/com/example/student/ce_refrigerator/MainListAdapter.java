package com.example.student.ce_refrigerator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.student.ce_refrigerator.EmptyData.shopping_list;

/**
 * Created by student on 2017/10/2.
 */

public class ShoppingListAdapter extends BaseAdapter {

    private Shopping_cart_list shopping_cart_list;

    public ShoppingListAdapter(Shopping_cart_list shopping_cart_list) {
        this.shopping_cart_list = shopping_cart_list;
    }

    @Override
    public int getCount() {
        
        return shopping_cart_list.getShoppingList().size();//改成4就有;list view就有4項資料
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
        View v=shopping_cart_list.getLayoutInflater().inflate(R.layout.listview_shopping_list,null);
        TextView tvItemid  = (TextView)v.findViewById(R.id.itemid);
        TextView tvFoodName2  = (TextView)v.findViewById(R.id.tvFoodName2);
        TextView tvPurchaseAmt2  = (TextView)v.findViewById(R.id.tvPurchaseAmt2);
        CheckBox cbShopping =(CheckBox)v.findViewById(R.id.cbShopping);
       final shopping_list sl =shopping_cart_list.getShoppingList().get(i);

        tvItemid.setText(String.valueOf(sl.getId()));
        tvFoodName2.setText(sl.getFood_name());
        tvPurchaseAmt2.setText(String.valueOf(sl.getPurchase_amount()));
        cbShopping.setChecked(sl.is_check());
        cbShopping.setOnCheckedChangeListener(new  CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sl.setIs_check(true);
                }else{
                    sl.setIs_check(false);
                }
                shopping_cart_list.updateShoppingList(sl);

            }
        });
        return v;
    }

}
