/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

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
}
