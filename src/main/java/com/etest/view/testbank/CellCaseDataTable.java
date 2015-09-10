/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank;

import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author jetdario
 */
public class CellCaseDataTable extends Table {

    public CellCaseDataTable() {
        setWidth("100%");
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setStyleName("wordwrap-table");
        setImmediate(true);
        
        addContainerProperty("id", Integer.class, null);
        addContainerProperty("case", Label.class, null);
        addContainerProperty("author", String.class, null);
        addContainerProperty("date created", Date.class, null);
        addContainerProperty("modify", VerticalLayout.class, null);
        
        setColumnWidth("case", 550);
        setColumnWidth("id", 50);
        setColumnWidth("date created", 150);
        setColumnWidth("modify", 100);
        setColumnAlignment("modify", Align.CENTER);
        
    }
    
}
