package com.thenextmediumsizedthing.bondfire;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class GroupCard {
    private int groupid;
    private String name;
    private String labelDistance;
    private Double distance;
    private ImageView background;
    private boolean selected;

    public GroupCard() {
        super();
    }

    public GroupCard(int groupid, String name, Double distance) {
        this.groupid = groupid;
        this.name = name;
        this.labelDistance = String.format("%.1f", distance) + " mi.  ";
        this.distance = distance;
        selected = false;
    }

    //getters and setters
    public String getName() {
        return this.name;
    }

    public String getLabelDistance() {
        return labelDistance;
    }

    public Double getNumActiveGroups() {
        return this.distance;//temporary hard coding.
    }

    /* returns post "select" boolean value */
    public boolean isSelected() {
        selected = !selected;
        return selected;
    }

}

