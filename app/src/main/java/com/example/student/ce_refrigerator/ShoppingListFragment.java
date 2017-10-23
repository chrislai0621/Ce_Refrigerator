package com.example.student.ce_refrigerator;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.student.ce_refrigerator.EmptyData.shopping_list;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends DialogFragment {

    private Shopping_cart_list shopping_cart_list_activity;
    private EditText etFoodName, etPurchaseAmt2;
    public ShoppingListFragment() {
        // Required empty public constructor
    }


    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        shopping_cart_list_activity = (Shopping_cart_list) getActivity();
        //取得inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //從fragment.xml取得自訂畫面
        //inflat(resource,viewGroup)
        View view = inflater.inflate(R.layout.fragment_shopping_list, null);
        etFoodName = (EditText) view.findViewById(R.id.etFoodName);
        etPurchaseAmt2 = (EditText) view.findViewById(R.id.etPurchaseAmt2);
        //建立AlertDialog

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (shopping_cart_list_activity.mStatus) {
            case "Add":

                builder.setTitle("新增採購清單")
                        .setIcon(android.R.drawable.ic_input_add)
                        .setView(view) //設定自訂view
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String strPurchaseAmt = etPurchaseAmt2.getText().toString();
                                if (strPurchaseAmt.isEmpty()) strPurchaseAmt = "0";
                                ((Shopping_cart_list) getActivity()).setOKMessage(etFoodName.getText().toString(), Double.valueOf(strPurchaseAmt));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                break;
            case "Edit":

                shopping_list s = shopping_cart_list_activity.getShoppingList().get(shopping_cart_list_activity.selectedIndex);
                etFoodName.setText(s.getFood_name());
                etPurchaseAmt2.setText(String.valueOf(s.getPurchase_amount()));


                builder.setTitle("修改採購清單")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(view) //設定自訂view
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String strPurchaseAmt = etPurchaseAmt2.getText().toString();
                                if (strPurchaseAmt.isEmpty()) strPurchaseAmt = "0";
                                ((Shopping_cart_list) getActivity()).setEditMessage(etFoodName.getText().toString(), Double.valueOf(strPurchaseAmt));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                break;

        }

        return builder.create(); //返回所建立的Dialog
    }

}
