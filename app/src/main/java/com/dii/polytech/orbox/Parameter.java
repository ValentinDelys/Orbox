package com.dii.polytech.orbox;

/**
 * Created by Dylan on 08/05/2016.
 */
public class Parameter {
    private String language = "";
    private int sound = 0;
    private int light = 0;

    public String get_language() {
        return language;
    }
    public void set_language(String language) {
        this.language = language;
    }
    public int get_sound() {
        return sound;
    }
    public void set_sound(int sound) {
        this.sound = sound;
    }
    public int get_light() {
        return light;
    }
    public void set_light(int light) {
        this.light = light;
    }
}
