/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.notification;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author jetdario
 */
public class NotifiationDataTable extends Table {

    public NotifiationDataTable() {
        setWidthUndefined();
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setStyleName("wordwrap-table");
        setImmediate(true);
        
        addContainerProperty("message", String.class, null);
        addContainerProperty("sender", String.class, null);
        addContainerProperty("date created", Date.class, null);
        addContainerProperty("action", VerticalLayout.class, null);
        
        setColumnWidth("message", 600);
        setColumnWidth("sender", 100);
        setColumnWidth("date created", 150);
        setColumnWidth("action", 100);
        
        setColumnAlignment("action", Align.CENTER);
    }
    
}
