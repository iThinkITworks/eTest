/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.datagrid;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SemestralTeamDataTable extends Table {

    public SemestralTeamDataTable() {
        setSizeFull();
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setImmediate(true);
        
        addContainerProperty("school year", String.class, null);
        addContainerProperty("semester", String.class, null);
        addContainerProperty("year level", String.class, null);
        addContainerProperty("subject", String.class, null);
        addContainerProperty("team leader", String.class, null);
        addContainerProperty("members", HorizontalLayout.class, null);
        
        setColumnAlignment("members", Align.CENTER);
        setColumnWidth("members", 150);
    }
    
}
