/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.notification;

import com.etest.model.EtestNotification;
import com.etest.service.NotificationService;
import com.etest.serviceprovider.NotificationServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.etest.valo.ValoMenuLayout;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class NotificationMainUI extends VerticalLayout {

    NotificationService ns = new NotificationServiceImpl();
    Table notificationTable = new NotifiationDataTable();
    
    ValoMenuLayout root = new ValoMenuLayout();
    ComponentContainer viewDisplay = root.getContentContainer();
    
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
        if(VaadinSession.getCurrent().getAttribute("userId") == null){
            Page.getCurrent().setLocation("http://localhost:8080/");             
        } else {
            addComponent(populateNoficationTable());
        }
        
    }
    
    Table populateNoficationTable(){
        notificationTable.removeAllItems();
        int i = 0;
        System.out.println("userId: "+VaadinSession.getCurrent().getAttribute("userId"));
//        System.out.println("username: "+VaadinSession.getCurrent().getAttribute("username"));
        
        
        
        for(EtestNotification en : ns.getAllNotificationByUser(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()))){
            VerticalLayout v = new VerticalLayout();
            v.setWidth("100%");
            
            Button read = new Button("read");
            read.setSizeFull();
            read.setData(en.getNotificationId());
            read.setIcon(FontAwesome.ASTERISK);
            read.addStyleName(ValoTheme.BUTTON_LINK);
            read.addStyleName(ValoTheme.BUTTON_TINY);
            read.addStyleName(ValoTheme.BUTTON_QUIET);
            read.addStyleName("button-container");
//            read.addClickListener(modifyBtnClickListener);
            v.addComponent(read);
            v.setComponentAlignment(read, Alignment.MIDDLE_LEFT);
            
            notificationTable.addItem(new Object[]{
                en.getNotice(), 
                "Sender", 
                en.getNoteDate(), 
                v                
            }, i);
        }
        
        return notificationTable;
    }
    
}
