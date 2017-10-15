package com.example.student.ce_refrigerator;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.student.ce_refrigerator.EmptyData.food;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodCategoryFragment extends DialogFragment {

    private SubCategory SubCategoryActivity ;
    private EditText edFoodName;
    public FoodCategoryFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SubCategoryActivity = (SubCategory) getActivity();
        //取得inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //從fragment.xml取得自訂畫面
        //inflat(resource,viewGroup)
        View view = inflater.inflate(R.layout.fragment_food_category, null);
        edFoodName = (EditText) view.findViewById(R.id.edFoodName);
        //建立AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (SubCategoryActivity.mStatus) {
            case "Add":

                builder.setTitle("新增食材名稱")
                        .setIcon(android.R.drawable.ic_input_add)
                        .setView(view) //設定自訂view
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                ((SubCategory) getActivity()).setOKMessage(edFoodName.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                break;
            case "Edit":

                food food1 = SubCategoryActivity.getListSubCategory().get(SubCategoryActivity.selectedIndex);

                edFoodName.setText(food1.getName());


                builder.setTitle("修改食材名稱")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(view) //設定自訂view
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ((SubCategory) getActivity()).setEditMessage(edFoodName.getText().toString());
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
