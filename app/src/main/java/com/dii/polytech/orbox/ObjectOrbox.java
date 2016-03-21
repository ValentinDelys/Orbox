package com.dii.polytech.orbox;

import java.util.ArrayList;

public class ObjectOrbox {
    private String _name;
    private String _oralDescriptor;
    private ArrayList<Descriptor> _descriptors ;

    public ObjectOrbox(){
        this._name="";
        _oralDescriptor="";
        _descriptors= new ArrayList<Descriptor>();
    }

    public ObjectOrbox(String _name){
        this._name=_name;
        _oralDescriptor="";
        _descriptors= new ArrayList<Descriptor>();
    }

    public ObjectOrbox(String _name, String _oralDescriptor){
        this._name=_name;
        this._oralDescriptor=_oralDescriptor;
        _descriptors= new ArrayList<Descriptor>();
    }

    public ObjectOrbox(String _name, String _oralDescriptor, ArrayList<Descriptor> descriptors){
        this._name=_name;
        this._oralDescriptor=_oralDescriptor;
        _descriptors= descriptors;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_oralDescriptor() {
        return _oralDescriptor;
    }

    public void set_oralDescriptor(String _oralDescriptor) {
        this._oralDescriptor = _oralDescriptor;
    }

    public ArrayList<Descriptor> get_Descriptors() {
        return _descriptors;
    }

    public void set_Descriptors(ArrayList<Descriptor> _descriptors) {
        this._descriptors = _descriptors;
    }

    public void add_Descriptor(Descriptor Des){
        _descriptors.add(Des);
    }

    public void remove_Descriptor(Descriptor Des){
        _descriptors.remove(Des);
    }

    public int get_DescriptorSize(){
        return _descriptors.size();
    }

    public void clear_Descriptors(){
        _descriptors.clear();
    }

    public String toString() {
        return "I'm an object named " + _name +
                ", my oral description is " + _oralDescriptor +
                " and I have " + Integer.toString(get_DescriptorSize()) +
                " descriptors." ;
    }

    public Descriptor get_ObjectOrboxByName(String name)
    {
        for(Descriptor descriptor : _descriptors)
        {
            if(descriptor.get_name().equals(name)) return descriptor;
        }

        return null;
    }
}
