/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.view.systemadministration.systemlogs.SystemLogsDataGrid;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class SystemLogsMainUI extends VerticalLayout {

    public SystemLogsMainUI() {
        setWidth("100%");
        
        addComponent(new SystemLogsDataGrid());
    }
    
}
