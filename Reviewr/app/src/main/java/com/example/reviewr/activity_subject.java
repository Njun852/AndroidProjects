package com.example.reviewr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_subject extends AppCompatActivity {

    TextView noTopics;
    RecyclerView recView;
    Button addTopicBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        noTopics = findViewById(R.id.noTopicFound);
        recView = findViewById(R.id.topicRecView);
        addTopicBtn = findViewById(R.id.addnewTopic);
        getSupportActionBar().setTitle(Subject.sublist.get(Subject.index).getTitle()+" Topics");

        start();
        addTopicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewContent.addNewContent("topic", view.getContext());
            }
        });
    }

    private void start(){
        if(Subject.Sublist().get(Subject.index).getTopiclist().isEmpty()){
            noTopics.setVisibility(View.VISIBLE);
        }else{
            noTopics.setVisibility(View.GONE);
            ReviewrAdapter.setRecView(recView, this, "topic");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }
}