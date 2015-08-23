package com.thenextmediumsizedthing.bondfire;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GroupClass implements Parcelable{

    private String name;
    private List<Person> members;
    private double avgRating;
    private int numRatings;
    private List<Review> reviews;
    private int image;
    private Intent chat;

    public GroupClass(String Name, int imageResID){
        name = Name;
        avgRating = 0.0;
        numRatings = 0;
        members = new ArrayList<Person>();
        image = imageResID;
        reviews = new ArrayList<Review>();
//        if (Name.equals("Bay Area Music")) {
            members.add(new Person("Jessica","Chiu","jessica"));
            members.add(new Person("Anne","Lin","anne"));
            members.add(new Person("Kenny","Dang","kenny"));
//            members.add(new Person("Trini","Kwan","trini"));
            /*members.add(new Person("Kimberly","Ann Hart","kim"));*/
//        }
    }

    public void add(Person p) {
        System.out.println(p.getfName());
        members.add(p);
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

    public String getName(){
        return this.name;
    }

    public double getAvgRating(){
        return this.avgRating;
    }

    public double getNumRatings(){
        return this.numRatings;
    }

    public int getImageId() {
        return this.image;
    }

    public List<Person> getMembers(){
        return this.members;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(image);
    }

    public static final Parcelable.Creator<GroupClass> CREATOR
            = new Parcelable.Creator<GroupClass>() {
        public GroupClass createFromParcel(Parcel in) {
            return new GroupClass(in.readString(),in.readInt());
        }

        public GroupClass[] newArray(int size) {
            return new GroupClass[size];
        }
    };
}
