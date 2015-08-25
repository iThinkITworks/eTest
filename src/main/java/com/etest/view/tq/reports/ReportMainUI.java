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
public class ReportMainUI extends VerticalLayout {
    
    public ReportMainUI() {
        setSizeFull();
        setSpacing(true);
        
//        addComponent(buildReportComponent());
    }    
    
    Component buildReportComponent(){
        TabSheet t = new TabSheet();
        t.setWidth("100%");
        
        VerticalLayout v = new VerticalLayout();
        v.setCaption("Online Queries");
        v.setWidth("100%");
        v.setMargin(true);
        v.addComponent(new OnlineQueriesUI());
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
