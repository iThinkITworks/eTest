/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.reports;

import com.etest.common.CommonCascadeComboBox;
import com.etest.common.CommonComboBox;
import com.etest.pdfgenerator.TQViewer;
import com.etest.service.CellItemService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.view.tq.itemanalysis.ItemAnalysisViewResultWindow;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class ReportGeneratorUI extends VerticalLayout {

    TQCoverageService tq = new TQCoverageServiceImpl();
    
    OptionGroup reportType1 = new OptionGroup();
    ComboBox searchSubject1 = CommonComboBox.getSubjectFromCurriculum("Search Subject");
    ComboBox searchApproveTq1 = new ComboBox();
    
    OptionGroup reportType2 = new OptionGroup();
    ComboBox searchSubject2 = CommonComboBox.getSubjectFromCurriculum("Search Subject");
    ComboBox searchApproveTq2 = new ComboBox();
    
    OptionGroup reportType3 = new OptionGroup();
    CheckBox summaryCaseItems = new CheckBox();
    CheckBox summaryItemGroup = new CheckBox();
    
    boolean report1 = false;
    boolean report2 = false;
    boolean report3 = false;
    
    public ReportGeneratorUI() {
        setWidth("100%");
        setSpacing(true);
//        setMargin(true);
                
        searchSubject1.addValueChangeListener(dropDownChangeListener);
        searchSubject1.setEnabled(false);
        searchApproveTq1.setWidth("100%");
        searchApproveTq1.setInputPrompt("Search Approved TQ");
        searchApproveTq1.setEnabled(false);
        searchApproveTq1.addStyleName(ValoTheme.COMBOBOX_SMALL);
        
        searchSubject2.addValueChangeListener(dropDownChangeListener);
        searchSubject2.setEnabled(false);
        searchApproveTq2.setWidth("100%");
        searchApproveTq2.setInputPrompt("Search TQ Ticket No.");
        searchApproveTq2.setEnabled(false);
        searchApproveTq2.addStyleName(ValoTheme.COMBOBOX_SMALL);
        
        GridLayout g1 = new GridLayout(3, 1);
        g1.setWidth("70%");
        g1.setSpacing(true);
        
        VerticalLayout v1 = new VerticalLayout();
        v1.setWidth("5px");
        
        reportType1.addItem("Test Questionnaire");
        reportType1.setWidth("200px");
        reportType1.addValueChangeListener(optionChangeListener);
        reportType1.setImmediate(true);
        v1.addComponent(reportType1);
        v1.setExpandRatio(reportType1, 1);
        g1.addComponent(v1, 0, 0);        
        g1.addComponent(searchSubject1, 1, 0);
        g1.addComponent(searchApproveTq1, 2, 0);
        
        addComponent(g1);
        
        addComponent(new Label("<HR>", ContentMode.HTML));
        
        GridLayout g2 = new GridLayout(3, 1);
        g2.setWidth("70%");
        g2.setSpacing(true);
        
        VerticalLayout v2 = new VerticalLayout();
        v2.setWidth("5px");
        
        reportType2.addItem("Item Analysis");
        reportType2.setWidth("200px");
        reportType2.addValueChangeListener(optionChangeListener);
        reportType2.setImmediate(true);
        v2.addComponent(reportType2);
        v2.setExpandRatio(reportType2, 1);
        g2.addComponent(v2, 0, 0);
        g2.addComponent(searchSubject2, 1, 0);
        g2.addComponent(searchApproveTq2, 2, 0);
        
        addComponent(g2);
        
        addComponent(new Label("<HR>", ContentMode.HTML));
        
        GridLayout g3 = new GridLayout(3, 1);
        g3.setWidth("70%");
        g3.setSpacing(true);
                
        VerticalLayout v3 = new VerticalLayout();
        v3.setWidth("5px");
        
        reportType3.addItem("Test Bank Inventory");
        reportType3.setWidth("200px");
        reportType3.addValueChangeListener(optionChangeListener);
        reportType3.setImmediate(true);
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setSpacing(true);
        
        summaryCaseItems.setCaption("Summary: Case vs Items");
        summaryCaseItems.setEnabled(false);
        summaryCaseItems.setImmediate(true);
        v.addComponent(summaryCaseItems);
        
        summaryItemGroup.setCaption("Summary: Items Group according to the Revised Blooms Taxonomy");
        summaryItemGroup.setEnabled(false);
        summaryItemGroup.setImmediate(true);
        v.addComponent(summaryItemGroup);
        
        v3.addComponent(reportType3);
        v3.setExpandRatio(reportType3, 1);
        g3.addComponent(v3, 0, 0);
        g3.setComponentAlignment(v3, Alignment.TOP_LEFT);
        g3.addComponent(v, 1, 0);
        
        
        addComponent(g3);
        
        addComponent(new Label("<HR>", ContentMode.HTML));
        
        Button button = new Button("Calculate & Generate");
        button.addClickListener(reportBtnClickListener);
        button.setWidth("300px");
        
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_LEFT);
    }
    
    ValueChangeListener optionChangeListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){            
        } else if (event.getProperty().getValue().equals("Test Questionnaire")){
            reportType2.clear();
            reportType3.clear();
            enableTestQuestionnaire(true);
        } else if (event.getProperty().getValue().equals("Item Analysis")) {
            reportType1.clear();
            reportType3.clear();
            enableItemAnalysis(true);
        } else {
            reportType1.clear();
            reportType2.clear();
            enableTestBankInventory(true);
        }
    };    
    
    ValueChangeListener dropDownChangeListener = (ValueChangeEvent event) -> {
        if(event.getProperty().getValue() == null){
        } else {
            if(searchSubject1.isEnabled()){
                CommonCascadeComboBox.getApprovedTqFromCurriculum(searchApproveTq1, (int) event.getProperty().getValue());
            } else {
                CommonCascadeComboBox.getTqTicketNoFromCurriculum(searchApproveTq2, (int) event.getProperty().getValue());
            }            
        }
    };
    
    Button.ClickListener reportBtnClickListener = (Button.ClickEvent event) -> {
        if(report1){
            if(searchSubject1.getValue() == null || searchApproveTq1.getValue() == null){
                Notification.show("Fill up all Fields!", Notification.Type.WARNING_MESSAGE);
                return;
            }              
            
            Window pdf = new TQViewer((int) searchApproveTq1.getValue());
            if(pdf.getParent() == null){
                UI.getCurrent().addWindow(pdf);
            }
        } 
        
        if (report2){
            if(searchSubject2.getValue() == null || searchApproveTq2.getValue() == null){
                Notification.show("Fill up all Fields!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            if(!tq.isTQCoverageAnalyzed((int) searchApproveTq2.getValue())){
                Notification.show("TQ was not yet analyzed", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            Window sub = new ItemAnalysisViewResultWindow((int) searchApproveTq2.getValue());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        } 
        
        if (report3) {
            
        }
    };
    
    private void enableTestQuestionnaire(boolean bol){
        searchSubject1.setEnabled(bol);
        searchApproveTq1.setEnabled(bol);

        searchSubject2.setEnabled(!bol);
        searchApproveTq2.setEnabled(!bol);

        summaryCaseItems.setEnabled(!bol);
        summaryItemGroup.setEnabled(!bol);
        
        report1 = true;
        report2 = false;
        report3 = false;
    }
    
    private void enableItemAnalysis(boolean bol){
        searchSubject1.setEnabled(!bol);
        searchApproveTq1.setEnabled(!bol);

        searchSubject2.setEnabled(bol);
        searchApproveTq2.setEnabled(bol);

        summaryCaseItems.setEnabled(!bol);
        summaryItemGroup.setEnabled(!bol);
        
        report1 = false;
        report2 = true;
        report3 = false;
    }
    
    private void enableTestBankInventory(boolean bol){
        searchSubject1.setEnabled(!bol);
        searchApproveTq1.setEnabled(!bol);

        searchSubject2.setEnabled(!bol);
        searchApproveTq2.setEnabled(!bol);

        summaryCaseItems.setEnabled(bol);
        summaryItemGroup.setEnabled(bol);
        
        report1 = false;
        report2 = false;
        report3 = true;
    }
    
    boolean getReport1Enabled(){
        return report1;
    }
    
    boolean getReport2Enabled(){
        return report2;
    }
    
    boolean getReport3Enabled(){
        return report3;
    }
}
