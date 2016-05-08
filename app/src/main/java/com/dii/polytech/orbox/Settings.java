package com.dii.polytech.orbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private Button buttonCancel ;
    private Button buttonOK ;
    private SeekBar volume ;
    private SeekBar lighting ;
    private Spinner langue ;
    Parameter param = new Parameter();
    private Toast toast = null ;
    ClientFrameManager clientframe = new ClientFrameManager();

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
        volume = (SeekBar) findViewById(R.id.Settings_SeekBarVolume);
        lighting = (SeekBar) findViewById(R.id.Settings_SeekBarLighting);
        langue = (Spinner) findViewById(R.id.Settings_SpinnerLanguages);

        buttonCancel.setOnClickListener(clickListenerButtons);
        buttonOK.setOnClickListener(clickListenerButtons);

        String frame = "03" + "\t" + "select * from Options WHERE 1=1" + "\n";
        // Send Frame by bluetooth
        // Recieve string
        String frameRecieve = "" ; // = ......
        if(clientframe.transmissionSucceed(frameRecieve)){
            param = clientframe.buildParameter(frameRecieve);
            volume.setProgress(param.get_sound());
            lighting.setProgress(param.get_light());
            if(param.get_language().equals("Français")){
                langue.setSelection(0);
            }
            else
                langue.setSelection(1);
        }
        else
        {
            Toast.makeText(Settings.this, getResources().getString(R.string.error_frame), Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener clickListenerButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Settings_ButtonOk:
                    String _volume =  Integer.toString(volume.getProgress());
                    String _lighting = Integer.toString(lighting.getProgress());
                    String _langue;
                    String frame = "00" + "\t" + "UPDATE Options SET volume = " + _volume + ", eclairage = " + _lighting + ", langue = " + langue.getSelectedItem().toString() + "\n";
                    // send frame by bluetooth
                    String frameRecieve = "" ; // = ...
                    if(clientframe.transmissionSucceed(frameRecieve)){
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Settings.this, getResources().getString(R.string.error_frame), Toast.LENGTH_SHORT).show();
                    }
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

}
