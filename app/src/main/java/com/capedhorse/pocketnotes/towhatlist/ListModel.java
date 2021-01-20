package com.capedhorse.pocketnotes.towhatlist;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ListModel extends RealmObject {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PrimaryKey
    private int id;

    private String name;
}
