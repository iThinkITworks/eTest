/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.TeamTeach;
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
public class TeamTeachDAO {
    
    public static List<TeamTeach> getAllSemestralTeamTeach(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<TeamTeach> teamTeachList = new ArrayList<TeamTeach>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_semestral_team_view "
                    + "WHERE TeamTeachStatus = 0 ");
            while(rs.next()){
                TeamTeach tt = new TeamTeach();
                tt.setSchoolYear(rs.getString("SchoolYear"));
                tt.setNormCourseOffering(CommonUtilities.convertStringToInt(rs.getString("Semester")));
                tt.setYearLevel(CommonUtilities.convertStringToInt(rs.getString("YearLevel")));
                tt.setSubject(rs.getString("Subject"));
                tt.setTeamLeader(rs.getString("TeamLeader"));
                teamTeachList.add(tt);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return teamTeachList;
    }
    
    public static List<String> getAllMembersFromTeam(int teamTeachId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<String> teamMembersList = new ArrayList<String>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT "
                    + "concat_ws(' ', f.Firstname, f.Lastname) AS member " 
                    + "FROM team_teach tt INNER JOIN faculty f "
                    + "ON tt.FacultyID = f.FacultyID "
                    + "WHERE tt.TeamTeachID = "+teamTeachId+" ");
            while(rs.next()){
                TeamTeach tt = new TeamTeach();
                teamMembersList.add(rs.getString("member"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return teamMembersList;
    }
    
    public static boolean insertNewTeamTeachLeader(TeamTeach tt){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO team_teach SET "
                    + "CurriculumID = ?, "
                    + "SchoolYear = ?, "
                    + "Semester = ?, "
                    + "UserID = ?");
            pstmt.setInt(1, tt.getCurriculumId());
            pstmt.setString(2, tt.getSchoolYear());
            pstmt.setInt(3, tt.getNormCourseOffering());
            pstmt.setInt(4, tt.getUserId());
            pstmt.executeUpdate();
            
            int teamTeachId = 0;            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id() AS id FROM team_teach ");
            while(rs.next()){
                teamTeachId = CommonUtilities.convertStringToInt(rs.getString("id"));
            }
            
            pstmt = conn.prepareStatement("INSERT INTO team_members SET "
                    + "TeamTeachID = ?, "
                    + "FacultyID = ? ");
            pstmt.setInt(1, teamTeachId);
            pstmt.setInt(2, tt.getFacultyId());
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return result;
    }
    
    public static boolean updateTeamTeachLeader(TeamTeach tt){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO team_teach SET "
                    + "CurriculumID = ?, "
                    + "SchoolYear = ?, "
                    + "Semester = ?, "
                    + "UserID = ? "
                    + "WHERE TeamTeachID = ? ");
            pstmt.setInt(1, tt.getCurriculumId());
            pstmt.setString(2, tt.getSchoolYear());
            pstmt.setInt(3, tt.getNormCourseOffering());
            pstmt.setInt(4, tt.getUserId());
            pstmt.setInt(5, tt.getTeamTeachId());
            pstmt.executeUpdate();
            
            return result;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return result;
    }
    
    public static boolean removeTeamTeachLeader(int teamTeachId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE team_teach SET "
                    + "TeamTeachStatus = ? "
                    + "WHERE TeamTeachID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, teamTeachId);
            pstmt.executeUpdate();
            
            return result;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TeamTeachDAO.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return result;
    }
}
