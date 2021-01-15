package com.capedhorse.pocketnotes.towhatlist;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ListModel extends RealmObject {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PrimaryKey
    private Integer id;
    @PrimaryKey
    private String name;
}
