/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.itemanalysis;

import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author jetdario
 */
public class ItemAnalysisDataTableProperties extends Table {
    
    public ItemAnalysisDataTableProperties() {
        setWidth("80%");
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setImmediate(true);
        
        addContainerProperty("Exam Title", String.class, null);
        addContainerProperty("Subject", String.class, null);
        addContainerProperty("date created", Date.class, null);
        addContainerProperty("Total Hours", Double.class, null);
        addContainerProperty("Total Items", Integer.class, null);
        addContainerProperty("remarks", VerticalLayout .class, null);
        
        setColumnAlignment("date created", Align.CENTER);
        setColumnAlignment("Total Hours", Align.CENTER);
        setColumnAlignment("Total Items", Align.CENTER);
        setColumnAlignment("remarks", Align.CENTER);
        
        setColumnWidth("remarks", 100);
    }
    
}
