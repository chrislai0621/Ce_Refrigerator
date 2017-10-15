package com.example.student.ce_refrigerator.EmptyData;

import java.io.Serializable;

/**
 * Created by student on 2017/10/13.
 */

public class food implements Serializable {
    private long id;//pk
    private long category_id;//fk category.id
    private String name;//食物名稱

    public food(long id, long category_id, String name) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
    }
    public food(long category_id, String name) {

        this.category_id = category_id;
        this.name = name;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
