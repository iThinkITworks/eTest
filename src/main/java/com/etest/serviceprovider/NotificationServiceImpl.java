/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.dao.CellCaseDAO;
import com.etest.model.EtestNotification;
import com.etest.service.NotificationService;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.VaadinSession;
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
public class NotificationServiceImpl implements NotificationService {

    @Override
    public boolean insertNotification(EtestNotification en) {
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO notifications "
                    + "SET UserID = ?, "
                    + "SenderID = ?, "
                    + "Notice = ?, "
                    + "Remarks = ?, "
                    + "NoteDate = now(), "
                    + "Status = ? ");
            pstmt.setInt(1, en.getUserId());
            pstmt.setInt(2, en.getSenderId());
            pstmt.setString(3, en.getNotice());
            pstmt.setString(4, en.getRemarks());
            pstmt.setInt(5, 1);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }

    @Override
    public List<EtestNotification> getAllNotificationByUser(int userId) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<EtestNotification> notificationList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM notifications "
                    + "WHERE UserID = "+userId+" ");
            while(rs.next()){
                EtestNotification en = new EtestNotification();
                en.setNotificationId(CommonUtilities.convertStringToInt(rs.getString("NotificationID")));
                en.setUserId(CommonUtilities.convertStringToInt(rs.getString("UserID")));
                en.setSenderId(CommonUtilities.convertStringToInt(rs.getString("SenderID")));
                en.setNotice(rs.getString("Notice"));
                en.setRemarks(rs.getString("Remarks"));
                en.setNoteDate(CommonUtilities.parsingDateWithTime(rs.getString("NoteDate")));
                en.setStatus(CommonUtilities.convertStringToInt(rs.getString("Status")));
                notificationList.add(en);                
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return notificationList;
    }

    @Override
    public EtestNotification viewNotificationById(int notificationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void isReadNotification(int notificationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void isReplyNotification(int notificationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int totalUnreadNotification(int userId) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM notifications "
                    + "WHERE UserID = "+userId+" "
                    + "AND Status = 0");
            while(rs.next()){
                count = CommonUtilities.convertStringToInt(rs.getString("total"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return count;
    }

    @Override
    public EtestNotification getNotificationById(int notificationId) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        EtestNotification en = new EtestNotification();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM notifications "
                    + "WHERE NotificationID = "+notificationId+" ");
            while(rs.next()){
                en.setNotificationId(CommonUtilities.convertStringToInt(rs.getString("NotificationID")));
                en.setUserId(CommonUtilities.convertStringToInt(rs.getString("UserID")));
                en.setSenderId(CommonUtilities.convertStringToInt(rs.getString("SenderID")));
                en.setNotice(rs.getString("Notice"));
                en.setRemarks(rs.getString("Remarks"));
                en.setNoteDate(CommonUtilities.parsingDateWithTime(rs.getString("NoteDate")));
                en.setStatus(CommonUtilities.convertStringToInt(rs.getString("Status")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return en;
    }

    @Override
    public boolean updateNoficationStatus(int notificationId) {
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE notifications SET "
                    + "Status = ? "
                    + "WHERE NotificationID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, notificationId);
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
    
}
