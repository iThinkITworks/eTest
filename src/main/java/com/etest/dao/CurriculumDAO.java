/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.Curriculum;
import com.etest.utilities.CommonUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class CurriculumDAO {
    
    public static boolean insertNewCurriculum(Curriculum curriculum){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO curriculum "
                    + "SET YearLevel = ?, "
                    + "CurrSubject = ?, "
                    + "DescriptiveTitle = ?, "
                    + "NormCourseOffering = ?");
            pstmt.setInt(1, curriculum.getYearLevel());
            pstmt.setString(2, curriculum.getSubject());
            pstmt.setString(3, curriculum.getDescriptiveTitle());
            pstmt.setInt(4, curriculum.getNormCourseOffering());
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();                
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean updateCurriculum(Curriculum curriculum){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE curriculum "
                    + "SET YearLevel = ?, "
                    + "CurrSubject = ?, "
                    + "DescriptiveTitle = ?, "
                    + "NormCourseOffering = ? "
                    + "WHERE CurriculumID = ? ");
            pstmt.setInt(1, curriculum.getYearLevel());
            pstmt.setString(2, curriculum.getSubject());
            pstmt.setString(3, curriculum.getDescriptiveTitle());
            pstmt.setInt(4, curriculum.getNormCourseOffering());
            pstmt.setInt(5, curriculum.getCurriculumId());
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();                
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static List<Curriculum> getAllCurriculum(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Curriculum> curriculumList = new ArrayList<Curriculum>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM curriculum WHERE "
                    + "CurriculumStatus = 0");
            while(rs.next()){
                Curriculum curriculum = new Curriculum();
                curriculum.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                curriculum.setYearLevel(CommonUtilities.convertStringToInt(rs.getString("YearLevel")));
                curriculum.setSubject(rs.getString("CurrSubject"));
                curriculum.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                curriculum.setNormCourseOffering(CommonUtilities.convertStringToInt(rs.getString("NormCourseOffering")));
                curriculumList.add(curriculum);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return curriculumList;
    }
    
    public static boolean removeCurriculum(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE curriculum "
                    + "SET CurriculumStatus = ? "
                    + "WHERE CurriculumID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, curriculumId);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();                
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static Curriculum getCurriculumById(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        Curriculum curriculum = new Curriculum();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM curriculum "
                    + "WHERE CurriculumID = "+curriculumId+" ");
            while(rs.next()){                
                curriculum.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                curriculum.setYearLevel(CommonUtilities.convertStringToInt(rs.getString("YearLevel")));
                curriculum.setSubject(rs.getString("CurrSubject"));
                curriculum.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                curriculum.setNormCourseOffering(CommonUtilities.convertStringToInt(rs.getString("NormCourseOffering")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return curriculum;
    }
    
    public static Map<Integer, String> getSubjectsFromCurriculum(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        Map<Integer, String> subjectListMap = new HashMap<Integer, String>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CurriculumID, CurrSubject FROM curriculum "
                    + "WHERE CurriculumStatus = 0");
            while(rs.next()){
                subjectListMap.put(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")), rs.getString("CurrSubject"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return subjectListMap;
    }
}
