package com.dii.polytech.orbox;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Administration extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Toast TestToast = null;

    ArrayList<Category> categories;

    public Category get_CategoryByName(String name){
        for(Category cat: categories){
            if(cat.get_name().equals(name)) return cat;
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

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

        //////////////////////////////////////////// TEST ////////////////////////////////////////////

        // CREATE CATEGORY
        Category CatTest = new Category("Category 1");
        Category CatTest2 = new Category("Category 2");

        // CREATE OBJECT
        ObjectOrbox ObjTest = new ObjectOrbox("Object 1");
        ObjectOrbox ObjTest2 = new ObjectOrbox("Object 2", "Oral description");
        ObjectOrbox ObjTest3 = new ObjectOrbox("Object 3", "Oral description");

        CatTest.add_ObjectOrbox(ObjTest);
        CatTest.add_ObjectOrbox(ObjTest2);
        CatTest2.add_ObjectOrbox(ObjTest3);

        categories = new ArrayList<Category>();
        categories.add(CatTest);
        categories.add(CatTest2);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.Administration_ListViewObjects);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                Category Cat = get_CategoryByName(listDataHeader.get(groupPosition));

                ObjectOrbox Obj = Cat.get_ObjectOrboxByName(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));

                Toast.makeText(getApplicationContext(), Obj.toString(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        registerForContextMenu(expListView);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, R.string.ContextMenuRenameObject);
        menu.add(0, v.getId(), 0, R.string.ContextMenuDeleteObject);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals(getResources().getString(R.string.ContextMenuRenameObject))) {
            Rename();
        } else if (item.getTitle().toString().equals(getResources().getString(R.string.ContextMenuDeleteObject))) {
            Delete();
        }
        else{
            return  false;
        }
        return true;
    }

    public void Rename(){

        final Dialog dialogRename = new Dialog(Administration.this);
        dialogRename.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRename.setContentView(R.layout.dialog_administration_rename_category);
        dialogRename.show();

        final EditText dialogRenameCategory = (EditText)dialogRename.findViewById(R.id.DialogRenameCategory_NewName);
        Button OkButton = (Button)dialogRename.findViewById(R.id.DialogRenameCategory_ButtonOk);
        Button CancelButton = (Button)dialogRename.findViewById(R.id.DialogRenameCategory_ButtonCancel);

        View.OnClickListener clickListenerButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.DialogRenameCategory_ButtonOk:
                        if(dialogRenameCategory.getText().toString().equals(""))
                            TestToast.makeText(Administration.this, "No text entered", Toast.LENGTH_SHORT).show();
                        else
                        {
                            TestToast.makeText(Administration.this, "Category rename in : " + dialogRenameCategory.getText().toString(), Toast.LENGTH_SHORT).show();
                            // Change name on IHM
                        }
                        dialogRename.cancel();
                        break;
                    case R.id.DialogRenameCategory_ButtonCancel:
                        TestToast.makeText(Administration.this, "Canceled", Toast.LENGTH_SHORT).show();
                        dialogRename.cancel();
                        break;
                    default:
                        //TODO
                        break;
                }
            }
        };

        OkButton.setOnClickListener(clickListenerButtons);
        CancelButton.setOnClickListener(clickListenerButtons);

    }

    public void Delete(){
        Dialog dialogDelete = new Dialog(Administration.this);
        dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDelete.setContentView(R.layout.dialog_administration_delete_category);
        dialogDelete.show();
    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (Category cat : categories){

            listDataHeader.add(cat.get_name());

            List<String> ListObjectsOrbox = new ArrayList<String>();

            for(ObjectOrbox obj : cat.get_ObjectsOrbox()){
                ListObjectsOrbox.add(obj.get_name());
            }

            listDataChild.put(cat.get_name(), ListObjectsOrbox);
        }
    }
}