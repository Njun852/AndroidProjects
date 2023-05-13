package com.example.reviewr;

import java.util.ArrayList;

public class Topic extends Subject{
   private String topicName,topicDetails;
    public Topic() {
    }
    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public String getTopicDetails() {
        return topicDetails;
    }
    public void setTopicDetails(String topicDetails) {
        this.topicDetails = topicDetails;
    }

}
