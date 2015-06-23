/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import java.util.Map;

/**
 *
 * @author jetdario
 */
public class TQAnswerKey extends TQItems {
    
    private int tqAnswerKeyId;
    private int cellItemId;
    private int itemNo;
    private String answer;
//    private Map<Integer, String> itemNoAndAnswer;

    public int getTqAnswerKeyId() {
        return tqAnswerKeyId;
    }

    public int getCellItemId() {
        return cellItemId;
    }

    public int getItemNo() {
        return itemNo;
    }

    public String getAnswer() {
        return answer;
    }

//    public Map<Integer, String> getItemNoAndAnswer() {
//        return itemNoAndAnswer;
//    }

    public void setTqAnswerKeyId(int tqAnswerKeyId) {
        this.tqAnswerKeyId = tqAnswerKeyId;
    }

    public void setCellItemId(int cellItemId) {
        this.cellItemId = cellItemId;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

//    public void setItemNoAndAnswer(Map<Integer, String> itemNoAndAnswer) {
//        this.itemNoAndAnswer = itemNoAndAnswer;
//    }
    
}
