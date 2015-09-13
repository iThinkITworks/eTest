/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.systemlogs;

import com.etest.model.SystemLogs;
import com.etest.service.SystemLogService;
import com.etest.service.UsersService;
import com.etest.serviceprovider.SystemLogServiceImpl;
import com.etest.serviceprovider.UsersServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Grid;
import java.util.Date;

/**
 *
 * @author jetdario
 */
public class SystemLogsDataGrid extends Grid implements ItemClickEvent.ItemClickListener {

    SystemLogService sls = new SystemLogServiceImpl();
    UsersService us = new UsersServiceImpl();
    
    public SystemLogsDataGrid() {        
        setWidth("950px");
        setHeight("600px");
        setSelectionMode(Grid.SelectionMode.SINGLE);
        setImmediate(true);
        
        addColumn("user", String.class);
        addColumn("activity", String.class);
        addColumn("entry date", Date.class);
        
        getColumn("user").setWidth(150);
        getColumn("activity").setWidth(550);
        getColumn("entry date").setWidth(250);
        
        setFrozenColumnCount(1);
        
        getContainerDataSource().removeAllItems();
        sls.getAllLogActivity().stream().forEach((sl) -> {
            addRow(us.getUsernameById(sl.getUserId()),
                    sl.getActivity(), 
                    sl.getEntryDateTime());
        });
        
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        Object itemId = event.getItemId();
        Item item = getContainerDataSource().getItem(itemId);
    }
    
    
}
