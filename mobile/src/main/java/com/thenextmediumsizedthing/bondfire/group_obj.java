package com.thenextmediumsizedthing.bondfire;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by annelin on 8/2/15.
 */
public class group_obj {

    private String group_name;
    private Drawable group_picture;
    private ArrayList<Object> group_members = new ArrayList<Object>();
    private String group_plan;
    private String group_blurb;
    private String[] group_tags;
    private boolean active;

    public group_obj(){
        this.reset_tags();
        this.active = true;
    }
    public void set_group(String name, ArrayList<Object> members,
                          String plan, String blurb, String[] tags){
        group_name = name;
        group_plan = plan;
        group_blurb = blurb;
//        group_picture = picture;
        group_members = members;
        set_tags(tags);
    }

    public void set_tags(String[] args){
        if (args.length == 0){
            reset_tags();
        } else {
            for (int i = 0; i < 3; i++) {
                this.group_tags[i] = args[i];
            }
        }
    }
    public void toggle(String x){
        if (x.equals("Inactive")){
            active = false;
        } else {
            active = true;
        }
    }

    private void reset_tags(){
        this.group_tags = new String[]{"", "", "", "Default: Other"};
    }

    public String get_name(){
        return this.group_name;
    }

    public Drawable get_image(){
        return this.group_picture;
    }

    public ArrayList<Object> get_members(){
        return this.group_members;
    }

    public String get_plan(){
        return this.group_plan;
    }

    public String get_blurb(){
        return this.group_blurb;
    }

    public String[] get_tags(){
        return this.group_tags;
    }
    public boolean get_active(){
        return this.active;
    }
}
