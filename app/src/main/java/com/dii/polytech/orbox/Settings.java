package com.dii.polytech.orbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    private View.OnClickListener clickListenerButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Settings_ButtonOk:
                    //TODO
                    finish();
                    break;
                case R.id.Settings_ButtonCancel:
                    finish();
                    break;
                default:
                    //TODO
                    break;
            }
        }
    };

    private Button buttonCancel = null;
    private Button buttonOK = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        buttonCancel = (Button) findViewById(R.id.Settings_ButtonCancel);
        buttonOK = (Button) findViewById(R.id.Settings_ButtonOk);

        buttonCancel.setOnClickListener(clickListenerButtons);
        buttonOK.setOnClickListener(clickListenerButtons);

    }

}
