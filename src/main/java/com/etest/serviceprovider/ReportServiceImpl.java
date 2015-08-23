/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.service.ReportService;
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
public class ReportServiceImpl implements ReportService {

    @Override
    public int getTotalCases(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalCases = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS totalCases FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "WHERE c.CurriculumStatus = 0  ");
            while(rs.next()){
                totalCases = CommonUtilities.convertStringToInt(rs.getString("totalCases"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalCases;
    }
    
    @Override
    public int getTotalItems() {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalItems = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS TotalItems FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "INNER JOIN cell_items ci ON ci.CellCaseID = cc.CellCaseID "
                    + "WHERE c.CurriculumStatus = 0");
            while(rs.next()){
                totalItems = CommonUtilities.convertStringToInt(rs.getString("TotalItems"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalItems;
    }

    @Override
    public int getTotalCasesBySubject(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalCases = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS totalCases FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "WHERE c.CurriculumStatus = 0 "
                    + "AND c.CurriculumID = "+curriculumId+" ");
            while(rs.next()){
                totalCases = CommonUtilities.convertStringToInt(rs.getString("totalCases"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalCases;
    }
    
    @Override
    public int getTotalItemsBySubject(int curriculumId) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalItems = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS TotalItems FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "INNER JOIN cell_items ci ON ci.CellCaseID = cc.CellCaseID "
                    + "WHERE c.CurriculumStatus = 0 "
                    + "AND c.CurriculumID = "+curriculumId+" ");
            while(rs.next()){
                totalItems = CommonUtilities.convertStringToInt(rs.getString("TotalItems"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalItems;
    }

    @Override
    public int getTotalItemsByBloomsCass(int curriculumId, int bloomsClassId) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalItems = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS TotalItems FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "INNER JOIN cell_items ci ON ci.CellCaseID = cc.CellCaseID "
                    + "WHERE c.CurriculumStatus = 0 "
                    + "AND c.CurriculumID = "+curriculumId+" "
                    + "AND ci.BloomsClassID = "+bloomsClassId+" ");
            while(rs.next()){
                totalItems = CommonUtilities.convertStringToInt(rs.getString("TotalItems"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalItems;
    }

    @Override
    public int getTotalAnalyzedItemsBySubject(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalItems = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS TotalItems FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "INNER JOIN cell_items ci ON ci.CellCaseID = cc.CellCaseID "
                    + "WHERE c.CurriculumStatus = 0 "
                    + "AND c.CurriculumID = "+curriculumId+" "
                    + "AND ci.Analyzed = 1 ");
            while(rs.next()){
                totalItems = CommonUtilities.convertStringToInt(rs.getString("TotalItems"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalItems;
    };
    
    @Override
    public int getTotalItemByDiscriminationIndex(int curriculumId, double index1, double index2) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalItems = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS TotalItems FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "INNER JOIN cell_items ci ON ci.CellCaseID = cc.CellCaseID "
                    + "WHERE c.CurriculumStatus = 0 "
                    + "AND c.CurriculumID = "+curriculumId+" "
                    + "AND ci.Analyzed = 1 "
                    + "AND (ci.DiscriminationIndex = "+index1+" OR ci.DiscriminationIndex < "+index2+") ");
            while(rs.next()){
                totalItems = CommonUtilities.convertStringToInt(rs.getString("TotalItems"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalItems;
    }

    @Override
    public int getTotalItemByDifficultyIndex(int curriculumId, double index1, double index2) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int totalItems = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS TotalItems FROM "
                    + "curriculum c INNER JOIN syllabus s ON c.CurriculumID = s.CurriculumID "
                    + "INNER JOIN cell_cases cc ON cc.SyllabusID = s.SyllabusID "
                    + "INNER JOIN cell_items ci ON ci.CellCaseID = cc.CellCaseID "
                    + "WHERE c.CurriculumStatus = 0 "
                    + "AND c.CurriculumID = "+curriculumId+" "
                    + "AND ci.Analyzed = 1 "
                    + "AND (ci.DifficultIndex = "+index1+" OR ci.DifficultIndex <= "+index2+") ");
            while(rs.next()){
                totalItems = CommonUtilities.convertStringToInt(rs.getString("TotalItems"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return totalItems;
    }
    
}
