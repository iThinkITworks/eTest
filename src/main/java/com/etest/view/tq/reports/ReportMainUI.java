/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.reports;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class ReportMainUI extends TabSheet implements TabSheet.SelectedTabChangeListener {
    
    public ReportMainUI() {
        setWidth("100%");
        addStyleName("bar");
        
        VerticalLayout v = new VerticalLayout();
        v.setCaption("Online Queries");
        v.setWidth("100%");
        v.setMargin(true);
        v.addComponent(new OnlineQueriesUI());
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Report Generator");
        v.setWidth("100%");
        v.setMargin(true);
        v.addComponent(new ReportGeneratorUI());
        addComponent(v);
    }    
    
//    Component buildReportComponent(){
//        TabSheet t = new TabSheet();
//        t.setWidth("100%");
//        
//        VerticalLayout v = new VerticalLayout();
//        v.setCaption("Online Queries");
//        v.setWidth("100%");
//        v.setMargin(true);
//        v.addComponent(new OnlineQueriesUI());
//        t.addComponent(v);
//        
//        v = new VerticalLayout();
//        v.setCaption("Report Generator");
//        v.setWidth("100%");
//        v.setMargin(true);
//        v.addComponent(new ReportGeneratorUI());
//        t.addComponent(v);        
//        
//        return t;
//    }

    @Override
    public void selectedTabChange(SelectedTabChangeEvent event) {
        //TODO
    }
}
