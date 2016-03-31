package com.dii.polytech.orbox;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class NewDescriptor extends AppCompatActivity {

    public final static int COORDINATES = 0;

    private Button buttonCancel = null;
    private Button buttonOK = null;
    private Spinner spinnerCategory = null;
    private Spinner spinnerObject = null;
    private Button buttonTakePicture = null;
    private ArrayList<Category> categories;
    private ArrayList<Point> coordinates;

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

        buttonTakePicture = (Button) findViewById(R.id.NewDescriptor_ButtonTakePicture);
        buttonTakePicture.setOnClickListener(clickListenerButtons);

        spinnerCategory = (Spinner) findViewById((R.id.NewDescriptor_SpinnerCategory));
        spinnerObject = (Spinner) findViewById((R.id.NewDescriptor_SpinnerObject));

        spinnerCategory.setOnItemSelectedListener(clickListenerSpinner);
        spinnerObject.setOnItemSelectedListener(clickListenerSpinner);

        /*Disable buttonOK, spinnerObject and imageViewShot*/
        buttonOK.setEnabled(false);
        spinnerObject.setEnabled(false);
        buttonTakePicture.setEnabled(false);

        /*Test*/
        MakeTest();

        prepareSpinnerCategory();
    }

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
                case R.id.NewDescriptor_ButtonTakePicture:
                    Intent intent = new Intent(NewDescriptor.this, ImageAreaSelector.class);
                    startActivityForResult(intent,COORDINATES);
                default:
                    //TODO
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COORDINATES) {
            if (resultCode == RESULT_OK) {
                coordinates = data.getParcelableArrayListExtra("Coordinates");

                buttonOK.setEnabled(true);

                /*Test*/
                for (Point p : coordinates) {
                    Log.d("Coordonnées", "x = " + String.valueOf(p.x) + " ,y = " + String.valueOf(p.y));
                }
            }
        }
    }

    private void prepareSpinnerCategory() {

        ArrayList<String> listCategoryNames = new ArrayList<String>();

        listCategoryNames.add("");

        for(Category cat : categories){
            listCategoryNames.add(cat.get_name());
        }

        ArrayAdapter<String> adapterSpinnerCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCategoryNames);
        spinnerCategory.setAdapter(adapterSpinnerCategory);
    }

    private void prepareSpinnerObject(int position) {

        ArrayList<String> objects = new ArrayList<String>();

        objects.add("");

        for (ObjectOrbox obj : categories.get(position).get_ObjectsOrbox()) {
            objects.add(obj.get_name());
        }

        ArrayAdapter<String> adapterSpinnerObject = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, objects);

        spinnerObject.setAdapter(adapterSpinnerObject);
    }

    public Category get_CategoryByName(String name){
        for(Category cat: categories){
            if(cat.get_name().equals(name)) return cat;
        }
        return null;
    }

    private Spinner.OnItemSelectedListener clickListenerSpinner = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            switch(parentView.getId())
            {
                case R.id.NewDescriptor_SpinnerCategory:

                    if(position != 0) {
                        prepareSpinnerObject(position-1);
                        spinnerObject.setEnabled(true);
                    }
                    else {
                        spinnerObject.setEnabled(false);
                        buttonTakePicture.setEnabled(false);
                    }

                    break;

                case R.id.NewDescriptor_SpinnerObject:

                    if(position != 0)
                        buttonTakePicture.setEnabled(true);
                    else
                        buttonTakePicture.setEnabled(false);

                    break;

                default:
                    //TODO
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            //TODO
        }
    };

    private void MakeTest(){

        // CREATE CATEGORY
        Category CatTest = new Category("Pièces");
        Category CatTest2 = new Category("Fruits");
        Category CatTest3 = new Category("Voitures");

        // CREATE OBJECT
        ObjectOrbox ObjTest1 = new ObjectOrbox("1€");
        ObjectOrbox ObjTest2 = new ObjectOrbox("2€", "Oral description");
        ObjectOrbox ObjTest3 = new ObjectOrbox("50ct", "Oral description");

        ObjectOrbox ObjTest10 = new ObjectOrbox("Banane");
        ObjectOrbox ObjTest20 = new ObjectOrbox("Orange", "Oral description");
        ObjectOrbox ObjTest30 = new ObjectOrbox("Cerise", "Oral description");

        ObjectOrbox ObjTest100 = new ObjectOrbox("Clio");
        ObjectOrbox ObjTest200 = new ObjectOrbox("Twingo", "Oral description");
        ObjectOrbox ObjTest300 = new ObjectOrbox("Polo", "Oral description");

        CatTest.add_ObjectOrbox(ObjTest1);
        CatTest.add_ObjectOrbox(ObjTest2);
        CatTest.add_ObjectOrbox(ObjTest3);

        CatTest2.add_ObjectOrbox(ObjTest10);
        CatTest2.add_ObjectOrbox(ObjTest20);
        CatTest2.add_ObjectOrbox(ObjTest30);

        CatTest3.add_ObjectOrbox(ObjTest100);
        CatTest3.add_ObjectOrbox(ObjTest200);
        CatTest3.add_ObjectOrbox(ObjTest300);

        categories = new ArrayList<Category>();
        categories.add(CatTest);
        categories.add(CatTest2);
        categories.add(CatTest3);
    }
}





