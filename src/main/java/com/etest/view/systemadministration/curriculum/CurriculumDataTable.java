/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.curriculum;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CurriculumDataTable extends Table {

    public CurriculumDataTable() {
        setWidth("100%");
        setSelectable(true);
        addStyleName(ValoTheme.TABLE_SMALL);
        addStyleName("wordwrap-table");
        addStyleName("selected-row");
        setImmediate(true);
        
//        addContainerProperty("id", Integer.class, null);
        addContainerProperty("year level", String.class, null);
        addContainerProperty("subject", String.class, null);
        addContainerProperty("descriptive title", String.class, null);
        addContainerProperty("normal course offering", String.class, null);
        addContainerProperty("modify", HorizontalLayout.class, null);
        
        setColumnAlignment("modify", Align.CENTER);
        setColumnWidth("modify", 130);
    }
    
}
