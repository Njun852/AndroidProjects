package com.example.reviewr;

import java.util.ArrayList;

public class Subject {
    public static ArrayList<Subject> sublist;
    public static int index;
    public Subject() {
    }
    private ArrayList<Topic> topiclist;
    public static ArrayList<Subject> Sublist(){

        if(sublist == null){
            sublist = new ArrayList<>();
        }
        return sublist;
    }
    public static void Sublist(ArrayList<Subject> newList){
        sublist = newList;
    }
    private String title, description;
    private int[] rgb;

    public int[] getRgb() {
        return rgb;
    }

    public ArrayList<Topic> getTopiclist() {
        if(topiclist == null){
            topiclist = new ArrayList<>();
        }
        return topiclist;
    }

    public void setTopiclist(ArrayList<Topic> topiclist) {
        this.topiclist = topiclist;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    public Subject(String Title, String description) {
        this.title = Title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String subTitle) {
        this.title = subTitle;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }
}
