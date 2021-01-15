package com.capedhorse.pocketnotes.towhatlist;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListItemHelper {
    Realm realm;

    public ListItemHelper(Realm realm) {
        this.realm = realm;
    }

    public List<ListItemModel> getListItem(String category) {
        RealmResults<ListItemModel> results = realm.where(ListItemModel.class).equalTo("category", category).findAll();
        return results;
    }

    //save function
    public void save(final ListItemModel listItemModel) {
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
                    listItemModel.setId(nextId);
                    ListItemModel model = realm.copyToRealm(listItemModel);
                } else {
                    Log.e("Error", "Database not found");
                }
            }
        });
    }

    //update function
    public void update(final Integer id, final String name, final Boolean done) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                ListItemModel model = realm.where(ListItemModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setId(id);
                model.setName(name);
                model.setDone(done);

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
        final RealmResults<ListItemModel> model = realm.where(ListItemModel.class)
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
