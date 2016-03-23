package com.dii.polytech.orbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class NewDescriptor extends AppCompatActivity {

    private View.OnClickListener clickListenerButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.NewDescriptor_ButtonOk:
                    //TODO
                    finish();
                    break;
                case R.id.NewDescriptor_ButtonCancel:
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
        setContentView(R.layout.activity_new_descriptor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonCancel = (Button) findViewById(R.id.NewDescriptor_ButtonCancel);
        buttonOK = (Button) findViewById(R.id.NewDescriptor_ButtonOk);

        buttonCancel.setOnClickListener(clickListenerButtons);
        buttonOK.setOnClickListener(clickListenerButtons);
    }

}
