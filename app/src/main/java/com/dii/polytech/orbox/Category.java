package com.dii.polytech.orbox;

import java.util.ArrayList;

public class Category {
    private String _name ;
    private ArrayList<ObjectOrbox> _objectsOrbox ;

    public Category()
    {
        _name = "";
        _objectsOrbox = new ArrayList<ObjectOrbox>();
    }

    public Category(String name){
        _name=name;
        _objectsOrbox = new ArrayList<ObjectOrbox>();
    }

    public Category(String _name, ArrayList<ObjectOrbox> objectsOrbox){
        this._name=_name;
        _objectsOrbox = objectsOrbox;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public ArrayList<ObjectOrbox> get_ObjectsOrbox() {
        return _objectsOrbox;
    }

    public void set_ObjectsOrbox(ArrayList<ObjectOrbox> _objectsOrbox) {
        this._objectsOrbox = _objectsOrbox;
    }

    public void add_ObjectOrbox(ObjectOrbox obj){
        _objectsOrbox.add(obj);
    }

    public void remove_ObjectOrbox(ObjectOrbox obj){
        _objectsOrbox.remove(obj);
    }

    public int get_ObjectOrboxsSize(){
        return _objectsOrbox.size();
    }

    public void clear_ObjectsOrbox(){
        _objectsOrbox.clear();
    }

    public String toString() {
        return "I'm a category named " + _name +
                " wich have " + Integer.toString(get_ObjectOrboxsSize()) +
                " objects." ;
    }

    public ObjectOrbox get_ObjectOrboxByName(String name)
    {
        for(ObjectOrbox obj : _objectsOrbox)
        {
            if(obj.get_name().equals(name)) return obj;
        }

        return null;
    }
}
