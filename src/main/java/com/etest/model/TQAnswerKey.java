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
public class TQAnswerKey extends TQCoverage {
    
    private int tqAnswerKeyId;
    private Map<Integer, String> itemNoAndAnswer;

    public int getTqAnswerKeyId() {
        return tqAnswerKeyId;
    }

    public Map<Integer, String> getItemNoAndAnswer() {
        return itemNoAndAnswer;
    }

    public void setTqAnswerKeyId(int tqAnswerKeyId) {
        this.tqAnswerKeyId = tqAnswerKeyId;
    }

    public void setItemNoAndAnswer(Map<Integer, String> itemNoAndAnswer) {
        this.itemNoAndAnswer = itemNoAndAnswer;
    }
    
}
