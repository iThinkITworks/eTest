/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.datagrid;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Grid;

/**
 *
 * @author jetdario
 */
public class CurriculumDataGrid extends Grid {

    IndexedContainer indexedContainer = new CurriculumContainerDataSource();
    
    public CurriculumDataGrid() {
        setSelectionMode(SelectionMode.SINGLE);
        setSizeFull();
        
        setContainerDataSource(indexedContainer);
    }
    
}
