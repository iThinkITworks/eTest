/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.reports;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class ReportMainUI extends VerticalLayout {
    
    public ReportMainUI() {
        setSizeFull();
        setSpacing(true);
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setHeight("100%");
        
        GridLayout g = new GridLayout(2, 1);
        g.setSpacing(true);
        
        ComboBox reportList = new ComboBox();
        reportList.setWidth("250px");
        reportList.setNullSelectionAllowed(false);
        reportList.addItem("Test Questionnaire");
        reportList.addItem("Inventory of Cases");
        reportList.addItem("Inventory of Items");
        reportList.setImmediate(true);
        reportList.addStyleName(ValoTheme.COMBOBOX_SMALL);
        g.addComponent(reportList, 0, 0);
        
        Button button = new Button("View Report");
        button.setWidth("250px");
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener((Button.ClickEvent event) -> {
            //TODO
            if(reportList.getValue().equals("Test Questionnaire")){
                v.removeAllComponents();
            } else  if (reportList.getValue().equals("Inventory of Cases")){
                v.removeAllComponents();
                v.setHeight("500px");
                v.addComponent(new ReportViewer(reportList.getValue().toString()));
            } else {
                v.removeAllComponents();
                v.setHeight("500px");
                v.addComponent(new ReportViewer(reportList.getValue().toString()));
            }
        });
        g.addComponent(button, 1, 0);
        
        addComponent(g);    
        addComponent(v);
        addComponent(buildReportComponent());
    }    
    
    Component buildReportComponent(){
        TabSheet t = new TabSheet();
        t.setWidth("100%");
        
        VerticalLayout v = new VerticalLayout();
        v.setCaption("Online Queries");
        v.setWidth("100%");
        v.setMargin(true);
        t.addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Report Generator");
        v.setWidth("100%");
        v.setMargin(true);
        v.addComponent(new ReportGeneratorUI());
        t.addComponent(v);        
        
        return t;
    }
}
