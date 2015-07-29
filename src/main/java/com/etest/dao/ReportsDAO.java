/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.InventoryOfCasesReport;
import com.etest.model.Syllabus;
import com.etest.service.SyllabusService;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.utilities.CommonUtilities;
import java.sql.Connection;
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
public class ReportsDAO {
    
    SyllabusService ss = new SyllabusServiceImpl();
    
    public static List<InventoryOfCasesReport> getInventoryOfCases(){
        Connection conn = DBConnection.connectToDB();
        
        Statement stmt = null;
        ResultSet rs = null;        
        List<InventoryOfCasesReport> reportList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CurrSubject, DescriptiveTitle, CurriculumID "
                    + "FROM curriculum WHERE CurriculumStatus = 0 ");
            while(rs.next()){
                InventoryOfCasesReport report = new InventoryOfCasesReport();
                report.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                report.setSubject(rs.getString("CurrSubject"));
                report.setDescriptiveTitle(rs.getString("DescriptiveTitle"));  
                reportList.add(report);
            }            
            
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return reportList;
    }
     
    public static List<Integer> getListOfSyllabusIdByCurriculumId(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> syllabusList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT SyllabusID FROM syllabus "
                    + "WHERE CurriculumID = "+curriculumId+" ");
            while(rs.next()){
                syllabusList.add(CommonUtilities.convertStringToInt(rs.getString("SyllabusID")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return syllabusList;
    }
    
    public static int getTotalCellCasesBySyllabus(List<Integer> syllabusIdList){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalCases = 0;
        
        try {
            for(Integer i : syllabusIdList){
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) AS totalCases FROM cell_cases "
                        + "WHERE SyllabusID = "+i+" AND CellCaseStatus = 0");
                while(rs.next()){
                    totalCases = totalCases + CommonUtilities.convertStringToInt(rs.getString("totalCases"));
                }
            }                    
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        
        return totalCases;
    }
    
    public static int getTotalCellItemsByCellCaseId(List<Integer> cellCaseIdList){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalItems = 0;
        
        try {
            for(Integer i : cellCaseIdList){
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) AS totalItems FROM cell_items "
                        + "WHERE CellCaseID = "+i+" AND CellItemStatus = 0 ");
                while(rs.next()){
                    totalItems = totalItems + CommonUtilities.convertStringToInt(rs.getString("totalItems"));
                }
            }                    
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        
        return totalItems;
    }
    
    public static List<Integer> getListOfCellCaseIdBySyllabusId(List<Integer> syllabusIdList){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> cellCaseIdList = new ArrayList<>();
        
        try {
            for(Integer i : syllabusIdList){
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT CellCaseID FROM cell_cases "
                        + "WHERE SyllabusID = "+i+" AND CellCaseStatus = 0 ");
                while(rs.next()){
                    cellCaseIdList.add(CommonUtilities.convertStringToInt(rs.getString("CellCaseID")));
                }
            }            
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellCaseIdList;
    }
}
