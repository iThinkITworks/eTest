/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.global.ShowErrorNotification;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class GenerateTQCoverage extends Button implements Button.ClickListener {

    TQCoverageService tq = new TQCoverageServiceImpl();
    Grid grid;
    int totalTestItems;
    FooterRow footer;
    
    public GenerateTQCoverage(Grid grid, 
            int totalTestItems, 
            FooterRow footer) {
        this.grid = grid;
        this.totalTestItems = totalTestItems;
        this.footer = footer;
        
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
        boolean isMaxItemsCompareToInputItems = tq.isMaxItemsCompareToInputItems(
                CommonUtilities.convertStringToDouble(getFooter().getCell("Max Items").getText()), 
                getTotalTestItems());
        System.out.println("total max items: "+getFooter().getCell("Max Items").getText());
        System.out.println("total test items: "+getTotalTestItems());
        if(!isMaxItemsCompareToInputItems){
            ShowErrorNotification.error("Max Items should be equal to Total Input Items!");
            return;
        }
    }
    
    Grid getTQGrid(){
        return grid;
    }
    
    int getTotalTestItems(){
        return totalTestItems;
    }
    
    FooterRow getFooter(){
        return footer;
    }
}
