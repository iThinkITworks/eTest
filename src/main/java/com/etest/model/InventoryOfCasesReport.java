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
public class InventoryOfCasesReport extends Syllabus {
    
    private int numberOfCases;
    private int numberOfItems;

    public int getNumberOfCases() {
        return numberOfCases;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfCases(int numberOfCases) {
        this.numberOfCases = numberOfCases;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
    
}
