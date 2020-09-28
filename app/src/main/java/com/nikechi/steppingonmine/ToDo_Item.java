package com.nikechi.steppingonmine;

import java.io.Serializable;

public class ToDo_Item implements Serializable {
    //MEMBER ATTRIBUTES
    private int _id;
    private String name;
    private long time;
    public ToDo_Item() {
    }
    public ToDo_Item(String n, long t) {

        name = n;
        time = t;
    }
    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }
    public String getName () {
        return name;
    }
    public void setName (String n) {
        name = n;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long t) {
        time = t;
    }
}
