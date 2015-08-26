/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view;

import com.etest.view.tq.reports.ReportMainUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class ReportManagementView extends VerticalLayout implements View {

    public ReportManagementView() {
        setWidth("100%");
        
        addComponent(new ReportMainUI());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO
    }
    
}
