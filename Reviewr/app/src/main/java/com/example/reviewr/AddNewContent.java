package com.example.reviewr;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewContent extends AppCompatActivity {

    private EditText contentTitle;
    private EditText contentDescription;
    private TextView noTitle;
    private Button addContentBtn;
    private Button clearSubBtn;
    public static String fieldType;

    public static void addNewContent(String type, Context context){
        AddNewContent.fieldType = type;
        Intent intent = new Intent(context, AddNewContent.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        contentTitle = findViewById(R.id.iSubTitle);
        contentDescription = findViewById(R.id.iSubDescription);
        noTitle = findViewById(R.id.noTitle);
        addContentBtn = findViewById(R.id.okBtn);
        clearSubBtn = findViewById(R.id.clearBtn);

        switch (fieldType){
            case "subject":
                contentTitle.setHint("Subject Title");
                getSupportActionBar().setTitle("Add Subject");
                break;
            case "topic":
                contentTitle.setHint("Topic Title");
                getSupportActionBar().setTitle("Add Topic");
                break;
        }
        addContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewContent();
            }
        });

        clearSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearContent();
            }
        });

    }

    private void addNewSub(int[] rgb){
        Intent intent = new Intent(this, MainActivity.class);
          Subject sub = new Subject();
          sub.setRgb(rgb);
          sub.setTitle(contentTitle.getText().toString());
          String subText = contentDescription.getText().toString();
          if(subText.equals("")){
              subText = "No description.";
          }
          sub.setDescription(subText);
          Subject.Sublist().add(sub);
          MainActivity.saveData();
          startActivity(intent);
          finish();
    }
    private void addNewContent(){
        boolean isValid = true;
        if(contentTitle.getText().toString().equals("")){
            noTitle.setVisibility(View.VISIBLE);
            isValid = false;
        }

        if(isValid){
            int r = ((int)(Math.random()*255));
            int g = ((int)(Math.random()*255));
            int b = ((int)(Math.random()*255));
            int[] rgb = {r, g, b};
            switch (fieldType){
                case "subject":
                    addNewSub(rgb);
                    break;
                case "topic":
                    addNewTopic(rgb);
                    break;
            }
        }else{
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        }

    }
    private void addNewTopic(int rgb[]){
        Intent intent = new Intent(this, activity_subject.class);
        Topic topic = new Topic();
        topic.setRgb(rgb);
        topic.setTopicName(contentTitle.getText().toString());
        String TopicDescription = contentDescription.getText().toString();
        if(TopicDescription.equals("")){
            TopicDescription = "No description.";
        }
        topic.setTopicDetails(TopicDescription);
        Subject.Sublist().get(Subject.index).getTopiclist().add(topic);

        MainActivity.saveData();
        startActivity(intent);
        finish();

    }

    private void clearContent(){
        contentTitle.setText("");
        contentDescription.setText("");
    }



}