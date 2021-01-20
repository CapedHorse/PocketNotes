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
        realm.beginTransaction();
        RealmResults<ListModel> results = realm.where(ListModel.class).findAll();
        realm.commitTransaction();
        return results;
    }

    public List<ListItemModel> getItemData(int categoryId) {
        RealmResults<ListItemModel> results = realm.where(ListItemModel.class).equalTo("categoryId", categoryId).findAll();
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
    }

    //update
    public void updateData(RealmModel model){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public void updateList(final Integer id, final String name){
        realm.executeTransactionAsync( new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ListModel model = realm.where(ListModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setName(name);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("Success", "onSuccess: Update SUcessfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public void deleteList(int id){

        realm.beginTransaction();
        ListModel model = realm.where(ListModel.class)
                .equalTo("id", id)
                .findFirst();
        model.deleteFromRealm();
        realm.commitTransaction();

    }

    public void deleteItem(int id){

        realm.beginTransaction();
        ListItemModel model = realm.where(ListItemModel.class)
                .equalTo("id", id)
                .findFirst();
        model.deleteFromRealm();
        realm.commitTransaction();

    }

}
