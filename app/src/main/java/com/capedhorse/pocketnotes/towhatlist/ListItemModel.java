package com.capedhorse.pocketnotes.towhatlist;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ListItemModel extends RealmObject {

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @PrimaryKey
    private int id;
    private Boolean done;
    private String name;
    private int categoryId;

}
