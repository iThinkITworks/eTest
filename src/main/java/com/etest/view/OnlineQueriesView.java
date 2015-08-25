/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view;

import com.etest.view.tq.reports.OnlineQueriesUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class OnlineQueriesView extends VerticalLayout implements View {

    public OnlineQueriesView() {
        setWidth("100%");
        
        addComponent(new OnlineQueriesUI());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO
    }
    
}
