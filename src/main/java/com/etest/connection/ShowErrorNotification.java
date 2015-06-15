/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.connection;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

/**
 *
 * @author jetdario
 */
public class ShowErrorNotification {
    
    public static void error(String error){
        Label label = new Label();
        label.setWidth("400px");
        label.setValue(error);
        label.setContentMode(ContentMode.HTML);
        Notification.show(label.getValue(), Notification.Type.ERROR_MESSAGE);
    }
    
    public static void warning(String error){
        Notification.show(error, Notification.Type.WARNING_MESSAGE);
    }
    
    public static void tray(String error){
        Notification.show(error, Notification.Type.TRAY_NOTIFICATION);
    }
    
}
