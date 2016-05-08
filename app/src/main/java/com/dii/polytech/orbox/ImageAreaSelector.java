package com.dii.polytech.orbox;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAreaSelector extends AppCompatActivity {

    private DescriptorView descriptorView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_area_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Point> coordinates = descriptorView.getPoints();

                    //                for (Point p : coordinates) {
                    //                    Log.d("Coordonnées", "x = " + String.valueOf(p.x) + " ,y = " + String.valueOf(p.y));
                    //                }

                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("Coordinates", coordinates);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        descriptorView = (DescriptorView)findViewById(R.id.ImageAreaSelector_DescriptorView);
        if (descriptorView != null) {
            descriptorView.setImageResource(R.drawable.img_test);
            descriptorView.setScaleType(ImageView.ScaleType.FIT_START);
        }

    }

}
