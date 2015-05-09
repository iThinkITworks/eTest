/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class AddNewMembersTable extends Table {

    public AddNewMembersTable() {
        setWidth("100%");
        setSelectable(false);
        addStyleName(ValoTheme.TABLE_SMALL);
        setImmediate(true);
        
        addContainerProperty("id", Integer.class, null);
        addContainerProperty("member", String.class, null);
        addContainerProperty("position", String.class, null);
        addContainerProperty("modify", HorizontalLayout.class, null);
        
        setColumnAlignment("modify", Align.CENTER);
    }
    
}
