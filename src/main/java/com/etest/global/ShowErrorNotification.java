/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.global;

import com.vaadin.ui.Notification;

/**
 *
 * @author jetdario
 */
public class ShowErrorNotification {

    public ShowErrorNotification() {        
    }
    
    public static void error(String error){
        Notification.show(error, Notification.Type.ERROR_MESSAGE);
    }
    
}
