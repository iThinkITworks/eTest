/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.dao.CellItemDAO;
import com.etest.model.CellItem;
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
    public List<CellItem> getAllItemsFromArchive() {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellItem> cellItemList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cell_items_archive");
            while(rs.next()){
                CellItem ci = new CellItem();
                ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                ci.setItem(rs.getString("Item"));
                ci.setOptionA(rs.getString("OptionA"));
                ci.setOptionB(rs.getString("OptionB"));
                ci.setOptionC(rs.getString("OptionC"));
                ci.setOptionD(rs.getString("OptionD"));
                cellItemList.add(ci);
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
        
        return cellItemList;
    }

    @Override
    public CellItem getCellItemById(int cellItemId) {
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        Statement innerStmt = null;
        ResultSet rs = null;
        ResultSet innerRs = null;
        CellItem ci = new CellItem();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cell_items_archive "
                    + "WHERE CellItemStatus = 0 "
                    + "AND CellItemID = "+cellItemId+" ");
            while(rs.next()){                
                ci.setCellCaseId(CommonUtilities.convertStringToInt(rs.getString("CellCaseID")));
                ci.setBloomsClassId(CommonUtilities.convertStringToInt(rs.getString("BloomsClassID")));
                ci.setItem(CommonUtilities.escapeSingleQuote(rs.getString("Item")));
                ci.setOptionA(CommonUtilities.escapeSingleQuote(rs.getString("OptionA")));
                ci.setOptionB(CommonUtilities.escapeSingleQuote(rs.getString("OptionB")));
                ci.setOptionC(CommonUtilities.escapeSingleQuote(rs.getString("OptionC")));
                ci.setOptionD(CommonUtilities.escapeSingleQuote(rs.getString("OptionD")));
                ci.setApproveItemStatus(CommonUtilities.convertStringToInt(rs.getString("ApproveStatus")));
                ci.setDifficultIndex(CommonUtilities.convertStringToDouble(rs.getString("DifficultIndex")));
                ci.setDiscriminationIndex(CommonUtilities.convertStringToDouble(rs.getString("DiscriminationIndex")));
                ci.setUserId(CommonUtilities.convertStringToInt(rs.getString("AuthoredBy_UserID")));
                ci.setDateCreated(CommonUtilities.parsingDateWithTime(rs.getString("DateCreated")));                
                ci.setCellItemStatus(CommonUtilities.convertStringToInt(rs.getString("CellItemStatus")));
                ci.setAnalyze(CommonUtilities.convertStringToInt(rs.getString("Analyzed")));
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
        
        return ci;
    }
    
}
