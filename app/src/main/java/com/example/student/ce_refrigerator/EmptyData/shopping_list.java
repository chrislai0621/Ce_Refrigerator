package com.example.student.ce_refrigerator.EmptyData;

/**
 * Created by student on 2017/10/13.
 */

public class shopping_list {
    private long id;
    private  String food_name;
    private  double purchase_amount;
    private boolean is_check;

    public shopping_list(long id, String food_name, double purchase_amount,boolean is_check) {
        this.id = id;
        this.food_name = food_name;
        this.purchase_amount = purchase_amount;
        this.is_check=is_check;
    }
    public shopping_list( String food_name, double purchase_amount,boolean is_check) {
        this.food_name = food_name;
        this.purchase_amount = purchase_amount;
        this.is_check=is_check;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public double getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(double purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public boolean is_check() {
        return is_check;
    }

    public void setIs_check(boolean is_check) {
        this.is_check = is_check;
    }
}
