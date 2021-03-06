package com.example.student.ce_refrigerator.EmptyData;

/**
 * Created by student on 2017/10/13.
 */

public class food_list {
    private long id;//(主鍵)
    private String img_id;//(照片id)
    private long category_id;//(食材類別 FK category.id)
    private long food_id;//(食物名稱 FK food.id)
    private String purchase_date;//(採購日期)
    private String expired_date;//(到期日期)
    private int purchase_date2;//(轉換過採購日期)
    private int expired_date2;//(轉換過到期日期)
    private double purchase_amount;//(採購金額)
    private int storage;//(存放位置)0冷藏1冷凍
    private String remark;//(備註)

    public food_list(long id,  long category_id, long food_id,String img_id, String purchase_date, String expired_date, double purchase_amount, int storage, String remark) {

        this.id = id;
        this.img_id = img_id;
        this.category_id = category_id;
        this.food_id = food_id;
        this.purchase_date = purchase_date;
        this.expired_date = expired_date;
        this.purchase_amount = purchase_amount;
        this.storage = storage;
        this.remark = remark;
        this.purchase_date2 = Integer.parseInt(purchase_date.toString().replace("/", "").replace(":", "").replace(" ", ""));
        this.expired_date2 = Integer.parseInt(expired_date.toString().replace("/", "").replace(":", "").replace(" ", ""));
    }
    public food_list( long category_id, long food_id, String img_id, String purchase_date, String expired_date,  double purchase_amount, int storage, String remark) {


        this.img_id = img_id;
        this.category_id = category_id;
        this.food_id = food_id;
        this.purchase_date = purchase_date;
        this.expired_date = expired_date;
        this.purchase_amount = purchase_amount;
        this.storage = storage;
        this.remark = remark;
        this.purchase_date2 = Integer.parseInt(purchase_date.toString().replace("/", "").replace(":", "").replace(" ", ""));
        this.expired_date2 = Integer.parseInt(expired_date.toString().replace("/", "").replace(":", "").replace(" ", ""));
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getFood_id() {
        return food_id;
    }

    public void setFood_id(long food_id) {
        this.food_id = food_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public int getPurchase_date2() {
        return purchase_date2;
    }

    public void setPurchase_date2(int purchase_date2) {
        this.purchase_date2 = purchase_date2;
    }

    public int getExpired_date2() {
        return expired_date2;
    }

    public void setExpired_date2(int expired_date2) {
        this.expired_date2 = expired_date2;
    }

    public double getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(double purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
