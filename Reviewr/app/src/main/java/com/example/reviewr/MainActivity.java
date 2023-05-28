package com.example.reviewr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

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
            Subject.Sublist(ReadWriteSystem.readData(this));
        }
        if(Subject.Sublist().isEmpty()){
            notFound.setVisibility(View.VISIBLE);
        }else{
            notFound.setVisibility(View.GONE);
            ReviewrAdapter.setRecView(recView,this, "subject");
        }
    }
}