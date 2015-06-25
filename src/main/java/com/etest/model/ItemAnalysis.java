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
    private List<Integer> itemNumber;
    private List<Character> answer;

    public String getStudentNumber() {
        return StudentNumber;
    }

    public List<Integer> getItemNumber() {
        return itemNumber;
    }

    public List<Character> getAnswer() {
        return answer;
    }

    public void setStudentNumber(String StudentNumber) {
        this.StudentNumber = StudentNumber;
    }

    public void setItemNumber(List<Integer> itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setAnswer(List<Character> answer) {
        this.answer = answer;
    }
    
}
