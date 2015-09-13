/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.VaadinSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class UsersDAO {
            
    public static boolean loginResult(String username, String password){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users "
                    + "WHERE Username_ = '"+username+"' "
                    + "AND Password_ = '"+password+"' ");
            while(rs.next()){
                VaadinSession.getCurrent().setAttribute("username", rs.getString("Username_"));
                VaadinSession.getCurrent().setAttribute("userId", rs.getString("UserID"));
                VaadinSession.getCurrent().setAttribute("userType", rs.getString("UserType"));
                VaadinSession.getCurrent().setAttribute("facultyId", rs.getString("FacultyID"));
                result = true;
            }                       
            
            if(result){
                pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                        + "UserID = ?, "
                        + "EntryDateTime = now(), "
                        + "Activity = ? ");            
                pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
                pstmt.setString(2, "Logged In!");
                pstmt.executeUpdate();
            }
            
        } catch (SQLException ex) {
            ErrorDBNotification.errorNotificationOnDBAccess("Severe Login ERROR");
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {            
                stmt.close();
                rs.close();
                
                if(result){
                    pstmt.close();
                }
                
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.errorNotificationOnDBAccess("Cannot close DB Connection");
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean updateUsersColumnValue(String column, 
            String value, 
            int facultyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE users SET "
                    + ""+column+" = ? "
                    + "WHERE FacultyID = ? ");
            pstmt.setString(1, value.toLowerCase());
            pstmt.setInt(2, facultyId);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.errorNotificationOnDBAccess("Severe Login ERROR");
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.errorNotificationOnDBAccess("Cannot close DB Connection");
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int getUserIdByFacultyId(int facultyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int userId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT UserID FROM users "
                    + "WHERE FacultyID = "+facultyId+" ");
            while(rs.next()){
                userId = CommonUtilities.convertStringToInt(rs.getString("UserID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return userId;
    }

    public static String getUsernameById(int userId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        String username = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Username_ FROM users "
                    + "WHERE UserID = "+userId+" ");
            while(rs.next()){
                username = rs.getString("Username_");
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return username;
    }    
}
