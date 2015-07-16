/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.administrator;

import com.vaadin.server.VaadinSession;

/**
 *
 * @author jetdario
 */
public class UserAccess {
    
    public static boolean approve(){
        return VaadinSession.getCurrent().getAttribute("userType").toString().equals("Year Level Coordinator");
    }
    
    public static boolean delete(){
        return VaadinSession.getCurrent().getAttribute("userType").toString().equals("Year Level Coordinator");
    }
}
