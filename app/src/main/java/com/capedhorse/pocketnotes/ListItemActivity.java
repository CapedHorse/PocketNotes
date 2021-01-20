package com.capedhorse.pocketnotes;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import android.os.Bundle;
import android.widget.TextView;

import com.capedhorse.pocketnotes.R;

public class ListItemActivity extends AppCompatActivity {
    TextView title;

    Integer id;
    String categoryName;

    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        title = findViewById(R.id.tvListTitle);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        categoryName = getIntent().getStringExtra("category");

        title.setText(categoryName);


    }
}