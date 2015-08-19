/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.reports;

import com.etest.common.CommonCascadeComboBox;
import com.etest.common.CommonComboBox;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
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
    
    public OnlineQueriesUI() {
        setWidth("100%");
        setSpacing(true);
                
        addComponent(buildForms());        
    }
    
    FormLayout buildForms(){
        FormLayout f = new FormLayout();
        f.setMargin(true);
        f.setSpacing(true);
        f.addStyleName("light");
        
        GridLayout topGrid = new GridLayout(3, 4);
        topGrid.setWidth("70%");
        topGrid.setSpacing(true);
        
        VerticalLayout top = new VerticalLayout();
        top.setWidth("150px");
        
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
//        graphicalInventoryGroup.addValueChangeListener(secondLevelTopOptionListener);
        graphicalInventoryGroup.setImmediate(true);

        searchSubject1.addValueChangeListener(dropDownChangeListener);
        searchSubject1.setEnabled(false);
        searchTest1.setWidth("100%");
        searchTest1.setInputPrompt("Search Approved TQ");
        searchTest1.setEnabled(false);
        searchTest1.addStyleName(ValoTheme.COMBOBOX_SMALL);
        
        topGrid.addComponent(top, 0, 0);
        topGrid.addComponent(graphicalInventoryGroup, 1, 0, 2, 0);
        topGrid.addComponent(searchSubject1, 1, 2);
        topGrid.addComponent(searchTest1, 2, 2);
        
        f.addComponent(topGrid);
        
        GridLayout bottomGrid = new GridLayout(3, 4);
        bottomGrid.setWidth("70%");
        bottomGrid.setSpacing(true);
        
        VerticalLayout bottom = new VerticalLayout();
        bottom.setWidth("192px");
        
        itemAnalysis.addItem("Item Analysis");
        itemAnalysis.setWidth("300px");
        bottom.addComponent(itemAnalysis);
        itemAnalysis.addValueChangeListener(firstLevelOptionListener);
        itemAnalysis.setImmediate(true);
        
        graphicalView.addItem("Graphical View");
        graphicalView.setWidth("240px");
        graphicalView.addValueChangeListener(secondLevelBottomOptionListener);
        graphicalView.setImmediate(true);
        
        tabularView.addItem("Tabular View");
        tabularView.addValueChangeListener(secondLevelBottomOptionListener);
        tabularView.setImmediate(true);
        
        graphicalViewGroup.addItem("Summary: All Tests of a Subject");
        graphicalViewGroup.addItem("Difficulty Index of a Subject's Test");
        graphicalViewGroup.addItem("Discrimination Index of a Subject's Test");
        graphicalViewGroup.setWidth("300px");
        graphicalViewGroup.setImmediate(true);
        
        tabularViewGroup.addItem("Summary: All Tests of a Subject");
        tabularViewGroup.addItem("Critical values of a test");
        tabularViewGroup.setImmediate(true);
        
        searchSubject2.addValueChangeListener(dropDownChangeListener);
        searchSubject2.setEnabled(false);
        searchTest2.setWidth("100%");
        searchTest2.setInputPrompt("Search Approved TQ");
        searchTest2.setEnabled(false);
        searchTest2.addStyleName(ValoTheme.COMBOBOX_SMALL);
        
        bottomGrid.addComponent(bottom, 0, 0);
        bottomGrid.addComponent(graphicalView, 1, 0);
        bottomGrid.addComponent(tabularView, 1, 1);
        bottomGrid.addComponent(graphicalViewGroup, 2, 0);
        bottomGrid.addComponent(tabularViewGroup, 2, 1);
        bottomGrid.addComponent(searchSubject2, 1, 2);
        bottomGrid.addComponent(searchTest2, 2, 2);
        f.addComponent(bottomGrid);
        
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        h.setMargin(true);
        
        Button button = new Button("Calculate & View");
        button.setWidth("300px");
        h.addComponent(button);
        h.setComponentAlignment(button, Alignment.MIDDLE_LEFT);
        f.addComponent(h);
        
        return f;
    }
    
    ValueChangeListener firstLevelOptionListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){            
        } else if (event.getProperty().getValue().equals("Graphical Inventory")){
            itemAnalysis.clear();
            graphicalView.clear();
            tabularView.clear();
            graphicalViewGroup.clear();
            tabularViewGroup.clear();
            enableTopOptionGroup(true);
        } else {
            graphicalInventory.clear();
            graphicalInventoryGroup.clear();
            enableBottomOptionGroup(true);
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
    
    ValueChangeListener dropDownChangeListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){
        } else {
            if(searchSubject1.isEnabled()){
                CommonCascadeComboBox.getApprovedTqFromCurriculum(searchTest1, (int) event.getProperty().getValue());
            }            
        }
    };
    
    void enableTopOptionGroup(boolean bol){
        graphicalInventoryGroup.setEnabled(bol);
        searchSubject1.setEnabled(bol);
        searchTest1.setEnabled(bol);
        
        graphicalView.setEnabled(!bol);
        tabularView.setEnabled(!bol);
        graphicalViewGroup.setEnabled(!bol);
        tabularViewGroup.setEnabled(!bol);
        searchSubject2.setEnabled(!bol);
        searchTest2.setEnabled(!bol);
    }
    
    void enableBottomOptionGroup(boolean bol){
        graphicalInventoryGroup.setEnabled(!bol);
        searchSubject1.setEnabled(!bol);
        searchTest1.setEnabled(!bol);
        
        graphicalView.setEnabled(bol);
        tabularView.setEnabled(bol);
        graphicalViewGroup.setEnabled(bol);
        tabularViewGroup.setEnabled(bol);
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
}
