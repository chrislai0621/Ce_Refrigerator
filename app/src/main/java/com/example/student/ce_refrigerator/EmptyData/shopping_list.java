package com.example.student.ce_refrigerator.EmptyData;

/**
 * Created by student on 2017/10/13.
 */

public class shopping_list {
    private  String food_name;
    private  double purchase_amount;

    public shopping_list(String food_name, double purchase_amount) {
        this.food_name = food_name;
        this.purchase_amount = purchase_amount;
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
}
