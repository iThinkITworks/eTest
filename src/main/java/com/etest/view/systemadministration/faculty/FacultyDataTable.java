/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.faculty;

import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class FacultyDataTable extends Table {

    public FacultyDataTable() {
        setWidth("100%");
        setSelectable(true);
        addStyleName(ValoTheme.TABLE_SMALL);
        setImmediate(true);
        
        addContainerProperty("name", String.class, null);
        addContainerProperty("login name", String.class, null);
        addContainerProperty("user type", String.class, null);
        addContainerProperty("position", String.class, null);
        addContainerProperty("modify", HorizontalLayout.class, null);
        
        setColumnAlignment("modify", Align.CENTER);
        setColumnWidth("modify", 150);
    }
    
}
