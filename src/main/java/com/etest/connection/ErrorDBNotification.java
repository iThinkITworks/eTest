/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.connection;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class ErrorDBNotification {

    public ErrorDBNotification() {
    }
    
    public static void errorNotificationOnDBAccess(String error){
        Notification.show(error, Notification.Type.ERROR_MESSAGE);
    }
    
    public static void showLoggedErrorOnWindow(String str){
        Window sub = new Window("Logged ERROR");
        sub.setWidth("500px");
        sub.setHeight("300px");
        if(sub.getParent() == null){
            UI.getCurrent().addWindow(sub);
        }
        sub.setModal(true);
        
        Panel panel = new Panel();        
        panel.setSizeFull();
        
        panel.setContent(new Label(str));        
        sub.setContent(panel);
    }
    
}
