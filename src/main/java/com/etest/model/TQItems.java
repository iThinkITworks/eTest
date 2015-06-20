/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class TQItems extends TQCases {
    
    private int tqItemId;
    private Map<Integer, Map<Integer, Integer>> cellCaseItemKey;

    public int getTqItemId() {
        return tqItemId;
    }

    public Map<Integer, Map<Integer, Integer>> getCellCaseItemKey() {
        return cellCaseItemKey;
    }
    
    public void setTqItemId(int tqItemId) {
        this.tqItemId = tqItemId;
    }

    public void setCellCaseItemKey(Map<Integer, Map<Integer, Integer>> cellCaseItemKey) {
        this.cellCaseItemKey = cellCaseItemKey;
    }
    
}
