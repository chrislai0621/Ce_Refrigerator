package com.example.student.ce_refrigerator.EmptyData;

import java.io.Serializable;

/**
 * Created by student on 2017/10/13.
 */

public class food implements Serializable {
    private int id;//pk
    private int category_id;//fk category.id
    private String name;//食物名稱

    public food(int id, int category_id, String name) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
