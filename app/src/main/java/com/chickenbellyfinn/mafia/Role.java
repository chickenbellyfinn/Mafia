package com.chickenbellyfinn.mafia;

/**
 * Created by Akshay on 7/19/2015.
 */
public class Role {


    public String name;
    public int min;
    public int max;
    public int count;

    public Role(String name, int min, int max, int initial){
        this.name = name;
        this.min = min;
        this.max = max;
        this.count = initial;
    }

}
