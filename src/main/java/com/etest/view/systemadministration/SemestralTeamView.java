/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class SemestralTeamView extends VerticalLayout implements View {

    public SemestralTeamView() {
        setSizeFull();
        
        Label h1 = new Label("Semestral Team View");
        h1.addStyleName("h1");
        addComponent(h1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO
    }
    
}
