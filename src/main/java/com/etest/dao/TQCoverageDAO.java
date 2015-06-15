/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.BloomsTaxonomy;
import com.etest.model.CellItem;
import com.etest.model.ItemKeys;
import com.etest.model.Syllabus;
import com.etest.service.SyllabusService;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.ui.Grid;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class TQCoverageDAO {
    
    SyllabusService ss = new SyllabusServiceImpl();
    
    public static int getBloomsClassId(String bloomsClass){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int bloomsClassId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT BloomsClassID FROM blooms_taxonomy "
                    + "WHERE BloomsClass = '"+bloomsClass+"' ");
            while(rs.next()){
                bloomsClassId = CommonUtilities.convertStringToInt(rs.getString("BloomsClassID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return bloomsClassId;
    }
    
    public static List<CellItem> getItemIdByDiscriminationIndex(Grid grid){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellItem> cellItemIdList = new ArrayList<>();
//        List<BloomsTaxonomy> bloomsClassIdList = BloomsClassDAO.
        
        try {
            int syllabusId = 0;
            Collection c = grid.getContainerDataSource().getItemIds();
            Iterator iterator = c.iterator();
            while (iterator.hasNext()) {
                Item item = grid.getContainerDataSource().getItem(iterator.next());
                Collection x = item.getItemPropertyIds();
                Iterator it = x.iterator();
                Object propertyId;
                while (it.hasNext()) {
                    propertyId = it.next();
                    if(propertyId.toString().equals("Topic")){
                        syllabusId = SyllabusDAO.getSyllabusIdByTopic(item.getItemProperty(propertyId).getValue().toString());
                    }
                    
                    if(propertyId.toString().contains("Pick")){
                        if(propertyId.toString().equals("Re-U(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 1 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Re-A(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 1 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Un-U(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 2 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Un-A(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 2 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Ap-U(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 3 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        if(propertyId.toString().equals("Ap-A(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 3 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("An-U(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 4 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("An-A(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 4 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Ev-U(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 5 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Ev-A(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 5 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Cr-U(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 6 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                        
                        if(propertyId.toString().equals("Cr-A(Pick)")){
                            if(item.getItemProperty(propertyId).getValue() != null){
                                int limit = CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT "
                                        + "s.SyllabusID AS SyllabusID, "
                                        + "ci.CellItemId AS CellItemID "
                                        + "FROM syllabus s "
                                        + "INNER JOIN cell_cases cc ON s.SyllabusID = cc.SyllabusID "
                                        + "INNER JOIN cell_items ci ON cc.CellCaseID = ci.CellCaseID "
                                        + "WHERE ci.CellItemStatus = 0 "
                                        + "AND s.SyllabusID = '"+syllabusId+"' "
                                        + "AND BloomsClassID = 6 "
                                        + "LIMIT  "+limit+" ");
                                while(rs.next()){
                                    CellItem ci = new CellItem();
                                    ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                                    cellItemIdList.add(ci);
                                }
                            }
                        }
                    }                
                }            
            }            
        } catch (SQLException ex) {
            Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return cellItemIdList;
    }
}
