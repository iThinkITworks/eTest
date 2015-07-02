/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class ProportionDataTable extends Window {

    Table upperGroupTable = new Table();
    Table lowerGroupTable = new Table();    
    
    Map<String, List<Character>> studentNoAndAnswer = new HashMap<>();
    List<String> upperGroupStudentNo = new ArrayList<>();
    List<String> lowerGroupStudentNo = new ArrayList<>();
    List<Integer> itemIds = new ArrayList<>();
    private int tqCoverageId;
    private double groupTotalForProportion;
    
    public ProportionDataTable(Map<String, List<Character>> studentNoAndAnswer, 
            List<String> upperGroupStudentNo, 
            List<String> lowerGroupStudentNo, 
            List<Integer> itemIds, 
            int tqCoverageId, 
            double groupTotalForProportion) {
        this.studentNoAndAnswer = studentNoAndAnswer;
        this.upperGroupStudentNo = upperGroupStudentNo;
        this.lowerGroupStudentNo = lowerGroupStudentNo;
        this.itemIds = itemIds;
        this.tqCoverageId = tqCoverageId; 
        this.groupTotalForProportion = groupTotalForProportion;
        
        setCaption("PROPORTION");
        setHeight("100%");
        setModal(true);
        center();
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setSpacing(true);
        v.setMargin(true);
        
        upperGroupTable.addContainerProperty("Item No.", Integer.class, null);
        upperGroupTable.setSelectable(true);
        upperGroupTable.setWidthUndefined();
        v.addComponent(upperGroupStudentPanel());
        
        lowerGroupTable.addContainerProperty("Item No.", Integer.class, null);
        lowerGroupTable.setSelectable(true);
        lowerGroupTable.setWidthUndefined();
        v.addComponent(lowerGroupStudentPanel());
        
        upperGroupTable.setWidthUndefined();
        lowerGroupTable.setWidthUndefined();
        
        getUpperGroupStudentTable();
        getLowerGroupStudentTable();
        
        setContent(v);
        getContent().setWidthUndefined();
    }
    
    Panel upperGroupStudentPanel(){
        Panel panel = new Panel("UPPER GROUP");
        panel.setWidth("100%");
        panel.setContent(upperGroupTable);
        
        return panel;
    }
    
    Panel lowerGroupStudentPanel(){
        Panel panel = new Panel("LOWER GROUP");
        panel.setWidth("100%");
        panel.setContent(lowerGroupTable);
        
        return panel;
    }
    
    void getUpperGroupStudentTable(){
        for(Object obj : getUpperGroupStudentNo()){
            upperGroupTable.addContainerProperty(obj.toString(), Integer.class, null);
        }
        upperGroupTable.addContainerProperty("Proportion", Double.class, null);
        
        int itemNo = 1;
        Object itemId;
        int index = 0;
        for(Integer i : getItemIds()){
            Collection c = upperGroupTable.getContainerPropertyIds();
            Iterator iterator = c.iterator();
            itemId = upperGroupTable.addItem();
            Item item = upperGroupTable.getItem(itemId);
            String header;
            double rowTotal = 0;
            while(iterator.hasNext()){       
                header = iterator.next().toString();
                if(header.equals("Item No.")){
                    item.getItemProperty(header).setValue(itemNo);
                } else if (header.equals("Proportion")) { 
                } else {
                    char ch = getStudentNoAndAnswer().get(header).get(index);
                    item.getItemProperty(header).setValue((ch == ItemAnalysisInterpretation.convertValueToOneOrZero(tqCoverageId, i) ? 1 : 0));
                    rowTotal = rowTotal + (ch == ItemAnalysisInterpretation.convertValueToOneOrZero(tqCoverageId, i) ? 1 : 0);
                }
            }  
            item.getItemProperty("Proportion").setValue(CommonUtilities.roundOffToTwoDecimal(rowTotal/getGroupTotalForProportion()));
            itemNo++;
            index++;
        }     
        
        upperGroupTable.setPageLength(upperGroupTable.size());
    }
    
    void getLowerGroupStudentTable(){
        for(Object obj : getLowerGroupStundetNo()){
            lowerGroupTable.addContainerProperty(obj.toString(), Integer.class, null);
        }
        lowerGroupTable.addContainerProperty("Proportion", Double.class, null);
        
        int itemNo = 1;
        Object itemId;
        int index = 0;
        for(Integer i : getItemIds()){
            Collection c = lowerGroupTable.getContainerPropertyIds();
            Iterator iterator = c.iterator();
            itemId = lowerGroupTable.addItem();
            Item item = lowerGroupTable.getItem(itemId);
            String header;
            double rowTotal = 0;
            while(iterator.hasNext()){       
                header = iterator.next().toString();
                if(header.equals("Item No.")){
                    item.getItemProperty(header).setValue(itemNo);
                } else if (header.equals("Proportion")) { 
                } else {
                    char ch = getStudentNoAndAnswer().get(header).get(index);
                    item.getItemProperty(header).setValue((ch == ItemAnalysisInterpretation.convertValueToOneOrZero(tqCoverageId, i) ? 1 : 0));
                    rowTotal = rowTotal + (ch == ItemAnalysisInterpretation.convertValueToOneOrZero(tqCoverageId, i) ? 1 : 0);
                }
            }  
            item.getItemProperty("Proportion").setValue(CommonUtilities.roundOffToTwoDecimal(rowTotal/getGroupTotalForProportion()));
            itemNo++;
            index++;
        }
        lowerGroupTable.setPageLength(lowerGroupTable.size());
    }
        
    Map<String, List<Character>> getStudentNoAndAnswer(){
        return studentNoAndAnswer;
    }
    
    List<String> getUpperGroupStudentNo(){
        return upperGroupStudentNo;
    }    
    
    List<String> getLowerGroupStundetNo(){
        return lowerGroupStudentNo;
    }
    
    List<Integer> getItemIds(){
        return itemIds;
    }
    
    int getTQCoverageId(){
        return tqCoverageId;
    }
    
    double getGroupTotalForProportion(){
        return groupTotalForProportion;
    }
}
