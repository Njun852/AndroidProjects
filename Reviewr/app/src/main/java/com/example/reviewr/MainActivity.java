package com.example.reviewr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static File file;
    private RecyclerView recView;
    private TextView notFound;
    private Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notFound = findViewById(R.id.noSubFound);
        addBtn = findViewById(R.id.addnewSub);

        recView = findViewById(R.id.recView);
        file = new File(getFilesDir(), "save.txt");
        start();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewContent.addNewContent("subject", view.getContext());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    private void start(){
        if(Subject.sublist == null){
            Subject.Sublist(loadData());
        }
        if(Subject.Sublist().isEmpty()){
            notFound.setVisibility(View.VISIBLE);
        }else{
            notFound.setVisibility(View.GONE);
            ReviewrAdapter.setRecView(recView,this, "subject");
        }
    }

    /*public static void saveSave(){

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < Subject.Sublist().size(); i++){
                Subject sub = Subject.Sublist().get(i);
                String subject = "*!"+sub.getTitle()+"-";
                String description = "@"+sub.setDescription()+"-";
                String bars = "#("+sub.getRgb()[0]+")("+sub.getRgb()[1]+")("+sub.getRgb()[2]+")-";

                String startTopic = "";
                for(int j = 0; j < Subject.Sublist().get(i).getTopiclist().size(); j++){
                    Topic topic = Subject.Sublist().get(i).getTopiclist().get(j);
                    String topicName = "$!"+topic.getTopicName()+"-";
                    String topicDescription = "@"+topic.getTopicDetails()+"-";
                    String topicBars = "#("+topic.getRgb()[0]+")("+topic.getRgb()[1]+")("+topic.getRgb()[2]+")-%";
                    startTopic+=topicName+topicDescription+topicBars;
                }
                if(Subject.Sublist().get(i).getTopiclist().isEmpty()){
                    startTopic="$";
                }
                String currentData = "{"+subject+description+bars+startTopic+"}";
                writer.write("\n"+currentData);

            }
            writer.close();
        }catch (IOException io){
            io.printStackTrace();
        }

    }*/

    public static void saveData(){
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
                String currentData = subTitle+subDescription+bars+topiclist;
                writer.write("\n"+currentData);
            }
            writer.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }

   public ArrayList<Subject> loadData(){

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
    }

    /*public ArrayList<Subject> loadSave(){
        ArrayList<Subject> loadl = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader((new FileReader(file)));
            String line = reader.readLine();

            while(line != null){
                String subname = "";
                String disc = "";
                int r = 0;
                int g = 0;
                int b = 0;
                if(line.equals("")){
                    line = reader.readLine();
                    continue;
                }
                for(int i = 1; i < line.length()-1; i++){
                    if(line.charAt(i) == '['){
                        int j = i+1;

                        if(line.charAt(j) == '!'){
                            int a = j+1;
                            while(line.charAt(a) !=']'){
                                subname += line.charAt(a);
                                a+=1;
                            }
                        }else if(line.charAt(j) == '@'){
                            int a = j+1;
                            while(line.charAt(a) !=']'){
                                disc += line.charAt(a);
                                a+=1;
                            }
                        }else if(line.charAt(j) == '#'){
                            int a = j+1;
                            int index = 0;
                            String tempr = "";
                            String tempg = "";
                            String tempb = "";
                            while(line.charAt(a) !=']'){
                                if(line.charAt(a) == '('){

                                    if(index == 0){
                                        int k = a+1;
                                        while(line.charAt(k) != ')'){
                                            tempr += line.charAt(k);
                                            k+=1;
                                        }
                                    }else if(index == 1){
                                        int k = a+1;
                                        while(line.charAt(k) != ')'){
                                            tempg += line.charAt(k);
                                            k+=1;
                                        }
                                    }else if(index == 2){
                                        int k = a+1;
                                        while(line.charAt(k) != ')'){
                                            tempb += line.charAt(k);
                                            k+=1;
                                        }
                                    }
                                    index +=1;
                                }
                                a+=1;
                            }r = Integer.parseInt(tempr);
                            g = Integer.parseInt(tempg);
                            b = Integer.parseInt(tempb);
                        }
                    }
                }
                Subject currentSub = new Subject();
                currentSub.setTitle(subname);
                currentSub.setDescription(disc);
                int rgb[] = {r, g, b};
                currentSub.setRgb(rgb);
                loadl.add(currentSub);
                line = reader.readLine();
            }
            reader.close();
        }catch (Exception e){
        }
        return loadl;
    }*/
}