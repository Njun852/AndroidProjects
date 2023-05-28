package com.example.reviewr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewrAdapter extends RecyclerView.Adapter<ReviewrAdapter.ViewHolder> {

    Context context;
    String RecViewName;
    public static int size;
    ReviewrAdapter(Context context, String RecViewName){
        this.context = context;
        this.RecViewName = RecViewName;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recviewlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = position;
        switch(this.RecViewName){
            case "subject":
                subjectRecview(holder,index);
                break;
            case "topic":
                topicRecview(holder, Subject.index, index);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return size;
    }

    private void subjectRecview(@NonNull ViewHolder holder, int index){
        holder.title.setText(Subject.sublist.get(index).getTitle());
        holder.description.setText(Subject.sublist.get(index).getDescription());

        int r = Subject.sublist.get(index).getRgb()[0];
        int g = Subject.sublist.get(index).getRgb()[1];
        int b = Subject.sublist.get(index).getRgb()[2];
        holder.bars.setBackgroundColor(Color.rgb(r, g, b));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity_subject.class);
                Subject.index = index;
                context.startActivity(intent);
            }
        });
    }

    private void topicRecview(@NonNull ViewHolder holder, int subjectIndex, int topicIndex){
        holder.title.setText(Subject.sublist.get(subjectIndex).getTopiclist().get(topicIndex).getTopicName());
        holder.description.setText(Subject.sublist.get(subjectIndex).getTopiclist().get(topicIndex).getTopicDetails());
        int r = Subject.Sublist().get(subjectIndex).getTopiclist().get(topicIndex).getRgb()[0];
        int g = Subject.Sublist().get(subjectIndex).getTopiclist().get(topicIndex).getRgb()[1];
        int b = Subject.Sublist().get(subjectIndex).getTopiclist().get(topicIndex).getRgb()[2];
        holder.bars.setBackgroundColor(Color.rgb(r, g, b));
    }
    public void setSubjects(ArrayList<Subject> sub){
        Subject.Sublist(sub);
        notifyDataSetChanged();
    }

    public void setTopics(ArrayList<Topic> topic){
        Subject.Sublist().get(Subject.index).setTopiclist(topic);
        notifyDataSetChanged();
    }
    public static void setRecView(RecyclerView recView, Context context, String RecViewName){

        switch(RecViewName){
            case "subject":
                ReviewrAdapter adapter = new ReviewrAdapter(context, RecViewName);
                size = Subject.Sublist().size();
                adapter.setSubjects(Subject.Sublist());
                recView.setAdapter(adapter);
                break;
            case "topic":
                ReviewrAdapter adapter2 = new ReviewrAdapter(context, RecViewName);
                size = Subject.Sublist().get(Subject.index).getTopiclist().size();
                adapter2.setTopics(Subject.Sublist().get(Subject.index).getTopiclist());
                recView.setAdapter(adapter2);
                break;
        }

        recView.setLayoutManager(new LinearLayoutManager(context));
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, description;

        private RelativeLayout container, bars;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            description = itemView.findViewById(R.id.description);
            bars = itemView.findViewById(R.id.bars);
            container = itemView.findViewById(R.id.Container);
        }
    }
}
