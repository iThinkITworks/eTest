/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public ProportionDataTable(Map<String, List<Character>> studentNoAndAnswer, 
            List<String> upperGroupStudentNo, 
            List<String> lowerGroupStudentNo, 
            List<Integer> itemIds) {
        this.studentNoAndAnswer = studentNoAndAnswer;
        this.upperGroupStudentNo = upperGroupStudentNo;
        this.lowerGroupStudentNo = lowerGroupStudentNo;
        this.itemIds = itemIds;
        
        setCaption("PROPORTION");
        setHeight("100%");
        setModal(true);
        center();
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setSpacing(true);
        
        upperGroupTable.addContainerProperty("Item No.", Integer.class, null);
        v.addComponent(upperGroupTable);
        
        lowerGroupTable.addContainerProperty("Item No.", Integer.class, null);
        v.addComponent(lowerGroupTable);
        
        upperGroupTable.setWidthUndefined();
        lowerGroupTable.setWidthUndefined();
        
        getUpperGroupStudentTable();
        getLowerGroupStudentTable();
        
        setContent(v);
        getContent().setWidthUndefined();
    }
    
    void getUpperGroupStudentTable(){
        for(Object obj : getUpperGroupStudentNo()){
            upperGroupTable.addContainerProperty(obj.toString(), String.class, null);
        }
    }
    
    void getLowerGroupStudentTable(){
        for(Object obj : getLowerGroupStundetNo()){
            lowerGroupTable.addContainerProperty(obj.toString(), String.class, null);
        }
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
}
