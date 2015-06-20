/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jetdario
 */
public class TQCases extends TQCoverage {
    
    private int tqCaseId;
    private ArrayList<Integer> cellCaseId;

    public int getTqCaseId() {
        return tqCaseId;
    }

    public ArrayList<Integer> getCellCaseId() {
        return cellCaseId;
    }

    public void setTqCaseId(int tqCaseId) {
        this.tqCaseId = tqCaseId;
    }

    public void setCellCaseId(ArrayList<Integer> cellCaseId) {
        this.cellCaseId = cellCaseId;
    }

}
