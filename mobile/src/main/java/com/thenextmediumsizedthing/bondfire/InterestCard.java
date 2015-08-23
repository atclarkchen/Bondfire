package com.thenextmediumsizedthing.bondfire;

import android.widget.ImageView;

public class InterestCard {
    private int id;
    private String title;
    private String labelActiveGroups;
    private int numActiveGroups;
    private ImageView background;
    private boolean selected;

    public InterestCard() {
        super();
    }

    public InterestCard(int id, String title, int numActiveGroups) {
        this.id = id;
        this.title = title;
        this.labelActiveGroups = Integer.toString(numActiveGroups) + "  ";
        this.numActiveGroups = numActiveGroups;
        selected = false;
    }

    //getters and setters
    public String getTitle() {
        return this.title;
    }

    public String getLabelActiveGroups() {
        return labelActiveGroups;
    }

    public int getNumActiveGroups() {
        return this.numActiveGroups;//temporary hard coding.
    }

    /* returns post "select" boolean value */
    public boolean isSelected() {
        selected = !selected;
        return selected;
    }

}

