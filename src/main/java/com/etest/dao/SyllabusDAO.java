/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.Syllabus;
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
public class SyllabusDAO {
    
    public static List<Syllabus> getAllSyllabus(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Syllabus> syllabusList = new ArrayList<Syllabus>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_syllabus_view "
                    + "WHERE CurriculumStatus = 0");
            while(rs.next()){
                Syllabus s = new Syllabus();
                s.setSyllabusId(CommonUtilities.convertStringToInt(rs.getString("SyllabusID")));
                s.setSubject(rs.getString("Subject"));
                s.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                s.setTopicNo(CommonUtilities.convertStringToInt(rs.getString("TopicNo")));
                s.setTopic(rs.getString("Topic"));
                s.setEstimatedTime(CommonUtilities.convertStringToFloat(rs.getString("EstimatedTime")));
                s.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                syllabusList.add(s);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return syllabusList;
    }
    
    public static Syllabus getSyllabusById(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        Syllabus s = new Syllabus();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_syllabus_view "
                    + "WHERE SyllabusID = "+syllabusId+" ");
            while(rs.next()){
                s.setSubject(rs.getString("Subject"));
                s.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                s.setTopicNo(CommonUtilities.convertStringToInt(rs.getString("TopicNo")));
                s.setTopic(rs.getString("Topic"));
                s.setEstimatedTime(CommonUtilities.convertStringToFloat(rs.getString("EstimatedTime")));
                s.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return s;
    }
    
    public static List<Syllabus> getSyllabusByCurriculum(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Syllabus> syllabusList = new ArrayList<Syllabus>();
        
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_syllabus_view "
                    + "WHERE CurriculumID IS "+curriculumId+" ");
            while(rs.next()){
                Syllabus s = new Syllabus();
                s.setSubject(rs.getString("Subject"));
                s.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                s.setTopicNo(CommonUtilities.convertStringToInt(rs.getString("TopicNo")));
                s.setTopic(rs.getString("Topic"));
                s.setEstimatedTime(CommonUtilities.convertStringToFloat(rs.getString("EstimatedTime")));
                s.setSyllabusId(CommonUtilities.convertStringToInt(rs.getString("SyllabusID")));
                syllabusList.add(s);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return syllabusList;
    }
    
    public static boolean insertNewSyllabus(Syllabus s){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO syllabus SET "
                    + "CurriculumID = ?, "
                    + "TopicNo = ?, "
                    + "Topic = ?, "
                    + "EstimatedTime = ? ");            
            pstmt.setInt(1, s.getCurriculumId());
            pstmt.setInt(2, s.getTopicNo());
            pstmt.setString(3, s.getTopic());
            pstmt.setFloat(4, s.getEstimatedTime());
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean updateSyllabus(Syllabus s){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE syllabus SET "
                    + "CurriculumID = ?, "
                    + "TopicNo = ?, "
                    + "Topic = ?, "
                    + "EstimatedTime = ? "
                    + "WHERE SyllabusID = ? ");            
            pstmt.setInt(1, s.getCurriculumId());
            pstmt.setInt(2, s.getTopicNo());
            pstmt.setString(3, s.getTopic());
            pstmt.setFloat(4, s.getEstimatedTime());
            pstmt.setInt(5, s.getSyllabusId());
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean removeSyllabus(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("DELETE FROM syllabus "
                    + "WHERE SyllabusID = ? ");            
            pstmt.setInt(1, syllabusId);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
}
