/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class TQItems extends TQCases {
    
    private int tqItemId;
    private int itemKeyId;
    private Map<Integer, List<TQAnswerKey>> cellCaseItemKey;

    public int getTqItemId() {
        return tqItemId;
    }

    public int getItemKeyId() {
        return itemKeyId;
    }

    public Map<Integer, List<TQAnswerKey>> getCellCaseItemKey() {
        return cellCaseItemKey;
    }
    
    public void setTqItemId(int tqItemId) {
        this.tqItemId = tqItemId;
    }

    public void setItemKeyId(int itemKeyId) {
        this.itemKeyId = itemKeyId;
    }

    public void setCellCaseItemKey(Map<Integer, List<TQAnswerKey>> cellCaseItemKey) {
        this.cellCaseItemKey = cellCaseItemKey;
    }
    
}
