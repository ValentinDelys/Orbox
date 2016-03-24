package com.dii.polytech.orbox;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;

public class Administration extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Toast TestToast = null;

    ArrayList<Category> categories;

    public void addObjectCategory(String name, ObjectOrbox obj){
        for(Category cat: categories){
            if(cat.get_name().equals(name)) cat.add_ObjectOrbox(obj);
        }
    }

    public Category get_CategoryByName(String name){
        for(Category cat: categories){
            if(cat.get_name().equals(name)) return cat;
        }
        return null;
    }

    public boolean objectExist(String categoryName, String objectName){
        for(Category cat: categories){
            if(cat.get_name().equals(categoryName)){
                for(ObjectOrbox obj: cat.get_ObjectsOrbox()){
                    if(obj.get_name().equals(objectName)) return true;
                }
            }
        }
        return false;
    }

    public boolean categoryExist(String name){
        for(Category cat: categories){
            if(cat.get_name().equals(name)) return true;
        }
        return false;
    }

    public void removeCategoryByName(String name){
        Category catToDelete = null;
        for(Category cat: categories){
            if(cat.get_name().equals(name))
                catToDelete=cat;
        }
        categories.remove(catToDelete);
    }

    public void renameCategoryByName(String previous, String next){
        for(Category cat: categories){
            if(cat.get_name().equals(previous))
                cat.set_name(next);
        }
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
                AddCategory();
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
        updateListData();

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
                // Nothing for the moment
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                // Nothing for the moment
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

        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);

        if(type==ExpandableListView.PACKED_POSITION_TYPE_GROUP)
        {
            menu.add(0, v.getId(), 0, R.string.ContextMenuAddObject);
            menu.add(0, v.getId(), 0, R.string.ContextMenuRenameCategory);
            menu.add(0, v.getId(), 0, R.string.ContextMenuDeleteCategory);
        }
    }

    public boolean onContextItemSelected(MenuItem item) {

        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

        int groupPos = groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);

        if (item.getTitle().toString().equals(getResources().getString(R.string.ContextMenuRenameCategory))) {
            RenameCategory(groupPos);
        } else if (item.getTitle().toString().equals(getResources().getString(R.string.ContextMenuDeleteCategory))) {
            DeleteCategory(groupPos);
        } else if (item.getTitle().toString().equals(getResources().getString(R.string.ContextMenuAddObject))) {
            AddObject(groupPos);
        }

        else{
            return  false;
        }
        return true;
    }

    public void AddObject(final int position){
        final Dialog dialogAdd = new Dialog(Administration.this);
        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdd.setContentView(R.layout.dialog_edittext_okcancel);
        dialogAdd.show();

        TextView text = (TextView)dialogAdd.findViewById(R.id.DialogEditText_TextView);
        text.setText(getResources().getString(R.string.QuestionAddObject));
        final EditText dialogAddObject = (EditText)dialogAdd.findViewById(R.id.DialogEditText_Text);
        Button OkButton = (Button)dialogAdd.findViewById(R.id.DialogEditText_ButtonOk);
        Button CancelButton = (Button)dialogAdd.findViewById(R.id.DialogEditText_ButtonCancel);

        View.OnClickListener clickListenerButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.DialogEditText_ButtonOk:
                        if(dialogAddObject.getText().toString().equals(""))
                            TestToast.makeText(Administration.this, "No text entered", Toast.LENGTH_SHORT).show();
                        else
                        {
                            if(objectExist(get_CategoryByName(listDataHeader.get(position)).get_name(),dialogAddObject.getText().toString()))
                                TestToast.makeText(Administration.this, "The object already exists", Toast.LENGTH_SHORT).show();
                            else
                            {
                                ObjectOrbox obj = new ObjectOrbox(dialogAddObject.getText().toString());
                                addObjectCategory(get_CategoryByName(listDataHeader.get(position)).get_name(),obj);
                                updateListData();
                                TestToast.makeText(Administration.this, "Object added : " + dialogAddObject.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialogAdd.cancel();
                        break;
                    case R.id.DialogEditText_ButtonCancel:
                        TestToast.makeText(Administration.this, "Canceled", Toast.LENGTH_SHORT).show();
                        dialogAdd.cancel();
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

    public void AddCategory(){
        final Dialog dialogAdd = new Dialog(Administration.this);
        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdd.setContentView(R.layout.dialog_edittext_okcancel);
        dialogAdd.show();

        TextView text = (TextView)dialogAdd.findViewById(R.id.DialogEditText_TextView);
        text.setText(getResources().getString(R.string.QuestionAddCategory));
        final EditText dialogAddCategory = (EditText)dialogAdd.findViewById(R.id.DialogEditText_Text);
        Button OkButton = (Button)dialogAdd.findViewById(R.id.DialogEditText_ButtonOk);
        Button CancelButton = (Button)dialogAdd.findViewById(R.id.DialogEditText_ButtonCancel);

        View.OnClickListener clickListenerButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.DialogEditText_ButtonOk:
                        if(dialogAddCategory.getText().toString().equals(""))
                            TestToast.makeText(Administration.this, "No text entered", Toast.LENGTH_SHORT).show();
                        else
                        {
                            if(categoryExist(dialogAddCategory.getText().toString()))
                                TestToast.makeText(Administration.this, "The category already exists", Toast.LENGTH_SHORT).show();
                            else
                            {
                                categories.add(new Category(dialogAddCategory.getText().toString()));
                                updateListData();
                                TestToast.makeText(Administration.this, "Category added : " + dialogAddCategory.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialogAdd.cancel();
                        break;
                    case R.id.DialogEditText_ButtonCancel:
                        TestToast.makeText(Administration.this, "Canceled", Toast.LENGTH_SHORT).show();
                        dialogAdd.cancel();
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

    public void RenameCategory(final int position){

        final Dialog dialogRename = new Dialog(Administration.this);
        dialogRename.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRename.setContentView(R.layout.dialog_edittext_okcancel);
        dialogRename.show();

        TextView text = (TextView)dialogRename.findViewById(R.id.DialogEditText_TextView);
        text.setText(getResources().getString(R.string.QuestionRenameCategory));
        final EditText dialogRenameCategory = (EditText)dialogRename.findViewById(R.id.DialogEditText_Text);
        Button OkButton = (Button)dialogRename.findViewById(R.id.DialogEditText_ButtonOk);
        Button CancelButton = (Button)dialogRename.findViewById(R.id.DialogEditText_ButtonCancel);

        View.OnClickListener clickListenerButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.DialogEditText_ButtonOk:
                        if(dialogRenameCategory.getText().toString().equals(""))
                            TestToast.makeText(Administration.this, "No text entered", Toast.LENGTH_SHORT).show();
                        else
                        {
                            renameCategoryByName(get_CategoryByName(listDataHeader.get(position)).get_name(), dialogRenameCategory.getText().toString());
                            updateListData();
                            TestToast.makeText(Administration.this, "Category has been renamed in : " + dialogRenameCategory.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        dialogRename.cancel();
                        break;
                    case R.id.DialogEditText_ButtonCancel:
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

    public void DeleteCategory(final int position){

        final Dialog dialogDelete = new Dialog(Administration.this);
        dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDelete.setContentView(R.layout.dialog_yesno);
        dialogDelete.show();

        TextView text = (TextView)dialogDelete.findViewById(R.id.DialogYesNo_TextView);
        text.setText(getResources().getString(R.string.QuestionDeleteCategory));
        Button YesButton = (Button)dialogDelete.findViewById(R.id.DialogYesNo_ButtonYes);
        Button NoButton = (Button)dialogDelete.findViewById(R.id.DialogYesNo_ButtonNo);

        View.OnClickListener clickListenerButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.DialogYesNo_ButtonYes:
                        removeCategoryByName(get_CategoryByName(listDataHeader.get(position)).get_name());
                        //listDataHeader.remove(position);
                        updateListData();
                        dialogDelete.cancel();
                        break;
                    case R.id.DialogYesNo_ButtonNo:
                        dialogDelete.cancel();
                        break;
                    default:
                        //TODO
                        break;
                }
            }
        };
        YesButton.setOnClickListener(clickListenerButtons);
        NoButton.setOnClickListener(clickListenerButtons);
    }

    /*
    * updating the list data
    */
    private void updateListData() {
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
        listAdapter=null;
        listAdapter = new ExpandableListAdapter(getBaseContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }
}