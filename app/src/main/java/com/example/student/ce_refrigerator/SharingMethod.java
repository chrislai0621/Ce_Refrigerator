package com.example.student.ce_refrigerator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * Created by student on 2017/10/25.
 */

public class SharingMethod {
    public static  int getPicWidth(Activity activity)
    {
        // 取得螢幕解析度
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int vWidth = dm.widthPixels;
        vWidth = (int) ((vWidth * 0.9) / 3);//算出每張圖的寬度
        if(vWidth>=300)
        {
            vWidth=300;
        }
        return vWidth;
    }
    //讀取SDCard圖片，型態為Bitmap
    public static Bitmap getBitmapFromSDCard(String file) {
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
