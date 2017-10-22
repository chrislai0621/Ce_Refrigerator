package com.example.student.ce_refrigerator;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private Item item;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        item  =(Item)getActivity();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        //return new DatePickerDialog(getActivity(),  AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, year, month, day);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        // TODO Auto-generated method stub
        String date =  String.valueOf(year) + "/" + String.valueOf(month+1) + "/"
                + String.valueOf(day);
        item.setDate(date);
    }

}
