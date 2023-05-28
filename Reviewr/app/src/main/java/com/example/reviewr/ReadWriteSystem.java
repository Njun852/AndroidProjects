package com.example.reviewr;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReadWriteSystem {
    private static String data = "myData";
    private static Gson gson;
    private static SharedPreferences myPref;
    public static File folder;
    public static File file;


    /*public static void writeData(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(int i = 0; i < Subject.Sublist().size(); i++){
                Subject sub = Subject.Sublist().get(i);
                String subTitle = "[!"+sub.getTitle()+"!]";
                String subDescription = "[@"+sub.getDescription()+"@]";
                String subBars = "[#("+sub.getRgb()[0]+")("+sub.getRgb()[1]+")("+sub.getRgb()[2]+")#]";
                writer.write("\n"+startOfSub);
                writer.write("\n"+subTitle);
                writer.write("\n"+subDescription);
                writer.write("\n"+subBars);


                for(int j = 0; j < sub.getTopiclist().size(); j++){
                    Topic topic = sub.getTopiclist().get(j);
                    String topicTitle = "\n[!"+topic.getTopicName()+"!]";
                    String topicDescription = "\n[@"+topic.getTopicDetails()+"@]";
                    String topicBars = "\n[#("+topic.getRgb()[0]+")("+topic.getRgb()[1]+")("+topic.getRgb()[2]+")#]";
                    writer.write("\n"+startOfTopic);
                    writer.write(topicTitle);
                    writer.write(topicDescription);
                    writer.write(topicBars);
                    writer.write("\n"+endOfTopic);
                }
                writer.write("\n"+endOfSub);
            }
            writer.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public static ArrayList<Subject> readData(){


        ArrayList<Subject> sublist = new ArrayList<>();
        try{
            reader = new BufferedReader(new FileReader(file));
             line = reader.readLine();
            String currentData = "";
            Subject sub = new Subject();
            Topic newtopic = new Topic();
            ArrayList<Topic> topiclist = new ArrayList<>();

            while(line != null){

                if(line.trim().equals("")){
                    line = reader.readLine();
                    continue;
                }
                System.out.println("line: "+line);
                if(line.equals(startOfSub)){
                    currentData = subject;
                    topiclist = new ArrayList<>();
                    sub = new Subject();
                }
                if(currentData.equals(subject)){
                    switch (line.charAt(1)){
                        case '!':
                            sub.setTitle(startReading(line, '!'));
                            break;
                        case '@':
                            sub.setDescription(startReading(line, '@'));
                            break;
                        case '#':
                            sub.setRgb(readBars(line));
                            break;
                    }
                }
                if(line.equals(startOfTopic)){
                    currentData = topic;
                    newtopic = new Topic();
                }
                if(currentData.equals(topic)){
                    switch (line.charAt(1)){
                        case '!':
                            newtopic.setTopicName(startReading(line, '!'));
                            break;
                        case '@':
                            newtopic.setTopicDetails(startReading(line, '@'));
                            break;
                        case '#':
                           newtopic.setRgb(readBars(line));
                            break;
                    }
                }
                if(line.equals(endOfTopic)){
                    System.out.println("Topic Name: "+newtopic.getTopicName());
                    topiclist.add(newtopic);
                }
                if(line.equals(endOfSub)){
                    sub.setTopiclist(topiclist);

                    sublist.add(sub);
                }
                line = reader.readLine();
            }
            reader.close();
        }catch (IOException io){
            io.printStackTrace();
        }
        return sublist;
    }


    public static String startReading(String lines, char datatype) throws IOException {
        String data = "";
        boolean hasNext = false;
        for(int i = 0; i < lines.length(); i++){
            if(!lines.contains(datatype+"]")){
                hasNext = true;
            }
            if(lines.charAt(i) == '[' || lines.charAt(i) == ']' || lines.charAt(i) == datatype){
                continue;
            }
            data += lines.charAt(i);
        }

        if(hasNext){
            try{
                line = reader.readLine();
                return data+"\n"+startReading(line, datatype);
            }catch (IOException io){
                io.printStackTrace();
            }

        }
        return data;
    }

    public static int[] readBars(String line){
        String r = "";
        String g = "";
        String b = "";
        int index = 0;
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == '[' || line.charAt(i) == ']' || line.charAt(i) == '#' || line.charAt(i) == '('){
                continue;
            }
            if(line.charAt(i) == ')'){
                index+=1;
                continue;
            }
            switch (index){
                case 0:
                    r += line.charAt(i);
                    break;
                case 1:
                    g+= line.charAt(i);
                    break;
                case 2:
                    b += line.charAt(i);
                    break;
            }
        }
        int rgb[] = {Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b)};
        return rgb;
    }*/



    public static void writeData(){
        SharedPreferences.Editor editor = myPref.edit();
        String json = gson.toJson(Subject.Sublist());
        editor.putString(data, json);
        editor.apply();
    }
    public static ArrayList<Subject> readData(Context context){
        myPref = context.getSharedPreferences(data, Context.MODE_PRIVATE);
        gson = new Gson();
        String json = myPref.getString(data, null);
        System.out.println(json);
        Type type = new TypeToken<ArrayList<Subject>>(){}.getType();
        return gson.fromJson(json, type);
    }

   /* public static void saveData(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(MainActivity.file));
            for(int i = 0; i < Subject.Sublist().size(); i++){
                Subject sub = Subject.Sublist().get(i);
                String subTitle = "*[!"+sub.getTitle()+"]";
                String subDescription = "[@"+sub.getDescription()+"]";
                String bars = "[#("+sub.getRgb()[0]+")("+sub.getRgb()[1]+")("+sub.getRgb()[2]+")]";

                String topiclist = "";
                for(int j = 0; j < Subject.Sublist().get(i).getTopiclist().size(); j++){
                    Topic topic = sub.getTopiclist().get(j);
                    String topicTitle = "$[!"+topic.getTopicName()+"]";
                    String topicDescription = "[@"+topic.getTopicDetails()+"]";
                    String topicBars = "[#("+topic.getRgb()[0]+")("+topic.getRgb()[1]+")("+topic.getRgb()[2]+")]";
                    String topicData = topicTitle+topicDescription+topicBars;
                    topiclist+=topicData;
                }
                String currentData = subTitle+subDescription+bars+topiclist+"`";
                writer.write("\n"+currentData);
            }
            writer.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    public static ArrayList<Subject> loadData(){

        ArrayList<Subject> subject = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader((new FileReader(MainActivity.file)));
            String line = reader.readLine();
            while(line != null){
                if(line.equals("")){
                    line = reader.readLine();
                    continue;
                }
                String title = "";
                String description = "";
                int[] bars = {0, 255, 0};
                Subject sub = new Subject();
                boolean readTitle = false;
                boolean readTopic = false;
                ArrayList<Topic> topiclist = new ArrayList<>();
                int set = 0;
                String topicTitle = "";
                String topicDescription = "";
                int[] topicBars = {255, 0, 0};
                for(int i = 0; i < line.length(); i++){
                    char index = line.charAt(i);
                    if(line.charAt(i) == '$' || readTopic){
                        readTopic = true;
                        switch(index){
                            case '!':
                                topicTitle = startReading(line, i);
                                set+=1;
                                break;
                            case '@':
                                topicDescription = startReading(line, i);
                                set+=1;
                                break;
                            case '#':
                                topicBars = readBars(startReading(line, i));
                                readTopic = false;
                                set+=1;
                                break;
                        }
                        if(set == 3){
                            Topic topic = new Topic();
                            topic.setRgb(topicBars);
                            topic.setTopicName(topicTitle);
                            System.out.println(topicDescription+" eee");
                            topic.setTopicDetails(topicDescription);
                            set = 0;
                            topiclist.add(topic);
                        }
                    }else if(line.charAt(i) == '*'|| readTitle){
                        readTitle = true;
                        switch(index){
                            case '!':
                                title = startReading(line, i);
                                break;
                            case '@':
                                description = startReading(line, i);
                                break;
                            case '#':
                                bars = readBars(startReading(line, i));
                                readTitle = false;
                                break;
                        }
                    }
                }
                sub.setTopiclist(topiclist);
                sub.setRgb(bars);
                sub.setTitle(title);
                sub.setDescription(description);
                subject.add(sub);
                line = reader.readLine();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return subject;
    }
    public static String startReading(String line, int startPoint){
        String lineRead = "";
        int i = startPoint+1;
        if(line.charAt(i-1) == '#'){
            String rgb = "";
            int index = 0;

            for(int j = i; i < line.length(); j++){
                if(line.charAt(j) == ']'){
                    break;
                }
                System.out.println("Hello");
                rgb+=line.charAt(j);
            }
            lineRead = rgb;
        }else{
            while(line.charAt(i)!= ']'){
                lineRead += line.charAt(i);
                i++;
            }
        }
        return lineRead;
    }
    public static int[] readBars(String rgb){
        String r = "";
        String g = "";
        String b = "";
        int val = 0;
        for(int j = 0; j < rgb.length(); j++){
            if(rgb.charAt(j) == '('){
                continue;
            }
            if(rgb.charAt(j) == ')'){
                val+=1;
                continue;
            }
            switch(val){
                case 0:
                    r+=rgb.charAt(j);
                    break;
                case 1:
                    b+=rgb.charAt(j);
                    break;
                case 2:
                    g+=rgb.charAt(j);
                    break;
            }
        }

        int rgbBars[] = {Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b)};
        return rgbBars;
    }
   /* public static void saveData(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < Subject.Sublist().size(); i++){
                Subject sub = Subject.Sublist().get(i);
                String subTitle = "*[!"+sub.getTitle()+"]";
                String subDescription = "[@"+sub.getDescription()+"]";
                String bars = "[#("+sub.getRgb()[0]+")("+sub.getRgb()[1]+")("+sub.getRgb()[2]+")]";

                String topiclist = "";
                for(int j = 0; j < Subject.Sublist().get(i).getTopiclist().size(); j++){
                    Topic topic = sub.getTopiclist().get(j);
                    String topicTitle = "$[!"+topic.getTopicName()+"]";
                    String topicDescription = "[@"+topic.getTopicDetails()+"]";
                    String topicBars = "[#("+topic.getRgb()[0]+")("+topic.getRgb()[1]+")("+topic.getRgb()[2]+")]";
                    String topicData = topicTitle+topicDescription+topicBars;
                    topiclist+=topicData;
                }
                String currentData = subTitle+subDescription+bars+topiclist+"`";
                writer.write("\n"+currentData);
            }
            writer.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }/*
    /*public ArrayList<Subject> loadData(){

        ArrayList<Subject> subject = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader((new FileReader(file)));
            String line = reader.readLine();
            while(line != null){
                if(line.equals("")){
                    line = reader.readLine();
                    continue;
                }
                String title = "";
                String description = "";
                int[] bars = {0, 255, 0};
                Subject sub = new Subject();
                boolean readTitle = false;
                boolean readTopic = false;
                ArrayList<Topic> topiclist = new ArrayList<>();
                int set = 0;
                String topicTitle = "";
                String topicDescription = "";
                int[] topicBars = {255, 0, 0};
                for(int i = 0; i < line.length(); i++){
                    char index = line.charAt(i);
                    if(line.charAt(i) == '$' || readTopic){
                        readTopic = true;
                        switch(index){
                            case '!':
                                topicTitle = startReading(line, i);
                                set+=1;
                                break;
                            case '@':
                                topicDescription = startReading(line, i);
                                set+=1;
                                break;
                            case '#':
                                topicBars = readBars(startReading(line, i));
                                readTopic = false;
                                set+=1;
                                break;
                        }
                        if(set == 3){
                            Topic topic = new Topic();
                            topic.setRgb(topicBars);
                            topic.setTopicName(topicTitle);
                            System.out.println(topicDescription+" eee");
                            topic.setTopicDetails(topicDescription);
                            set = 0;
                            topiclist.add(topic);
                        }
                    }else if(line.charAt(i) == '*'|| readTitle){
                        readTitle = true;
                        switch(index){
                            case '!':
                                title = startReading(line, i);
                                break;
                            case '@':
                                description = startReading(line, i);
                                break;
                            case '#':
                                bars = readBars(startReading(line, i));
                                readTitle = false;
                                break;
                        }
                    }
                }
                sub.setTopiclist(topiclist);
                sub.setRgb(bars);
                sub.setTitle(title);
                sub.setDescription(description);
                subject.add(sub);
                line = reader.readLine();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return subject;
    }

    public String startReading(String line, int startPoint){
        String lineRead = "";
        int i = startPoint+1;
        if(line.charAt(i-1) == '#'){
            String rgb = "";
            int index = 0;

            for(int j = i; i < line.length(); j++){
                if(line.charAt(j) == ']'){
                    break;
                }
                System.out.println("Hello");
                rgb+=line.charAt(j);
            }
            lineRead = rgb;
        }else{
            while(line.charAt(i)!= ']'){
                lineRead += line.charAt(i);
                i++;
            }
        }
        return lineRead;
    }
    public int[] readBars(String rgb){
        String r = "";
        String g = "";
        String b = "";
        int val = 0;
        for(int j = 0; j < rgb.length(); j++){
            if(rgb.charAt(j) == '('){
                continue;
            }
            if(rgb.charAt(j) == ')'){
                val+=1;
                continue;
            }
            switch(val){
                case 0:
                    r+=rgb.charAt(j);
                    break;
                case 1:
                    b+=rgb.charAt(j);
                    break;
                case 2:
                    g+=rgb.charAt(j);
                    break;
            }
        }

        int rgbBars[] = {Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b)};
        return rgbBars;
    }*/
}
