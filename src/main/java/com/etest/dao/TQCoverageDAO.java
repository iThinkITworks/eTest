/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.utilities.CommonUtilities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class TQCoverageDAO {
    
    public static int getBloomsClassId(String bloomsClass){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int bloomsClassId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT BloomsClassID FROM blooms_taxonomy "
                    + "WHERE BloomsClass = '"+bloomsClass+"' ");
            while(rs.next()){
                bloomsClassId = CommonUtilities.convertStringToInt(rs.getString("BloomsClassID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return bloomsClassId;
    }
    
}
