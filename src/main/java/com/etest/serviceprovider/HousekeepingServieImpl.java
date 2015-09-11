/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.EtestNotification;
import com.etest.model.Housekeeping;
import com.etest.service.HousekeepingService;
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
public class HousekeepingServieImpl implements HousekeepingService {

    @Override
    public List<Housekeeping> getAllItemsFromArchive() {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Housekeeping> housekeepingList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cell_items_archive");
            while(rs.next()){
                Housekeeping h = new Housekeeping();
                h.setCellItemsArchiveId(CommonUtilities.convertStringToInt(rs.getString("CellItemArchiveID")));
                h.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                h.setItem(rs.getString("Item"));
                h.setOptionA(rs.getString("OptionA"));
                h.setOptionB(rs.getString("OptionB"));
                h.setOptionC(rs.getString("OptionC"));
                h.setOptionD(rs.getString("OptionD"));
                housekeepingList.add(h);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(HousekeepingServieImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(HousekeepingServieImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return housekeepingList;
    }
    
}
