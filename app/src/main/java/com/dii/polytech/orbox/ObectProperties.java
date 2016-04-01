package com.dii.polytech.orbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ObectProperties extends AppCompatActivity {
    Toast TestToast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obect_properties);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView objectName = (TextView)findViewById(R.id.ObjectProperties_nameObject);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // START NEW DESCRIPTOR ACTIVITY
            }
        });

        Intent intent = getIntent();
        String category = intent.getStringExtra("Category");
        String object = intent.getStringExtra("Object");

        this.setTitle(object);
        objectName.setText(object + " is an object off " + category + " and get X descriptors");


    }
}
