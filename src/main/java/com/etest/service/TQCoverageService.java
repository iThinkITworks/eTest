/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.common.BloomsClassTaxonomy;
import com.etest.common.BloomsClassTaxonomy.BloomsClass;
import com.vaadin.data.Item;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

/**
 *
 * @author jetdario
 */
public interface TQCoverageService {
    
    public double calculateTotalHourSpent(Grid grid);
    
    public void calculateProportion(Grid grid);
    
    public double calculateTotalProportion(Grid grid);
    
    public void calculateMaxItems(Grid grid, TextField totalItems);
    
    public double calculateTotalMaxItems(Grid grid);
    
    public int getBloomsClassId(String bloomsClass);
    
    public int getTotalForBloomsClassColumn(Grid grid, String propertyId); 
    
    public boolean isValueInTBNotZero(Item item, String propertyId);
    
    public boolean isGreaterThanInTB(Item item, String propertyIdInTB, String propertyIdPick);
    
    public int calculateTotalPickItems(Grid grid, String propertyId);
    
    public int calculateTotalPickItemsPerTopic(Grid grid, Object itemId);
    
    public void revertAllInputItemsToZero(Grid grid, Object itemId);
    
    public int calculateRunningTotal(Grid grid);
}