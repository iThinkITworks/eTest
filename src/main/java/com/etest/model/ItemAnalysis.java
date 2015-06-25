/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import java.util.List;

/**
 *
 * @author jetdario
 */
public class ItemAnalysis {
    
    private String StudentNumber;
    private int totalScore;
    private List<Character> answer;

    public String getStudentNumber() {
        return StudentNumber;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public List<Character> getAnswer() {
        return answer;
    }

    public void setStudentNumber(String StudentNumber) {
        this.StudentNumber = StudentNumber;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setAnswer(List<Character> answer) {
        this.answer = answer;
    }
    
}
