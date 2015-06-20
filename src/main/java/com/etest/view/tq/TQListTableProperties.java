/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author jetdario
 */
public class TQListTableProperties extends Table {

    public TQListTableProperties() {
        setWidth("100%");
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setStyleName("wordwrap-table");
        setImmediate(true);
        
        addContainerProperty("Exam Title", String.class, null);
        addContainerProperty("Subject", String.class, null);
        addContainerProperty("date created", Date.class, null);
        addContainerProperty("Total Hours", Double.class, null);
        addContainerProperty("Total Items", Integer.class, null);
        addContainerProperty("modify", HorizontalLayout.class, null);
    }
    
}
