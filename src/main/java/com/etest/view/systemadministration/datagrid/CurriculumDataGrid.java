/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.datagrid;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CurriculumDataGrid extends Grid {
    
    public CurriculumDataGrid() {        
        setSizeFull();
        setSelectionMode(SelectionMode.SINGLE);
        addStyleName(ValoTheme.TABLE_SMALL);
        
        addColumn("ID", Integer.class);
        addColumn("year level", String.class);
        addColumn("subject", String.class);
        addColumn("descriptive title", String.class);
        addColumn("normal course offering", String.class);
        
    }
    
}