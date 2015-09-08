/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.notification;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class NotificationMainUI extends VerticalLayout {

    Table notificationTable = new NotifiationDataTable();
    
    public NotificationMainUI() {
        setSizeFull();
        setSpacing(true);
        
        Button sendMsgBtn = new Button("Send Message");
        sendMsgBtn.setWidthUndefined();
        sendMsgBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        sendMsgBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        sendMsgBtn.addClickListener((Button.ClickEvent event) -> {
            Notification.show("Send Message!");
        });
        
        addComponent(sendMsgBtn);        
        addComponent(populateNoficationTable());
    }
    
    Table populateNoficationTable(){
        notificationTable.removeAllItems();
        
        return notificationTable;
    }
    
}
