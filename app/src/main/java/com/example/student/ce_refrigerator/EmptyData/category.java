package com.example.student.ce_refrigerator.EmptyData;

import java.io.Serializable;

/**
 * 食材類別
 */

public class category implements Serializable{
    private  long id; //主鍵
    private String name;//大類名稱

    public category(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public category( String name) {
        this.id = id;
        this.name = name;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
