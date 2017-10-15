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

import com.example.student.ce_refrigerator.EmptyData.category;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends DialogFragment {//變更繼承DialogFragment
    private Category categoryActivity ;
    private EditText edCategoryName;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        categoryActivity = (Category) getActivity();
        //取得inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //從fragment.xml取得自訂畫面
        //inflat(resource,viewGroup)
        View view = inflater.inflate(R.layout.fragment_category, null);
        edCategoryName = (EditText) view.findViewById(R.id.edCategoryName);
        //建立AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (categoryActivity.mStatus) {
            case "Add":

                builder.setTitle("新增食材類別")
                        .setIcon(android.R.drawable.ic_input_add)
                        .setView(view) //設定自訂view
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                ((Category) getActivity()).setOKMessage(edCategoryName.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                break;
            case "Edit":

                category category = categoryActivity.getListCategory().get(categoryActivity.selectedIndex);

                     edCategoryName.setText(category.getName());


                builder.setTitle("修改食材類別")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(view) //設定自訂view
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ((Category) getActivity()).setEditMessage(edCategoryName.getText().toString());
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
