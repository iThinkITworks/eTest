/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.connection;

import com.vaadin.ui.Notification;

/**
 *
 * @author jetdario
 */
public class ErrorDBNotification {

    public ErrorDBNotification() {
    }
    
    public static void errorConnectoToDB(){
        Notification.show("Cant connect to DB!", Notification.Type.ERROR_MESSAGE);
    }
    
}
