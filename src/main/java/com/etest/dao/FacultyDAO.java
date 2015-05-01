/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.Users;
import com.etest.utilities.CommonUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class FacultyDAO {
    
    public static List<Users> getAllFaculty(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Users> userList = new ArrayList<Users>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_faculty_view "
                    + "WHERE FacultyStatus IS NULL");
            while(rs.next()){
                Users u = new Users();
                u.setName(rs.getString("name"));
                u.setUsername_(rs.getString("LoginName"));
                userList.add(u);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return userList;
    }
    
    public static String getFacultyNameById(int facultyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        String name = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name FROM enrolled_faculty_view "
                    + "WHERE FacultyStatus IS NULL "
                    + "AND facultyID = "+facultyId+" ");            
            while(rs.next()){
                name = rs.getString("name");
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    
    public static int getFacultyIdByName(String name){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int facultyId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT FacultyID FROM enrolled_faculty_view "
                    + "WHERE FacultyStatus IS NULL "
                    + "AND name = '"+name+"' ");            
            while(rs.next()){
                facultyId = CommonUtilities.convertStringToInt(rs.getString("FacultyID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return facultyId;
    }
    
    public static Users getFacultyInfoById(int facultyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        Users u = new Users();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT "
                    + "f.firstname AS Firstname, "
                    + "f.middlename AS Middlename, "
                    + "f.lastname AS Lastname, "
                    + "f.position AS Position, "
                    + "u.Username_ AS Username_, "
                    + "u.Password_ AS Password_, "
                    + "u.UserType AS UserType, "
                    + "u.Assignment AS Assignment, "
                    + "u.UserID AS UserID "
                    + "FROM faculty f INNER JOIN "
                    + "Users u ON f.FacultyID = u.FacultyID "
                    + "WHERE FacultyStatus IS NULL "
                    + "AND f.FacultyID = "+facultyId+" ");            
            while(rs.next()){
                u.setFirstname(rs.getString("Firstname"));
                u.setMiddlename(rs.getString("Middlename"));
                u.setLastname(rs.getString("Lastname"));
                u.setPosition(rs.getString("Position"));
                u.setUsername_(rs.getString("Username_"));
                u.setPassword_(rs.getString("Password_"));
                u.setUserType(rs.getString("UserType"));
                u.setAssignment(rs.getString("Assignment"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return u;
    }
    
    public static boolean insertNewFaculty(Users users){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO faculty SET "
                    + "Firstname = ?, "
                    + "Middlename = ?, "
                    + "Lastname = ? ");
            pstmt.setString(1, users.getFirstname());
            pstmt.setString(2, users.getMiddlename());
            pstmt.setString(3, users.getLastname());
            pstmt.executeUpdate();
            
            
            int facultyId = 0;            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id() AS id FROM faculty ");
            while(rs.next()){
                facultyId = CommonUtilities.convertStringToInt(rs.getString("id"));
            }
            
            pstmt = conn.prepareStatement("INSERT INTO users SET "
                    + "FacultyID = ?, "
                    + "Username_ = ?, "
                    + "Password_ = ? ");
            pstmt.setInt(1, facultyId);
            pstmt.setString(2, users.getUsername_());
            pstmt.setString(3, users.getPassword_());
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();                
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static boolean updateFaculty(Users users){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE faculty SET "
                    + "Firstname = ?, "
                    + "Middlename = ?, "
                    + "Lastname = ? "
                    + "WHERE FacultyID = ?");
            pstmt.setString(1, users.getFirstname());
            pstmt.setString(2, users.getMiddlename());
            pstmt.setString(3, users.getLastname());
            pstmt.setInt(4, users.getFacultyId());
            pstmt.executeUpdate();
                        
            pstmt = conn.prepareStatement("UPDATE users SET "
                    + "Username_ = ?, "
                    + "Password_ = ? "
                    + "WHERE FacultyID = ? ");            
            pstmt.setString(1, users.getUsername_());
            pstmt.setString(2, users.getPassword_());
            pstmt.setInt(3, users.getFacultyId());
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();                
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static boolean removeFaculty(int facultyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE faculty SET "
                    + "FacultyStatus = ? "
                    + "WHERE FacultyID = ?");
            pstmt.setString(1, "deleted");
            pstmt.setInt(2, facultyId);
            pstmt.executeUpdate();                        
            
            result = true;
        } catch (SQLException ex) {            
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(FacultyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
