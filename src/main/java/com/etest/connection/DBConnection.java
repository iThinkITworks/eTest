/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class DBConnection {
    
    private static final String DB_USERNAME = "ge_test";
    private static final String DB_PASSWORD = "ge_test_15";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ge_test_schema";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public static Connection connectToDB(){
        Connection conn;
        try {
            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            return conn;
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            ErrorDBNotification.errorNotificationOnDBAccess("Connection failed to DB, SEVERE");
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);            
            return null;
        }
    }
}
