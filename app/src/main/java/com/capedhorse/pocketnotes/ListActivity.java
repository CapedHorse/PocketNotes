package com.capedhorse.pocketnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.capedhorse.pocketnotes.towhatlist.ListModel;
import com.capedhorse.pocketnotes.towhatlist.ListNameAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements View.OnTouchListener {
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    ListNameAdapter listAdapter;
    List<ListModel> listModels;

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //setup realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        listModels = new ArrayList<>();

        listModels = realmHelper.getListData();

        show();
    }

    private void show() {
        listAdapter = new ListNameAdapter(this, listModels);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}