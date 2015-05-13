/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.CellCase;
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
public class CellCaseDAO {
    
    public static List<CellCase> getAllCellCase(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellCase> cellCaseList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE CellCaseStatus = 0 ");
            while(rs.next()){
                CellCase c = new CellCase();
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(rs.getString("Topic"));
                c.setCaseTopic(rs.getString("caseTopic"));
                c.setUsername_(rs.getString("Author"));
                c.setApprovalStatus(CommonUtilities.convertStringToInt(rs.getString("ApprovalStatus")));
                cellCaseList.add(c);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellCaseList;
    }
    
    public static List<CellCase> getCellCaseByTopic(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellCase> cellCaseList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE SyllabusID = "+syllabusId+" "
                    + "AND CellCaseStatus = 0 ");
            while(rs.next()){
                CellCase c = new CellCase();
                c.setCellCaseId(CommonUtilities.convertStringToInt(rs.getString("CellCaseID")));
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(rs.getString("Topic"));
                c.setCaseTopic(rs.getString("caseTopic"));
                c.setUsername_(rs.getString("Author"));
                c.setDateCreated(CommonUtilities.parsingDateWithTime(rs.getString("DateCreated")));
                c.setApprovalStatus(CommonUtilities.convertStringToInt(rs.getString("ApprovalStatus")));
                cellCaseList.add(c);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellCaseList;
    }
    
    public static CellCase getCellCaseById(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        CellCase c = new CellCase();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE CellCaseID = "+cellCaseId+" "
                    + "AND CellCaseStatus = 0 ");
            while(rs.next()){                
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(rs.getString("Topic"));
                c.setCaseTopic(rs.getString("caseTopic"));
                c.setUsername_(rs.getString("Author"));
                c.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                c.setSyllabusId(CommonUtilities.convertStringToInt(rs.getString("SyllabusID")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return c;
    }
    
    public static List<CellCase> getCellCaseByAuthor(int userId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellCase> cellCaseList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE UserID = "+userId+" "
                    + "AND CellCaseStatus = 0 ");
            while(rs.next()){
                CellCase c = new CellCase();
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(rs.getString("Topic"));
                c.setCaseTopic(rs.getString("caseTopic"));
                c.setUsername_(rs.getString("Username_"));
                cellCaseList.add(c);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellCaseList;
    }
    
    public static boolean insertNewCellCase(CellCase c){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO cell_cases SET "
                    + "SyllabusID = ?, "
                    + "CaseTopic = ?, "
                    + "AuthoredBy_UserID = ?, "
                    + "DateCreated = now() ");
            pstmt.setInt(1, c.getSyllabusId());
            pstmt.setString(2, c.getCaseTopic());
            pstmt.setInt(3, c.getUserId());
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean modifyCellCase(CellCase c){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE cell_cases SET "
                    + "SyllabusID = ?, "
                    + "CaseTopic = ? "
                    + "WHERE CellCaseID = ? ");
            pstmt.setInt(1, c.getSyllabusId());
            pstmt.setString(2, c.getCaseTopic());
            pstmt.setInt(3, c.getCellCaseId());
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean approveCellCase(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE cell_cases SET "
                    + "ApprovalStatus = ? "
                    + "WHERE CellCaseID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, cellCaseId);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean removeCellCase(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE cell_cases SET "
                    + "CellCaseStatus = ? "
                    + "WHERE CellCaseID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, cellCaseId);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean isCellCaseApproved(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS approved FROM "
                    + "enrolled_cell_case_view");
            while(rs.next()){
                if(rs.getString("approved").equals("1")){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
}
