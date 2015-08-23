package com.thenextmediumsizedthing.bondfire;

import java.util.List;

public class Person {

    private String fName;
    private String lName;
    private double avgRating;
    private int numRatings;
    private List<Review> reviews;
    private String image;
    private int imageId;

    public Person(String firstName,String lastName,String img) {
        fName = firstName;
        lName = lastName;
        avgRating = 0.0;
        numRatings = 0;
        image = img;
    }

    public void updateAverage() {
        if (numRatings != 0) {
            double ratingSum = 0;
            for (Review r: reviews) {
                ratingSum += r.getRating();
            }
            avgRating = ratingSum/numRatings;
        }
    }

    public void addReview(int numStars, String text) {
        reviews.add(new Review(numStars,text));
        numRatings++;
        updateAverage();
    }

    public String getfName(){
        return this.fName;
    }

    public String getlName(){
        return this.lName;
    }

    public String getImage(){
        return this.image;
    }

    public double getAvgRating(){
        return this.avgRating;
    }

    public double getNumRatings(){
        return this.numRatings;
    }

    public void setImageId(int resID){
        this.imageId = resID;
    }

    public int getImageId() {
        return this.imageId;
    }



}
