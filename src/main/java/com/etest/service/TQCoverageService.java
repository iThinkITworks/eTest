/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.CellItem;
import com.etest.model.TQAnswerKey;
import com.etest.model.TQCoverage;
import com.etest.model.TQItems;
import com.etest.model.TopicCoverage;
import com.vaadin.data.Item;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public interface TQCoverageService {
    
    public double calculateTotalHourSpent(Grid grid);
    
    public void calculateProportion(Grid grid);
    
    public double calculateTotalProportion(Grid grid);
    
    public void calculateMaxItems(Grid grid, 
            TextField totalItems);
    
    public double calculateTotalMaxItems(Grid grid);
    
    public int getBloomsClassId(String bloomsClass);
    
    public int getTotalForBloomsClassColumn(Grid grid, 
            String propertyId); 
    
    public boolean isValueInTBNotZero(Item item, 
            String propertyId);
    
    public boolean isGreaterThanInTB(Item item, 
            String propertyIdInTB, 
            String propertyIdPick);
    
    public int calculateTotalPickItems(Grid grid, 
            String propertyId);
    
    public int calculateTotalPickItemsPerTopic(Grid grid, 
            Object itemId);
    
    public void revertAllInputItemsToZero(Grid grid, 
            Object itemId);
    
    public int calculateRunningTotal(Grid grid);
    
    public boolean isMaxItemsCompareToInputItems(double maxItems, 
            int inputItems);
    
    public boolean isValueOfRunningTotal(Item item, 
            String propertyId);
    
    public boolean isRunningTotalGreaterThanMaxItemsTotal(int runningTotal, 
            double maxItemsTotal);
    
    public List<CellItem> getItemIdByBloomClassId(Grid grid);
    
    public boolean insertNewTQCoverage(TopicCoverage coverage, 
            TQItems items, 
            Map<Integer, List<TQAnswerKey>> cellCaseItemKey, 
            Grid grid);
    
    public List<TQCoverage> getAllTQCoverage();
    
    public Map<Integer, Map<Integer, Integer>> getTQCoverage(int tqCoverageId);
    
    public boolean approveTQCoverage(int tqCoverageId);
    
    public boolean isTQCoverageApproved(int tqCoverageId);
    
    public boolean deleteTQCoverage(int tqCoverageId);
    
    public List<TQAnswerKey> getTQCoverageAnswerKey(int tqCoverageId);

    public List<Integer> getCellItemIdByTQCoverageId(int tqCoverageId);
    
    public String getAnswerByCellItemId(int tqCoverageId, 
            int cellItemId);
    
    public TQCoverage getTQCoverageById(int tqCoverageId);
    
    public boolean saveItemAnalysis(Grid grid, 
            int tqCoverageId);
}
