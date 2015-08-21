/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.reports;

import com.etest.common.CommonCascadeComboBox;
import com.etest.common.CommonComboBox;
import com.etest.view.tq.charts.GraphicalInventoryChart;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class OnlineQueriesUI extends VerticalLayout {

    OptionGroup graphicalInventory = new OptionGroup();  
    OptionGroup graphicalInventoryGroup= new OptionGroup();   
    
    OptionGroup itemAnalysis = new OptionGroup();    
    OptionGroup graphicalView = new OptionGroup();
    OptionGroup tabularView = new OptionGroup();
    OptionGroup graphicalViewGroup = new OptionGroup();
    OptionGroup tabularViewGroup = new OptionGroup();
    
    ComboBox searchSubject1 = CommonComboBox.getSubjectFromCurriculum("Search Subject");
    ComboBox searchTest1 = new ComboBox();
    
    ComboBox searchSubject2 = CommonComboBox.getSubjectFromCurriculum("Search Subject");
    ComboBox searchTest2 = new ComboBox();
    
    boolean graphicalInventoryReport = false;
    boolean itemAnalysisReport = false;
    
    public OnlineQueriesUI() {
        setWidth("100%");
        setSpacing(true);
                
        Label lineSeparator1 = new Label();
        lineSeparator1.setContentMode(ContentMode.HTML);
        lineSeparator1.setStyleName("line-separator");
        
        Label lineSeparator2 = new Label();
        lineSeparator2.setContentMode(ContentMode.HTML);
        lineSeparator2.setStyleName("line-separator");
        
        disableAllComponents(false);
        
        GridLayout topGrid = new GridLayout(3, 1);
        topGrid.setWidth("800px");
        topGrid.setSpacing(true);
        
        VerticalLayout top = new VerticalLayout();
        top.setWidth("166px");
        
        graphicalInventory.addItem("Graphical Inventory");
        graphicalInventory.setWidth("300px");
        top.addComponent(graphicalInventory);     
        graphicalInventory.addValueChangeListener(firstLevelOptionListener);
        graphicalInventory.setImmediate(true);
        
        graphicalInventoryGroup.addItem("All Subjects");
        
        Label subjectProportionedCaption = new Label();
        subjectProportionedCaption.setWidth("500px");
        subjectProportionedCaption.setCaption("A Subject's No. of Items Proportioned "
                + "According to the Revised Bloom's Taxonomy");
        subjectProportionedCaption.setContentMode(ContentMode.HTML);
        subjectProportionedCaption.setHeightUndefined();
        
        graphicalInventoryGroup.addItem(subjectProportionedCaption.getCaption());
        graphicalInventoryGroup.setWidth("400px");
        graphicalInventoryGroup.addValueChangeListener(secondLevelTopOptionListener);
        graphicalInventoryGroup.setImmediate(true);
        
        topGrid.addComponent(top, 0, 0);
        topGrid.addComponent(graphicalInventoryGroup, 1, 0, 2, 0);        
        addComponent(topGrid);
        
        GridLayout midGrid = new GridLayout(3, 1);
        midGrid.setWidth("720px");
        midGrid.setSpacing(true);
        
        Label label = new Label();
        label.setWidth("235px");
        midGrid.addComponent(label, 0, 0);
        
        searchSubject1.setInputPrompt("Search Subject");
        searchSubject1.setWidth("225px");
        searchSubject1.addValueChangeListener(dropDownChangeListener);
        searchSubject1.setEnabled(false);
        searchTest1.setWidth("225px");
        searchTest1.setInputPrompt("Search Test");
        searchTest1.setEnabled(false);
        searchTest1.addStyleName(ValoTheme.COMBOBOX_SMALL);
        midGrid.addComponent(searchSubject1, 1, 0);
        midGrid.addComponent(searchTest1, 2, 0);
        addComponent(midGrid);
        
        addComponent(lineSeparator1);
        
        GridLayout bottomGrid = new GridLayout(3, 3);
        bottomGrid.setWidth("800px");
        bottomGrid.setSpacing(true);
        
        VerticalLayout bottom = new VerticalLayout();
        bottom.setWidth("235px");
        
        itemAnalysis.addItem("Item Analysis");
        itemAnalysis.setWidth("310px");
        bottom.addComponent(itemAnalysis);
        itemAnalysis.addValueChangeListener(firstLevelOptionListener);
        itemAnalysis.setImmediate(true);
        
        graphicalView.addItem("Graphical View");
        graphicalView.setWidth("210px");
        graphicalView.addValueChangeListener(secondLevelBottomOptionListener);
        graphicalView.setImmediate(true);
        
        tabularView.addItem("Tabular View");
        tabularView.addValueChangeListener(secondLevelBottomOptionListener);
        tabularView.setImmediate(true);
        
        graphicalViewGroup.addItem("Summary: All Tests of a Subject");
        graphicalViewGroup.addItem("Difficulty Index of a Subject's Test");
        graphicalViewGroup.addItem("Discrimination Index of a Subject's Test");
        graphicalViewGroup.setWidth("300px");
        graphicalViewGroup.addValueChangeListener(thirdLevelBottomOptionListener);
        graphicalViewGroup.setImmediate(true);
        
        tabularViewGroup.addItem("Summary: All Tests of a Subject");
        tabularViewGroup.addItem("Critical values of a test");
        tabularViewGroup.addValueChangeListener(thirdLevelBottomOptionListener);
        tabularViewGroup.setImmediate(true);
        
        searchSubject2.setInputPrompt("Search Subject");
        searchSubject2.setWidth("225px");
        searchSubject2.addValueChangeListener(dropDownChangeListener);
        searchSubject2.setEnabled(false);
        searchTest2.setWidth("225px");
        searchTest2.setInputPrompt("Search Test");
        searchTest2.setEnabled(false);
        searchTest2.addStyleName(ValoTheme.COMBOBOX_SMALL);
        
        bottomGrid.addComponent(bottom, 0, 0);
        bottomGrid.addComponent(graphicalView, 1, 0);
        bottomGrid.addComponent(tabularView, 1, 1);
        bottomGrid.addComponent(graphicalViewGroup, 2, 0);
        bottomGrid.addComponent(tabularViewGroup, 2, 1);
        bottomGrid.addComponent(searchSubject2, 1, 2);
        bottomGrid.addComponent(searchTest2, 2, 2);
        addComponent(bottomGrid);
        
        addComponent(lineSeparator2);
        
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        h.setMargin(true);
                
        Button calculateAndViewBtn = new Button("Calculate & View");
        calculateAndViewBtn.setWidth("300px");
        calculateAndViewBtn.addClickListener(buttonClickListener);
        h.addComponent(calculateAndViewBtn);
        h.setComponentAlignment(calculateAndViewBtn, Alignment.MIDDLE_LEFT);
        addComponent(calculateAndViewBtn);
          
    }
        
    ValueChangeListener firstLevelOptionListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){            
        } else if (event.getProperty().getValue().equals("Graphical Inventory")){
            itemAnalysis.clear();
            graphicalView.clear();
            tabularView.clear();
            graphicalViewGroup.clear();
            tabularViewGroup.clear();
            enableTopLevel2OptionGroup(true);
            enableBottomLevel3Component(false);
            graphicalInventoryReport = true;
            itemAnalysisReport = false;
        } else {
            graphicalInventory.clear();
            graphicalInventoryGroup.clear();
            enableBottomLevel2OptionGroup(true);
            enableTopLevel3Component(false);
            graphicalInventoryReport = false;
            itemAnalysisReport = true;
        }
    };
        
    ValueChangeListener secondLevelTopOptionListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){            
        } else if (event.getProperty().getValue().equals("All Subjects")) {
            enableTopLevel3Component(false);
        } else {
            enableTopLevel3Component(true);
        }
        
    };
    
    ValueChangeListener secondLevelBottomOptionListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){            
        } else if(event.getProperty().getValue().equals("Graphical View")){
            tabularView.clear();
            tabularViewGroup.clear();
            enableGraphicalViewGroup(true);
        } else {
            graphicalView.clear();
            graphicalViewGroup.clear();
            enableTabularViewGroup(true);
        }
    };
    
    ValueChangeListener thirdLevelBottomOptionListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){              
        } else if (event.getProperty().getValue().equals("Summary: All Tests of a Subject")) {
            searchTest2.setEnabled(false);
        } else {
            enableBottomLevel3Component(true);
        }
    };
    
    ValueChangeListener dropDownChangeListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){
        } else {
            if(searchSubject1.isEnabled()){
                CommonCascadeComboBox.getApprovedTqFromCurriculum(searchTest1, (int) event.getProperty().getValue());
            } 
            
            if (searchSubject2.isEnabled()){
                CommonCascadeComboBox.getApprovedTqFromCurriculum(searchTest2, (int) event.getProperty().getValue());
            }             
        }
    };
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(isGraphicalInventoryReport()){
            System.out.println("Graphical Inventory Report");
            Window sub = new GraphicalInventoryChart();
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        } 
        
        if(isItemAnalysisReport()){
            System.out.println("Item Analysis Report");
        }
    };
    
    void enableTopLevel2OptionGroup(boolean bol){
        graphicalInventoryGroup.setEnabled(bol);
        
        graphicalView.setEnabled(!bol);
        tabularView.setEnabled(!bol);
        graphicalViewGroup.setEnabled(!bol);
        tabularViewGroup.setEnabled(!bol);
    }
    
    void enableTopLevel3Component(boolean bol){
        searchSubject1.setEnabled(bol);
        searchTest1.setEnabled(bol);
    }
    
    void enableBottomLevel2OptionGroup(boolean bol){
        graphicalInventoryGroup.setEnabled(!bol);
        
        graphicalView.setEnabled(bol);
        tabularView.setEnabled(bol);
    }
        
    void enableBottomLevel3Component(boolean bol){
        searchSubject2.setEnabled(bol);
        searchTest2.setEnabled(bol);
    }
    
    void enableGraphicalViewGroup(boolean bol){
        graphicalViewGroup.setEnabled(bol);
        tabularViewGroup.setEnabled(!bol);
    }
    
    void enableTabularViewGroup(boolean bol){
        graphicalViewGroup.setEnabled(!bol);
        tabularViewGroup.setEnabled(bol);
    }
    
    void disableAllComponents(boolean bol){
        graphicalInventoryGroup.setEnabled(bol);
        graphicalView.setEnabled(bol);
        tabularView.setEnabled(bol);
        graphicalViewGroup.setEnabled(bol);
        tabularViewGroup.setEnabled(bol);
    }
    
    boolean isGraphicalInventoryReport(){
        return graphicalInventoryReport;
    }
    
    boolean isItemAnalysisReport(){
        return itemAnalysisReport;
    }
}
