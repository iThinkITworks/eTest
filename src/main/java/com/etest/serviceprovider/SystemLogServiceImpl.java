/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.SystemLogs;
import com.etest.service.SystemLogService;
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
public class SystemLogServiceImpl implements SystemLogService {

    @Override
    public List<SystemLogs> getAllLogActivity() {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<SystemLogs> systemLogsList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM system_logs "
                    + "ORDER BY SystemLogID DESC");
            while(rs.next()){
                SystemLogs s = new SystemLogs();
                s.setSystemLogId(CommonUtilities.convertStringToInt(rs.getString("SystemLogID")));
                s.setUserId(CommonUtilities.convertStringToInt(rs.getString("UserID")));
                s.setEntryDateTime(CommonUtilities.parsingDateWithTime(rs.getString("EntryDateTime")));
                s.setActivity(rs.getString("Activity"));
                systemLogsList.add(s);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SystemLogServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SystemLogServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return systemLogsList;
    }
    
}
