package com.capedhorse.pocketnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capedhorse.pocketnotes.R;
import com.capedhorse.pocketnotes.towhatlist.ListItemAdapter;
import com.capedhorse.pocketnotes.towhatlist.ListItemModel;
import com.capedhorse.pocketnotes.towhatlist.ListModel;
import com.capedhorse.pocketnotes.towhatlist.ListNameAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListItemActivity extends AppCompatActivity {
    Button btnAdd;
    TextView title;
    int id;
    String categoryName;

    RealmHelper realmHelper = new RealmHelper(RealmConfig.newRealmInstance());

    RecyclerView recyclerView;
    ListItemAdapter listAdapter;
    List<ListItemModel> itemModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        recyclerView = findViewById(R.id.recyclerViewItem);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        categoryName = getIntent().getStringExtra("category");

        itemModels = new ArrayList<>();
        itemModels = realmHelper.getItemData(id);

        title = findViewById(R.id.tvListTitle);
        title.setText(categoryName);

        show();

        btnAdd = findViewById(R.id.btnAddList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText input = new EditText(ListItemActivity.this);

                AlertDialog.Builder add = new AlertDialog.Builder(ListItemActivity.this)
                        .setTitle("Add New Item")
                        .setMessage("Type the item name")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListItemModel itemModel = new ListItemModel();

                                itemModel.setId((int) realmHelper.getNextId(itemModel));
                                itemModel.setDone(false);
                                itemModel.setName(input.getText().toString());
                                itemModel.setCategoryId(id);

                                realmHelper.insertData(itemModel);

                                Toast.makeText(ListItemActivity.this,"New item added.", Toast.LENGTH_SHORT).show();

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
        listAdapter = new ListItemAdapter(this, itemModels);
        recyclerView.setAdapter(listAdapter);
    }

}