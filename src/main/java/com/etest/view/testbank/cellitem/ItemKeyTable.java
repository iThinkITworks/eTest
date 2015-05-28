/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author jetdario
 */
public class ItemKeyTable extends Table {

    public ItemKeyTable() {
        setWidth("100%");
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setStyleName("wordwrap-table");
        setImmediate(true);
        
        addContainerProperty("key", String.class, null);
        addContainerProperty("answer", String.class, null);
        addContainerProperty("modify", Button.class, null);
        
        setColumnWidth("modify", 100);
        setColumnAlignment("modify", Align.CENTER);
    }
    
}
