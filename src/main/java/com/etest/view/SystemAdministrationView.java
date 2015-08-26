/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view;

import com.etest.view.systemadministration.SystemAdministrationUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class SystemAdministrationView extends VerticalLayout implements View {

    public SystemAdministrationView() {
        setWidth("100%");
        
        addComponent(new SystemAdministrationUI());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO
    }
    
}
