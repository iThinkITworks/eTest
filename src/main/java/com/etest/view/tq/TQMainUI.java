/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.view.itemanalysis.TQItemAnalysisUI;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class TQMainUI extends TabSheet implements TabSheet.SelectedTabChangeListener {
    
    TQListUI tqListUI = new TQListUI();
    TQItemAnalysisUI tqItemAnalysis = new TQItemAnalysisUI();
    
    public TQMainUI() {
        setWidth("100%");
        addStyleName("bar");
        
        VerticalLayout v = new VerticalLayout();
        v.setCaption("Create TQ Coverage");
        v.setWidth("100%");
        v.addComponent(new TQCoverageUI());
        v.setMargin(true);
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("TQ List");
        v.setWidth("100%");
        v.addComponent(tqListUI.populateDataTable());
        v.setMargin(true);        
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Item Analysis");
        v.setWidth("100%");
        v.addComponent(tqItemAnalysis.populateDataTable());
        v.setMargin(true);        
        addComponent(v);
        
        addSelectedTabChangeListener(this);
    }

    @Override
    public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        //TODO
        tqItemAnalysis.populateDataTable();
        tqListUI.populateDataTable();        
    }
        
}