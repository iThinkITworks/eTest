/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class ItemAnalysisDataGridProperties extends Grid {

    TQCoverageService tq = new TQCoverageServiceImpl();
    private int tqCoverageId;
    List<String> upperGroupStudentNo;
    List<String> lowerGroupStudentNo;
    List<Integer> itemIds;
    Map<String, List<Character>> studentNoAndAnswer;
    double groupTotalForProportion;
    
    public ItemAnalysisDataGridProperties(int tqCoverageId, 
            List<String> upperGroupStudentNo, 
            List<String> lowerGroupStudentNo, 
            List<Integer> itemIds, 
            Map<String, List<Character>> studentNoAndAnswer, 
            double groupTotalForProportion) { 
        this.tqCoverageId = tqCoverageId;
        this.upperGroupStudentNo = upperGroupStudentNo;
        this.lowerGroupStudentNo = lowerGroupStudentNo;
        this.studentNoAndAnswer = studentNoAndAnswer;
        this.groupTotalForProportion = groupTotalForProportion;
        this.itemIds = itemIds;
        
        setWidth("100%");
        setSelectionMode(Grid.SelectionMode.NONE);
        setImmediate(true);
        setFooterVisible(true);
        
        addColumn("item no.", Integer.class);
        addColumn("difficulty index", Double.class);
        addColumn("difficulty interpretation", String.class);
        addColumn("discrimination index", Double.class);
        addColumn("discrimination interpretation", String.class);
        addColumn("itemId", Integer.class);
        
        HeaderRow mainHeader = getDefaultHeaderRow();
        mainHeader.getCell("difficulty index").setText("Difficulty");
        mainHeader.getCell("difficulty interpretation").setText("Interpretation");
        mainHeader.getCell("discrimination index").setText("Discrimination");
        mainHeader.getCell("discrimination interpretation").setText("Interpretation");
        mainHeader.getCell("itemId").setText("Item ID");
        
        int itemNo = 1;
        for(Integer cellItemId : itemIds){
            addRow(itemNo, null, null, null, null, cellItemId);
            itemNo++;
        }
        
        List<Double> upperProportion = new ArrayList<>();
        List<Double> lowerProportion = new ArrayList<>();
                 
        int upper = 0;        
        for(Integer i : tq.getCellItemIdByTQCoverageId(tqCoverageId)){ 
            double rowTotal = 0;
            for(int j = 0; j < getUpperGroupStudentNo().size(); j++){
                List<Character> c = getStudentNoAndAnswer().get(getUpperGroupStudentNo().get(j));
                rowTotal = rowTotal + (c.get(upper).equals(ItemAnalysisInterpretation.convertValueToOneOrZero(tqCoverageId, i)) ? 1 : 0);                 
            }
            upperProportion.add(rowTotal/getGroupTotalForProportion());
            upper++;
        }
        
        int lower = 0;        
        for(Integer i : tq.getCellItemIdByTQCoverageId(tqCoverageId)){ 
            double rowTotal = 0;
            for(int j = 0; j < getLowerGroupStudentNo().size(); j++){
                List<Character> c = getStudentNoAndAnswer().get(getLowerGroupStudentNo().get(j));
                rowTotal = rowTotal + (c.get(lower).equals(ItemAnalysisInterpretation.convertValueToOneOrZero(tqCoverageId, i)) ? 1 : 0);                 
            }
            lowerProportion.add(rowTotal/getGroupTotalForProportion());
            lower++;
        }
        
        Collection c = getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            int itemId = (int) iterator.next();
            Item item = getContainerDataSource().getItem(itemId);
            item.getItemProperty("difficulty index").setValue(
                    CommonUtilities.roundOffToTwoDecimal(
                            calculateDifficultyIndex(
                                    upperProportion.get(itemId-1), 
                                    lowerProportion.get(itemId-1))));
            item.getItemProperty("difficulty interpretation").setValue(
                    ItemAnalysisInterpretation.getDifficultyInterpretation(
                            CommonUtilities.roundOffToTwoDecimal(
                                    calculateDifficultyIndex(
                                            upperProportion.get(itemId-1), 
                                            lowerProportion.get(itemId-1)))));
            item.getItemProperty("discrimination index").setValue(
                    CommonUtilities.roundOffToTwoDecimal(
                            calculateDisriminationIndex(
                                    upperProportion.get(itemId-1), 
                                    lowerProportion.get(itemId-1))));
            item.getItemProperty("discrimination interpretation").setValue(
                    ItemAnalysisInterpretation.getDiscriminationInterpretation(
                            CommonUtilities.roundOffToTwoDecimal(
                                    calculateDisriminationIndex(
                                            upperProportion.get(itemId-1), 
                                            lowerProportion.get(itemId-1)))));
        }
        
        removeColumn("itemId");
    }
    
    int getTqCoverageId(){
        return tqCoverageId;
    }
    
    List<String> getUpperGroupStudentNo(){
        return upperGroupStudentNo;
    }
    
    List<String> getLowerGroupStudentNo(){
        return lowerGroupStudentNo;
    }
    
    List<Integer> getItemIds(){
        return itemIds;
    }
    
    Map<String, List<Character>> getStudentNoAndAnswer(){
        return studentNoAndAnswer;
    }
    
    double getGroupTotalForProportion(){
        return groupTotalForProportion;
    }
    
    public double calculateDifficultyIndex(double upperProportion, double lowerProportion){
        return (upperProportion + lowerProportion) / 2;
    }
    
    public double calculateDisriminationIndex(double upperProportion, double lowerProportion){
        return (upperProportion - lowerProportion) < 0 ? 0.00 : (upperProportion - lowerProportion);
    }    
}
