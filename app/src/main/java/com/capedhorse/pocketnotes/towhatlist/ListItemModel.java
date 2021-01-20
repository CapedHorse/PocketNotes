package com.capedhorse.pocketnotes.towhatlist;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ListItemModel extends RealmObject {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @PrimaryKey
    private Integer id;
    private Boolean done;
    private String name;
    private Integer categoryId;

}
