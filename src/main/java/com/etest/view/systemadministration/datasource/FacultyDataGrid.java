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
public class FacultyDataGrid extends Grid {

    public FacultyDataGrid() {
        setSizeFull();
        setSelectionMode(SelectionMode.SINGLE);
        
        addColumn("Name", String.class);
        addColumn("Login Name", String.class);
        
    }
    
}
