package com.capedhorse.pocketnotes;

import android.content.Context;
import android.util.Log;

import com.capedhorse.pocketnotes.towhatlist.ListItemModel;
import com.capedhorse.pocketnotes.towhatlist.ListModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;

    //constructor
    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //get all data for view in activity
    public List<ListModel> getListData(){
        RealmResults<ListModel> results = realm.where(ListModel.class).findAll();
        return results;
    }

    public List<ListItemModel> getItemData(String category) {
        RealmResults<ListItemModel> results = realm.where(ListItemModel.class).equalTo("category", category).findAll();
        return results;
    }

    //next id
    public long getNextId(RealmModel realmModel){
        if (realm.where(realmModel.getClass()).count() !=0){
            long id = realm.where(realmModel.getClass()).max("id").longValue();
            return id +1;
        }else{
            return 1;
        }
    }

    //insert
    public void insertData (RealmModel catatan){
        realm.beginTransaction();
        realm.copyToRealm(catatan);
        realm.commitTransaction();
        realm.close();
    }

    //update
    public void updateData(RealmModel model){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();

        realm.close();
    }

    //delete function
    public void deleteData(int id, RealmObject realmModel){
        realm.beginTransaction();
        RealmObject catatan = realm.where(realmModel.getClass()).equalTo("id",id).findFirst();
        catatan.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

}
