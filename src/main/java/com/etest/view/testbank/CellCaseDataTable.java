/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author jetdario
 */
public class CellCaseDataTable extends Table {

    public CellCaseDataTable() {
        setWidth("100%");
        setSelectable(true);
        addStyleName(ValoTheme.TABLE_SMALL);
        setStyleName("wordwrap-table");
        setImmediate(true);
        
        addContainerProperty("id", Integer.class, null);
        addContainerProperty("case", String.class, null);
        addContainerProperty("author", String.class, null);
        addContainerProperty("date created", Date.class, null);
        addContainerProperty("modify", HorizontalLayout.class, null);
        
        setColumnWidth("case", 500);
    }
    
}
