/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view;

import com.etest.view.testbank.TestBankUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class TestBankView extends VerticalLayout implements View {

    public TestBankView() {
        setWidth("100%");
        
        addComponent(new TestBankUI());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO
    }
    
}
