/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CellItemDataTable extends Table {

    public CellItemDataTable() {
        setWidth("100%");
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setStyleName("wordwrap-table");
        setImmediate(true);
        
//        addContainerProperty("id", Integer.class, null);
        addContainerProperty("stem", Label.class, null);
        addContainerProperty("modify", VerticalLayout.class, null);
                
        setColumnWidth("stem", 570);
//        setColumnWidth("modify", 130);
        setColumnAlignment("modify", Align.CENTER);
    }
    
}
