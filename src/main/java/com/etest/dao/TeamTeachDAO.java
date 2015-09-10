/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.TeamTeach;
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
public class TeamTeachDAO {
    
    public static List<TeamTeach> getAllSemestralTeamTeach(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<TeamTeach> teamTeachList = new ArrayList<TeamTeach>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_semestral_team_view ");
            while(rs.next()){
                TeamTeach tt = new TeamTeach();
                tt.setTeamTeachId(CommonUtilities.convertStringToInt(rs.getString("TeamTeachID")));
                tt.setSchoolYear(rs.getString("SchoolYear"));
                tt.setNormCourseOffering(CommonUtilities.convertStringToInt(rs.getString("Semester")));
                tt.setYearLevel(CommonUtilities.convertStringToInt(rs.getString("YearLevel")));
                tt.setSubject(rs.getString("CurrSubject"));
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
    
    public static List<TeamTeach> getAllMembersFromTeam(int teamTeachId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<TeamTeach> teamMembersList = new ArrayList<TeamTeach>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT "
                    + "concat_ws(' ', f.Firstname, f.Lastname) AS member, "
                    + "f.FacultyID AS FacultyID " 
                    + "FROM team_members tm INNER JOIN "
                    + "faculty f ON f.FacultyID = tm.FacultyID "
                    + "WHERE tm.TeamTeachID = "+teamTeachId+" ");
            while(rs.next()){
                TeamTeach tt = new TeamTeach();
                tt.setFacultyId(CommonUtilities.convertStringToInt(rs.getString("FacultyID")));
                tt.setName(rs.getString("member"));
                teamMembersList.add(tt);
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
    
    public static int getTeamTeachIdByUserId(int userId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int teamTeachId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT TeamTeachID FROM enrolled_semestral_team_view  "
                    + "WHERE UserID = "+userId+" ");
            while(rs.next()){
                teamTeachId = CommonUtilities.convertStringToInt(rs.getString("TeamTeachID"));
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
        
        return teamTeachId;
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
    
    public static boolean updateTeamTeachLeader(int teamTeachId, int userId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE team_teach SET "
                    + "UserID = ? "
                    + "WHERE TeamTeachID = ? ");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, teamTeachId);
            pstmt.executeUpdate();
            
            result = true;
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
    
    public static boolean removeSemestralTeam(int teamTeachId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("DELETE FROM team_teach "
                    + "WHERE TeamTeachID = ? ");
            pstmt.setInt(1, teamTeachId);
            pstmt.executeUpdate();
            
            result = true;
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

    public static int countTeamMembers(int teamTeachId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int teamMembers = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS teamMembers FROM team_members "
                    + "WHERE TeamTeachID = "+teamTeachId+" ");
            while(rs.next()){
                teamMembers = CommonUtilities.convertStringToInt(rs.getString("teamMembers"));
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
        
        return teamMembers;
    }
    
    public static List<Users> getAllFacultyExceptTeamLeader(int facultyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Users> userList = new ArrayList<Users>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_faculty_view "
                    + "WHERE FacultyStatus = 0 "
                    + "AND FacultyID != "+facultyId+" ");
            while(rs.next()){
                Users u = new Users();
                u.setName(rs.getString("name"));
                u.setUsername_(rs.getString("LoginName"));
                u.setFacultyId(CommonUtilities.convertStringToInt(rs.getString("FacultyID")));
                userList.add(u);
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
        
        return userList;
    }
    
    public static int getFacultyIdByTeamTeachId(int teamTeachId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int facultyId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT FacultyID FROM enrolled_semestral_team_view  "
                    + "WHERE TeamTeachID = "+teamTeachId+" ");
            while(rs.next()){
                facultyId = CommonUtilities.convertStringToInt(rs.getString("FacultyID"));
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
        
        return facultyId;
    }
    
    public static boolean addTeamMember(int teamTeachId, int facultyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO team_members SET "
                    + "TeamTeachID = ?, "
                    + "FacultyID = ? ");
            pstmt.setInt(1, teamTeachId);
            pstmt.setInt(2, facultyId);
            pstmt.executeUpdate();
            
            result = true;
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
    
    public static boolean removeTeamMember(int teamTeachId, int facultyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("DELETE FROM team_members "
                    + "WHERE TeamTeachID = ? "
                    + "AND FacultyID = ? ");
            pstmt.setInt(1, teamTeachId);
            pstmt.setInt(2, facultyId);
            pstmt.executeUpdate();
            
            result = true;
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

    public static boolean isFacultyTeamLeader(int teamTeachId, 
            int facultyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS TeamLead FROM enrolled_semestral_team_view "
                    + "WHERE TeamTeachID = "+teamTeachId+" AND FacultyID = "+facultyId+" ");
            while(rs.next()){
                if(rs.getString("TeamLead").equals("1")){
                    result = true;
                }
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
        
        return result;
    }
    
    public static boolean isTeamMemberAlreadyExist(int teamTeachId, 
            int facultyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS member FROM team_members "
                    + "WHERE TeamTeachID = "+teamTeachId+" AND FacultyID = "+facultyId+" ");
            while(rs.next()){
                if(rs.getString("member").equals("1")){
                    result = true;
                }
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
        
        return result;
    }
    
    public static int getTeamLeaderIdByCurriculumId(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int teamLeaderId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT UserID FROM team_teach "
                    + "WHERE CurriculumID = "+curriculumId+" ");
            while(rs.next()){
                teamLeaderId = CommonUtilities.convertStringToInt(rs.getString("UserID"));
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
        
        return teamLeaderId;
    }
}
