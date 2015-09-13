/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.connection.DBConnection;
import com.etest.dao.UsersDAO;
import com.etest.service.UsersService;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.VaadinSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class UsersServiceImpl implements UsersService {

    @Override
    public boolean loginResult(String username, String password) {
        return UsersDAO.loginResult(username, password);
    }

    @Override
    public boolean updateUsersColumnValue(String column, 
            String value, 
            int facultyId) {
        return UsersDAO.updateUsersColumnValue(column, 
                value, 
                facultyId);
    }

    @Override
    public int getUserIdByFacultyId(int facultyId) {
        return UsersDAO.getUserIdByFacultyId(facultyId);
    }

    @Override
    public String getUsernameById(int userId) {
        return UsersDAO.getUsernameById(userId);
    }

    @Override
    public void logout() {
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        
        try {            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Logged Out!");
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
