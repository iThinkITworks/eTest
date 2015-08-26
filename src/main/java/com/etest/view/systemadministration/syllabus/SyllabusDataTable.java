/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.syllabus;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SyllabusDataTable extends Table {

    public SyllabusDataTable() {
        setWidth("100%");
        setSelectable(true);
        addStyleName(ValoTheme.TABLE_SMALL);
        addStyleName("wordwrap-table");
        setImmediate(true);
        
        addContainerProperty("subject", String.class, null);
        addContainerProperty("descriptive title", Label.class, null);
        addContainerProperty("topic no", Integer.class, null);
        addContainerProperty("topic", Label.class, null);
        addContainerProperty("time", Float.class, null);
        addContainerProperty("modify", VerticalLayout.class, null);
        
        setColumnAlignment("modify", Align.LEFT);
        setColumnAlignment("time", Align.CENTER);
        setColumnAlignment("topic no", Align.CENTER);
        
        setColumnWidth("modify", 80);
        setColumnWidth("descriptive title", 300);
        setColumnWidth("subject", 80);
        setColumnWidth("time", 50);
    }
    
}
