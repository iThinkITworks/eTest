/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class GenerateTQCoverage extends Button implements Button.ClickListener {

    Grid grid;
    int totalTestItems;
    
    public GenerateTQCoverage(Grid grid, int totalTestItems) {
        this.grid = grid;
        this.totalTestItems = totalTestItems;
        
        setCaption("GENERATE TQ");
        setWidth("100%");
        setIcon(FontAwesome.GEAR);
        addStyleName(ValoTheme.BUTTON_PRIMARY);
        addStyleName(ValoTheme.BUTTON_SMALL);
        setImmediate(true);
        
        addClickListener(this);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        //TODO
    }
    
    Grid getTQGrid(){
        return grid;
    }
    
    int getTotalTestItems(){
        return totalTestItems;
    }
}
