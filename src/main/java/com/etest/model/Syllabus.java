/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

/**
 *
 * @author jetdario
 */
public class Syllabus extends Curriculum {
    
    private int syllabusId;
    private int topicNo;
    private String topic;
    private float estimatedTime;

    public int getSyllabusId() {
        return syllabusId;
    }

    public int getTopicNo() {
        return topicNo;
    }

    public String getTopic() {
        return topic;
    }

    public float getEstimatedTime() {
        return estimatedTime;
    }

    public void setSyllabusId(int syllabusId) {
        this.syllabusId = syllabusId;
    }

    public void setTopicNo(int topicNo) {
        this.topicNo = topicNo;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setEstimatedTime(float estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    
}
