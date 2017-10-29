package com.example.student.ce_refrigerator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.InputStream;

/**
 * Created by student on 2017/10/25.
 */

public class SharingMethod {
    public static int getPicWidth(Activity activity) {
        // 取得螢幕解析度
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int vWidth = dm.widthPixels;
        vWidth = (int) ((vWidth * 0.8) / 3);//算出每張圖的寬度
        if (vWidth >= 300) {
            vWidth = 300;
        }
        return vWidth;
    }

    //讀取SDCard圖片，型態為Bitmap
    public static Bitmap getBitmapFromSDCard(String file) {
        try {
            String sd = Environment.getExternalStorageDirectory().toString();
            sd = sd + File.separator + "CeRefrigerator" + File.separator;
            Bitmap bitmap = null;
            File f = new File(sd + file);
            if (f.exists()) {

                bitmap = BitmapFactory.decodeFile(sd + file);
            }

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapFromSDCard(InputStream is, int inSampleSize) {
        try {
            String sd = Environment.getExternalStorageDirectory().toString();
            sd = sd + File.separator + "CeRefrigerator" + File.separator;
            Bitmap bitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = inSampleSize;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            System.gc();


            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPicPath(String fileName) {
        String sd = getPicDir();
        String path = sd + fileName;
        return path;
    }
    public static String getPicDir() {
        String sd = Environment.getExternalStorageDirectory().toString();
        sd = sd + File.separator + "CeRefrigerator" + File.separator;

        return sd;
    }
}
