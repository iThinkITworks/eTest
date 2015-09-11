/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.housekeeping;

import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class HousekeepingDataTable extends Table {

    public HousekeepingDataTable() {
        setWidthUndefined();
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setStyleName("wordwrap-table");
        setImmediate(true);
        
        addContainerProperty("id", Integer.class, null);
        addContainerProperty("stem", String.class, null);
//        addContainerProperty("option-a", String.class, null);
//        addContainerProperty("option-b", String.class, null);
//        addContainerProperty("option-c", String.class, null);
//        addContainerProperty("option-d", String.class, null);
        addContainerProperty("modify", VerticalLayout.class, null);
                
        setColumnWidth("id", 50);
        setColumnWidth("stem", 650);
        setColumnWidth("modify", 100);
        setColumnAlignment("modify", Align.CENTER);
    }
    
}
