package com.dii.polytech.orbox;

public class Descriptor {
    private String _name;
    private boolean _active;
    private boolean _descriptorCalculated;
    private boolean _vignetteCreated;
    private int _x;
    private int _y;
    private int _width;
    private int _height;

    public Descriptor()
    {
        this._name="";
        _active=false;
        _descriptorCalculated=false;
        _vignetteCreated=false;
        _x=0;
        _y=0;
        _width=0;
        _height=0;
    }

    public Descriptor(String _name)
    {
        this._name=_name;
        _active=false;
        _descriptorCalculated=false;
        _vignetteCreated=false;
        _x=0;
        _y=0;
        _width=0;
        _height=0;
    }

    public Descriptor(String _name, boolean _active, boolean _descriptorCalculated, boolean _vignetteCreated,
                      int _x, int _y, int _width, int _height)
    {
        this._name=_name;
        this._active=_active;
        this._descriptorCalculated=_descriptorCalculated;
        this._vignetteCreated=_vignetteCreated;
        this._x=_x;
        this._y=_y;
        this._width=_width;
        this._height=_height;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public boolean is_active() {
        return _active;
    }

    public void set_active(boolean _active) {
        this._active = _active;
    }

    public boolean is_descriptorCalculated() {
        return _descriptorCalculated;
    }

    public void set_descriptorCalculated(boolean _descriptorCalculated) {
        this._descriptorCalculated = _descriptorCalculated;
    }

    public boolean is_vignetteCreated() {
        return _vignetteCreated;
    }

    public void set_vignetteCreated(boolean _vignetteCreated) {
        this._vignetteCreated = _vignetteCreated;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public int get_width() {
        return _width;
    }

    public void set_width(int _width) {
        this._width = _width;
    }

    public int get_height() {
        return _height;
    }

    public void set_height(int _height) {
        this._height = _height;
    }

    public String toString() {
        return "I'm a descriptor named " + _name +
                " and here are my properties : \n" +
                "Active : " + Boolean.toString(_active) +
                "\nDescriptor calculated : " + Boolean.toString(_descriptorCalculated) +
                "\nVignette calculated : " + Boolean.toString(_vignetteCreated) +
                "\nx : " + Integer.toString(_x) +
                "\ny : " + Integer.toString(_y) +
                "\nWidth : " + Integer.toString(_width) +
                "\nHeight : " + Integer.toString(_height) ;
    }
}
