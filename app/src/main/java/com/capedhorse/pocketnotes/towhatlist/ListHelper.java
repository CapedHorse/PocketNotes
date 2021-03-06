package com.capedhorse.pocketnotes.towhatlist;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListHelper {
    Realm realm;

    //constructor
    public ListHelper(Realm realm) {
        this.realm = realm;
    }

    //get all data for view in activity
    public List<ListModel> getAllData(){
        RealmResults<ListModel> results = realm.where(ListModel.class).findAll();
        return results;
    }

    //save function
    public void save(final ListModel listModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "Database was created");
                    Number currentId = realm.where(ListModel.class).max("id");
                    int nextId;
                    if (currentId == null) {
                        nextId = 1;
                    } else {
                        nextId = currentId.intValue() + 1;
                    }
                    listModel.setId(nextId);
                    ListModel model = realm.copyToRealm(listModel);
                } else {
                    Log.e("Error", "Database not found");
                }
            }
        });
    }

    //update function
    public void update(final Integer id, final String name) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                ListModel model = realm.where(ListModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setId(id);
                model.setName(name);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("Success", "Successfully updated");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    //delete function
    public void delete(Integer id){
        final RealmResults<ListModel> model = realm.where(ListModel.class)
                .equalTo("id", id)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }

}
