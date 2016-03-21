package com.dii.polytech.orbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    private View.OnClickListener clickListenerButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Home_ButtonSettings:
                    startActivity(new Intent(Home.this, Settings.class));
                    break;
                case R.id.Home_ButtonAdministration:
                    //TODO
                    startActivity(new Intent(Home.this, Administration.class));
                    break;
                case R.id.Home_ButtonNewDescriptor:
                    //TODO
                    startActivity(new Intent(Home.this, NewDescriptor.class));
                    break;
                default:
                    //TODO
                    break;
            }
        }
    };

    private Button buttonSettings = null;
    private Button buttonAdministration = null;
    private Button buttonNewDescriptor = null;
    private Toast toastBluetooth = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //TODO
        toastBluetooth.makeText(Home.this, "TOAST DE TEST", Toast.LENGTH_SHORT).show();

        buttonSettings = (Button) findViewById(R.id.Home_ButtonSettings);
        buttonAdministration = (Button) findViewById(R.id.Home_ButtonAdministration);
        buttonNewDescriptor = (Button) findViewById(R.id.Home_ButtonNewDescriptor);

        buttonSettings.setOnClickListener(clickListenerButtons);
        buttonAdministration.setOnClickListener(clickListenerButtons);
        buttonNewDescriptor.setOnClickListener(clickListenerButtons);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
