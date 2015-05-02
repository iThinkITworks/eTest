/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.datasource;

import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SyllabusDataGrid extends Grid {

    public SyllabusDataGrid() {
        setSizeFull();
        setSelectionMode(Grid.SelectionMode.SINGLE);
        addStyleName(ValoTheme.TABLE_SMALL);
        
        addColumn("ID", Integer.class);
        addColumn("subject", String.class);
        addColumn("descriptive title", String.class);
        addColumn("topic no", Integer.class);
        addColumn("topic", String.class);
        addColumn("estimated time", Float.class);
    }
}