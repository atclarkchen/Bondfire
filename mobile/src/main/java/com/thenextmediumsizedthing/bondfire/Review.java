package com.thenextmediumsizedthing.bondfire;


import java.util.ArrayList;
import java.util.List;

public class Review {

    private int stars;
    private String written;
    private List<Boolean> starsBool;

    public Review(int numStars,String text) {
        stars = numStars;
        written = text;
        starsBool= new ArrayList<Boolean>();
        for (int i=1;i <= 5;i++){
            if (i <= numStars){
                starsBool.add(true);
            } else {
                starsBool.add(false);
            }
        }
    }

    public int getRating() {
        return this.stars;
    }

    public String getWritten() {
        return this.written;
    }

    public List<Boolean> getStarsBool(){
        return starsBool;
    }

}
