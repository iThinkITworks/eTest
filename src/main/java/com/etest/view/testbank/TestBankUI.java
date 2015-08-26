/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank;

import com.etest.view.tq.TQMainUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class TestBankUI extends TabSheet implements TabSheet.SelectedTabChangeListener {

    public TestBankUI() {
        setWidth("100%");
        addStyleName("bar");
        
        VerticalLayout v = new VerticalLayout();
        v.setCaption("Cell Management");
        v.setWidth("100%");
        v.addComponent(new CellCaseMainUI());
//        v.setMargin(true);
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Test Questionnaire Management");
        v.setWidth("100%");
        v.addComponent(new TQMainUI());
//        v.setMargin(true);
        addComponent(v);
        
    }

    @Override
    public void selectedTabChange(SelectedTabChangeEvent event) {
        //TODO
    }
    
}
