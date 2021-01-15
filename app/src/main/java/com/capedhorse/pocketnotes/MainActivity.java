package com.capedhorse.pocketnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnList = findViewById(R.id.btnList);

        btnList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnList) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
        }
    }
}