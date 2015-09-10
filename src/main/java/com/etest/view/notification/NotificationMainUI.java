/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.notification;

import com.etest.model.EtestNotification;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.service.NotificationService;
import com.etest.service.UsersService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.NotificationServiceImpl;
import com.etest.serviceprovider.UsersServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class NotificationMainUI extends VerticalLayout {

    NotificationService ns = new NotificationServiceImpl();
    UsersService ss = new UsersServiceImpl();
    CellCaseService ccs = new CellCaseServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    Table notificationTable = new NotifiationDataTable();
        
    Label message = new Label();
    
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
        sendMsgBtn.setVisible(false);        
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
        for(EtestNotification en : ns.getAllNotificationByUser(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()))){
            VerticalLayout v = new VerticalLayout();
            v.setWidth("100%");
            
            Button read = new Button();
            read.setWidthUndefined();
            read.setCaption((en.getStatus()==0)?"unread":"read");
            read.setData(en.getNotificationId());
            read.setIcon(FontAwesome.VIDEO_CAMERA);
            read.addStyleName(ValoTheme.BUTTON_LINK);
            read.addStyleName(ValoTheme.BUTTON_TINY);
            read.addStyleName(ValoTheme.BUTTON_QUIET);
            read.addStyleName("button-container");
            read.addClickListener(buttonClickListener);
            v.addComponent(read);
            v.setComponentAlignment(read, Alignment.MIDDLE_LEFT);
                        
            message.setContentMode(ContentMode.HTML);
            
            String[] split = en.getNotice().split(" ");
            int cellCaseId;
            if(split[0].equals("CellCaseID")){
                message.setCaption(en.getRemarks()+en.getNotice());
                cellCaseId = CommonUtilities.convertStringToInt(split[1].replace("#", ""));
            } else {
                cellCaseId = cis.getCellItemById(CommonUtilities.convertStringToInt(split[1].replace("#", ""))).getCellCaseId();
                message.setCaption(en.getRemarks()+en.getNotice()+" from CellCaseID #"+cellCaseId);                
            }
            
            Button approve = new Button("status");
            approve.setSizeFull();         
            approve.addStyleName(ValoTheme.BUTTON_LINK);
            approve.addStyleName(ValoTheme.BUTTON_TINY);
            approve.addStyleName(ValoTheme.BUTTON_QUIET);
            approve.addStyleName("button-container");
            v.addComponent(approve);
            v.setComponentAlignment(approve, Alignment.MIDDLE_LEFT);
            
            if(ccs.getCellCaseById(cellCaseId).getApprovalStatus() == 0){ approve.setIcon(FontAwesome.THUMBS_DOWN); }
            else { approve.setIcon(FontAwesome.THUMBS_UP); }
                        
            notificationTable.addItem(new Object[]{
                message.getCaption().toString(), 
                ss.getUsernameById(en.getSenderId()), 
                en.getNoteDate(), 
                v                
            }, i);
            i++;
        }
        
        return notificationTable;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) ->{
        if(event.getButton().getCaption().equals("read") || 
                event.getButton().getCaption().equals("unread")){
            EtestNotification en = ns.getNotificationById((int) event.getButton().getData());
            String[] split = en.getNotice().split(" ");
            int cellCaseId;
            int cellItemId;
            if(split[0].equals("CellCaseID")){
                cellCaseId = CommonUtilities.convertStringToInt(split[1].replace("#", ""));
                cellItemId = 0;
            } else {
                cellCaseId = cis.getCellItemById(CommonUtilities.convertStringToInt(split[1].replace("#", ""))).getCellCaseId();
                cellItemId = CommonUtilities.convertStringToInt(split[1].replace("#", ""));
            }
            
            Window sub = new ViewCaseNotificationWindow(cellCaseId, cellItemId);
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }          
            sub.addCloseListener((Window.CloseEvent e) -> {
                populateNoficationTable();
            });            
            
            ns.updateNoficationStatus((int) event.getButton().getData());
        } else {
            
        }
    };
    
    int getCellCaseId(String[] str){
        int cellCaseId = CommonUtilities.convertStringToInt(str[1].replace("#", ""));
        return cellCaseId;
    }
}
