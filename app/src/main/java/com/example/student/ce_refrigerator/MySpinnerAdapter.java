package com.example.student.ce_refrigerator;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by student on 2017/10/17.
 */

public class MySpinnerAdapter extends BaseAdapter {

    Activity activity;
    public MySpinnerAdapter(Activity activity) {
        this.activity= activity;
    }

    @Override
    public int getCount() {
        return 0;
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
        return null;
    }

}
