package com.example.student.ce_refrigerator;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.student.ce_refrigerator.Dao.CategoryDao;
import com.example.student.ce_refrigerator.Dao.FoodDao;
import com.example.student.ce_refrigerator.Dao.FoodListDao;
import com.example.student.ce_refrigerator.EmptyData.category;
import com.example.student.ce_refrigerator.EmptyData.food;
import com.example.student.ce_refrigerator.EmptyData.food_list;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.student.ce_refrigerator.SharingMethod.getBitmapFromSDCard;
import static com.example.student.ce_refrigerator.SharingMethod.getPicDir;
import static com.example.student.ce_refrigerator.SharingMethod.getPicPath;
import static com.example.student.ce_refrigerator.SharingMethod.getPicWidth;

public class Item extends AppCompatActivity {
    static final int PICK_FROM_CAMERA = 1;
    static final int PICK_FROM_GALLERY = 2;
    static final int PICK_FROM_CAMERA_GET = 3;
    static final int PICK_FROM_GALLERY_GET = 4;
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
    private Uri outputFileUri2;
    private Calendar mCal;
    private String imgFileName;
    private String picPath;
    private long categoryid;
    private long foodid;
    private boolean havePic = false;
    private CharSequence s;
    private String mSelectedImagePath;
    private int picWidth;
    private int android_version;

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ImageView1.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        getAndroidVersion();
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

                    f = new food_list(categoryid, foodid, imgFileName, sPurchase, sExpiredDate, PurchaseAmt, storage, sRemark);
                } else {
                    f = new food_list(categoryid, foodid, "", sPurchase, sExpiredDate, PurchaseAmt, storage, sRemark);

                }
                foodListDao.insert(f);
                finish();

            }
        });
        picWidth = getPicWidth(Item.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(picWidth, picWidth);//設定寬高
        params.setMargins(15, 15, 15, 15);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        ImageView1.setLayoutParams(params);
        ImageView1.setBackgroundResource(R.drawable.camera);
    }

    private void setSpinnerAdapter() {
        _AdapterCategory = new ArrayAdapter<String>(this, R.layout.spinner_style, arrayCategory);
        _AdapterFood = new ArrayAdapter<String>(this, R.layout.spinner_style, arrayFood);
        _AdapterCategory.setDropDownViewResource(R.layout.spinner_style);
        _AdapterFood.setDropDownViewResource(R.layout.spinner_style);
        listCategory = categoryDao.getAll();
        if (listCategory.size() == 0) {
            Toast.makeText(this, "請維護食材類別", Toast.LENGTH_LONG).show();
        }
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
                if (listfood.size() == 0) {
                    Toast.makeText(Item.this, "請維護食材名稱", Toast.LENGTH_LONG).show();
                }
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

                                setimgFileName();

                                File tmpFile = new File(picPath, imgFileName);
                                Intent intent2 = new Intent(
                                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                outputFileUri = Uri.fromFile(tmpFile);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                startActivityForResult(intent2, PICK_FROM_CAMERA);

                                break;
                            case 1://相簿
//
                                setimgFileName();


                                if (android_version > 5) {
                                    //构建一个内容选择的Intent
                                    // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    //设置选择类型为图片类型
                                    intent.setType("image/*");
                                    //打开图片选择
                                    startActivityForResult(intent, PICK_FROM_GALLERY);

                                } else {
                                    File tmpFile2 = new File(picPath, imgFileName);
                                    outputFileUri = Uri.fromFile(tmpFile2);
                                    Intent photoPickerIntent =
                                            new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    photoPickerIntent.setType("image/*");
                                    photoPickerIntent.putExtra("crop", "true");
                                    photoPickerIntent.putExtra("aspectX", 4);
                                    photoPickerIntent.putExtra("aspectY", 3);
                                    photoPickerIntent.putExtra("outputX", picWidth);
                                    photoPickerIntent.putExtra("outputY", picWidth);
                                    photoPickerIntent.putExtra("return-data", false);
                                    photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 圖片格式
                                    photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                    startActivityForResult(photoPickerIntent, PICK_FROM_CAMERA_GET);

                                }
                                break;
                        }
                    }
                }).show();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(outputFileUri, "image/*");
                    intent.putExtra("crop", "true");  //crop = true時就打開裁切畫面
                    intent.putExtra("aspectX", 4);    //aspectX與aspectY是設定裁切框的比例
                    intent.putExtra("aspectY", 3);
                    intent.putExtra("outputX", picWidth);  //這則是裁切的照片大小
                    intent.putExtra("outputY", picWidth);
                    intent.putExtra("return-data", false);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 圖片格式
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(intent, PICK_FROM_CAMERA_GET);
                }
                break;
            case PICK_FROM_GALLERY:


//
//                        outputFileUri = Uri.fromFile(new File(getPicPath(imgFileName)));
//
//                        Intent intent = new Intent("com.android.camera.action.CROP");
//                        intent.setDataAndType(data.getData(), "image/*");
//                        intent.putExtra("outputX", picWidth); // pass width
//                        intent.putExtra("outputY", picWidth); // pass height
//                        intent.putExtra("aspectX", 1);
//                        intent.putExtra("aspectY", 1);
////                        intent.putExtra("scale", true);
////                        intent.putExtra("noFaceDetection", true);
//                        intent.putExtra("output", outputFileUri);
//                        startActivityForResult(intent, PICK_FROM_GALLERY_GET);
                if (data == null) {
                    return;
                } else {
                    //用户从图库选择图片后会返回所选图片的Uri

                    //获取到用户所选图片的Uri
                    outputFileUri = data.getData();
                    //返回的Uri为content类型的Uri,不能进行复制等操作,需要转换为文件Uri
                    outputFileUri = convertUri(outputFileUri);
                    startImageZoom(outputFileUri);
                }


                break;

            case PICK_FROM_CAMERA_GET:
                Bitmap photo = null;
                try {
                    String imgPath = getPicPath(imgFileName);
                    InputStream is = new FileInputStream(imgPath);
                    photo = getBitmapFromSDCard(is, 2);
                    Log.d("IMAGE_FILE", "Width=" + photo.getWidth() + "Height=" + photo.getHeight());
                    Log.d("IMAGE_FILE", "Byte=" + photo.getByteCount());
                } catch (Exception e) {
                    Log.d("IMAGE_FILE", e.getMessage());
                }


                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), photo);
                ImageView1.setBackground(bitmapDrawable);
                havePic = true;
                break;
            case PICK_FROM_GALLERY_GET:
//                ImageView1.setBackground(null);
//                ImageView1.setImageURI(Uri.parse("file://" + outputFileUri));
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //获取到裁剪后的图像
                        Bitmap bm = extras.getParcelable("data");
                        ImageView1.setImageBitmap(bm);
                    }
                }

                havePic = true;
                break;

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                ImageView1.setEnabled(true);
            }
        }
    }

    public void setimgFileName() {
        s = DateFormat.format("yyyyMMddHHmmss", mCal.getTime());
        imgFileName = s.toString() + ".jpg";

    }

    /**
     * 将content类型的Uri转化为文件类型的Uri
     *
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri) {
        InputStream is;
        try {
            //Uri ----> InputStream
            is = getContentResolver().openInputStream(uri);
            //InputStream ----> Bitmap
            Bitmap bm = BitmapFactory.decodeStream(is);
            //关闭流
            is.close();
            return saveBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Bitmap写入SD卡中的一个文件中,并返回写入文件的Uri
     *
     * @param bm
     * @return
     */
    private Uri saveBitmap(Bitmap bm) {
        //新建文件夹用于存放裁剪后的图片
        String dirPath = getPicDir();
        File tmpDir = new File(dirPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        //新建文件存储裁剪后的图片
        File img = new File(dirPath + imgFileName);

        outputFileUri = Uri.fromFile(img);
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 通过Uri传递图像信息以供裁剪
     *
     * @param uri
     */
    private void startImageZoom(Uri uri) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", picWidth);
        intent.putExtra("outputY", picWidth);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, PICK_FROM_GALLERY_GET);
    }

    private void getAndroidVersion() {

        String release = Build.VERSION.RELEASE;

        int intSubStart = release.indexOf(".");
        android_version = Integer.valueOf(release.substring(0, intSubStart));
        Log.d("ANDROID_VERSION", "RELEASE =" + release);
        Log.d("ANDROID_VERSION", "android_version =" + android_version);

    }
}



