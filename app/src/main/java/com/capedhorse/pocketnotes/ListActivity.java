package com.capedhorse.pocketnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capedhorse.pocketnotes.towhatlist.ListModel;
import com.capedhorse.pocketnotes.towhatlist.ListNameAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RealmHelper realmHelper = new RealmHelper(RealmConfig.newRealmInstance());
    RecyclerView recyclerView;
    ListNameAdapter listAdapter;
    List<ListModel> listModels;


    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerViewList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listModels = new ArrayList<>();

        listModels = realmHelper.getListData();

        show();

        btnAdd = findViewById(R.id.btnAddList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText input = new EditText(ListActivity.this);

                AlertDialog.Builder add = new AlertDialog.Builder(ListActivity.this)
                        .setTitle("Add New List")
                        .setMessage("Type the list name")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListModel listModel = new ListModel();

                                listModel.setId((int) realmHelper.getNextId(listModel));
                                listModel.setName(input.getText().toString());
                                realmHelper.insertData(listModel);

                                Toast.makeText(ListActivity.this,"New list added.", Toast.LENGTH_SHORT).show();

                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                add.show();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listAdapter.notifyDataSetChanged();
        show();
    }

    private void show() {
        listAdapter = new ListNameAdapter(this, listModels);
        recyclerView.setAdapter(listAdapter);
    }


}