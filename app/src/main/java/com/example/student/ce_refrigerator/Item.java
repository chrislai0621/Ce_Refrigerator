package com.example.student.ce_refrigerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.EmptyData.category;
import com.example.student.ce_refrigerator.EmptyData.food;
import com.example.student.ce_refrigerator.EmptyData.food_list;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Item extends AppCompatActivity {
    static final int PICK_FROM_CAMERA = 123;
    static final int PICK_FROM_GALLERY = 321;
    static final int PICK_FROM_GET = 456;

    private Button btnAction;
    private EditText etExpiredDate, etPurchaseDate, etPurchaseAmt, etRemark;
    private Spinner spCategory, spFood;
    private ImageView ImageView1;
    private RadioButton rb1, rb2;

    private int myYear, myMonth, myDay, myHour, myMinute;
    static final int ID_DATEPICKER = 0;
    static final int ID_TIMEPICKER = 1;
    static String mDateItem = "purchase";
    private List<category> listCategory = new ArrayList<>();
    private List<food> listfood = new ArrayList<>();
    private ArrayList<String> arrayCategory = new ArrayList<>();
    private ArrayList<String> arrayFood = new ArrayList<>();
    private ArrayAdapter<String> _AdapterCategory;
    private ArrayAdapter<String> _AdapterFood;

    private CategoryDao categoryDao;
    private FoodDao foodDao;
    private FoodListDao foodListDao;

    private Uri outputFileUri;
    private Calendar mCal;
    private String imgFile;
    private String picPath;
    private long categoryid;
    private long foodid;
    private boolean havePic = false;
    private CharSequence s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        categoryDao = new CategoryDao(getApplicationContext());
        foodDao = new FoodDao(getApplicationContext());
        foodListDao = new FoodListDao(getApplicationContext());

        initView();
        setSpinnerAdapter();
        createSDFolder();

    }

    private void initView() {


        etExpiredDate = (EditText) findViewById(R.id.etExpiredDate);
        etPurchaseDate = (EditText) findViewById(R.id.etPurchaseDate);
        etExpiredDate.setOnClickListener(datePickerOnClickListener);
        etPurchaseDate.setOnClickListener(datePickerOnClickListener);
        etRemark = (EditText) findViewById(R.id.etRemark);
        etPurchaseAmt = (EditText) findViewById(R.id.etPurchaseAmt);
        mCal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy/MM/dd", mCal.getTime());
        etPurchaseDate.setText(s.toString());
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spFood = (Spinner) findViewById(R.id.spFood);
        ImageView1 = (ImageView) findViewById(R.id.ImageView1);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);

        btnAction = (Button) findViewById(R.id.btnAction);
        btnAction.setText("OK");
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_list f;


                String sPurchase = etPurchaseDate.getText().toString();
                String sExpiredDate = etExpiredDate.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                String sPurchaseAmt = etPurchaseAmt.getText().toString();
                double PurchaseAmt = 0;
                int storage = 0;
                String sRemark = etRemark.getText().toString();
                String msg = "";
                try {

                    if (sPurchase.isEmpty()) {


                        msg += "採購日期必填\n";
                    }
                    if (sExpiredDate.isEmpty()) {

                        msg += "到期日期必填\n";
                    }
                    if (!sPurchaseAmt.isEmpty()) {
                        PurchaseAmt = Double.valueOf(sPurchaseAmt);
                    } else {
                        msg += "採購金額必填\n";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!msg.isEmpty()) {
                    new AlertDialog.Builder(Item.this)
                            .setTitle("欄位少填")
                            .setMessage(msg)
                            .show();
                    return;
                }
                if (rb1.isChecked()) storage = 0;
                else if (rb2.isChecked()) storage = 1;

                if (havePic) {

                    f = new food_list(categoryid, foodid, imgFile, sPurchase, sExpiredDate, PurchaseAmt, storage, sRemark);
                } else {
                    f = new food_list(categoryid, foodid, "", sPurchase, sExpiredDate, PurchaseAmt, storage, sRemark);

                }
                foodListDao.insert(f);
                finish();

            }
        });
    }

    private void setSpinnerAdapter() {
        _AdapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayCategory);
        _AdapterFood = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayFood);
        listCategory = categoryDao.getAll();
        for (category o : listCategory) {
            arrayCategory.add(o.getName());
        }


        spCategory.setAdapter(_AdapterCategory);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryid = listCategory.get(i).getId();
                listfood = foodDao.getAll(categoryid);
                arrayFood.clear();
                for (food o : listfood) {
                    arrayFood.add(o.getName());
                }
                spFood.setAdapter(_AdapterFood);
                spFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        foodid = listfood.get(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void createSDFolder() {
        //返回 File ，得到外部儲存目錄即 SDCard
        File sd = Environment.getExternalStorageDirectory();
        picPath = sd.getPath() + File.separator + "CeRefrigerator";

        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判斷sd記憶卡是否存在
        if (sdCardExist) {

            Log.d("PIC_PATH", Environment.getExternalStorageDirectory().toString());//得到根目錄
            Log.d("PIC_PATH", Environment.getDataDirectory().toString());
            Log.d("PIC_PATH", Environment.getRootDirectory().toString());
            Log.d("PIC_PATH", picPath);
            File file = new File(picPath);
            if (!file.exists()) {
                file.mkdir();
            }
            picPath = picPath + File.separator;
        }

    }

    public void BackPage(View v) {
        finish();
    }


    //觸發採購日期與到期日期的日期視窗
    private EditText.OnClickListener datePickerOnClickListener
            = new EditText.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.etPurchaseDate:
                    mDateItem = "purchase";
                    break;
                case R.id.etExpiredDate:
                    mDateItem = "expired";
                    break;
            }
            DateFragment newFragment = new DateFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }
    };

    //供DateFragment傳回date值
    public void setDate(String date) {
        switch (mDateItem) {
            case "purchase":
                etPurchaseDate.setText(date.toString());
                break;
            case "expired":
                etExpiredDate.setText(date.toString());
                break;
        }
    }

    public void onclick_camera(View v) {
        final String[] arrayCamera = getResources().getStringArray(R.array.camera);
        new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
                .setTitle("選擇照片")
                .setItems(arrayCamera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0: //相機

                                s = DateFormat.format("yyyyMMddHHmmss", mCal.getTime());
                                imgFile = s.toString() + ".jpg";
                                Intent intent = new Intent(
                                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                File tmpFile = new File(picPath, imgFile);
                                outputFileUri = Uri.fromFile(tmpFile);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                startActivityForResult(intent, PICK_FROM_CAMERA);
                                break;
                            case 1://相簿
                                Intent it = new Intent(Intent.ACTION_PICK, null);
                                it.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(it, PICK_FROM_GALLERY);
                                break;
                        }
                    }
                }).show();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PICK_FROM_CAMERA || requestCode == PICK_FROM_GALLERY) {
            if (resultCode == RESULT_OK) {

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(outputFileUri, "image/*");
                intent.putExtra("crop", "true");  //crop = true時就打開裁切畫面
                intent.putExtra("aspectX", 1);    //aspectX與aspectY是設定裁切框的比例
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 150);  //這則是裁切的照片大小
                intent.putExtra("outputY", 150);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, PICK_FROM_GET);

            }
        } else if (requestCode == PICK_FROM_GET) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");

                int oldwidth = photo.getWidth();
                int oldheight = photo.getHeight();
                float scaleWidth = 100 / (float) oldwidth;
                float scaleHeight = 100 / (float) oldheight;
                Matrix matrix = new Matrix();

                matrix.postScale(scaleWidth, scaleHeight);
                // create the new Bitmap object

                Bitmap resizedBitmap = Bitmap.createBitmap(photo, 0, 0, oldwidth,
                        oldheight, matrix, true);
                ImageView1.setImageBitmap(resizedBitmap);
                //String img_address = Environment.getExternalStorageDirectory() + imgFile;
                //Bitmap bitmap = BitmapFactory.decodeFile(img_address);
                //ImageView1.setImageBitmap(bitmap);

                havePic = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
