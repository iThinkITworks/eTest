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
public class ItemKeys extends CellItem {
    
    private int itemKeyId;
    private String itemKey;
    private String answer;

    public int getItemKeyId() {
        return itemKeyId;
    }

    public String getItemKey() {
        return itemKey;
    }

    public String getAnswer() {
        return answer;
    }

    public void setItemKeyId(int itemKeyId) {
        this.itemKeyId = itemKeyId;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
}
