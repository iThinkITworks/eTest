/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.CellItem;
import com.etest.model.TQAnswerKey;
import com.etest.model.TQCoverage;
import com.etest.model.TQItems;
import com.etest.model.TopicCoverage;
import com.etest.service.SyllabusService;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.ui.Grid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    
    public static boolean insertNewTQCoverage(TopicCoverage coverage, 
            TQItems items, 
            Map<Integer, List<TQAnswerKey>> cellCaseItemKey, 
            Grid grid) {
        Connection conn = DBConnection.connectToDB();
        PreparedStatement tqCoverage = null;
        PreparedStatement topicCoverage = null;
        PreparedStatement tqCases = null;
        PreparedStatement tqItems = null;
        PreparedStatement tqAnswerKey = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            tqCoverage = conn.prepareStatement("INSERT INTO tq_coverage SET "
                    + "ExamTitle = ?, "
                    + "CurriculumID = ?, "
                    + "TeamTeachID = ?, "
                    + "DateCreated = now(), "
                    + "TotalHoursCoverage = ?, "
                    + "TotalItems = ? ");
            tqCoverage.setString(1, coverage.getExamTitle());
            tqCoverage.setInt(2, coverage.getCurriculumId());
            tqCoverage.setInt(3, coverage.getTeamTeachId());
            tqCoverage.setDouble(4, coverage.getTotalHoursCoverage());
            tqCoverage.setInt(5, coverage.getTotalItems());
            tqCoverage.executeUpdate();
            
            int tqCoverageId = 0;            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id() AS TqCoverageID FROM tq_coverage ");
            while(rs.next()){
                tqCoverageId = CommonUtilities.convertStringToInt(rs.getString("TqCoverageID"));
            }
            
            Collection c = grid.getContainerDataSource().getItemIds();
            Iterator iterator = c.iterator();
            while(iterator.hasNext()){
                Item item = grid.getContainerDataSource().getItem(iterator.next());                
                topicCoverage = conn.prepareStatement("INSERT INTO topic_coverage SET "
                        + "TqCoverageID = ?, "
                        + "SyllabusID = ?, "
                        + "HoursSpent = ?, "
                        + "Proportion = ?, "
                        + "RememberU = ?, "
                        + "RememberA = ?, "
                        + "UnderstandU = ?, "
                        + "UnderstandA = ?, "
                        + "ApplyU = ?, "
                        + "ApplyA = ?, "
                        + "AnalyzeU = ?, "
                        + "AnalyzeA = ?, "
                        + "EvaluateU = ?, "
                        + "EvaluateA = ?, "
                        + "CreateU = ?, "
                        + "CreateA = ?, "
                        + "TotalItemsRequired = ? ");
                topicCoverage.setInt(1, tqCoverageId);
                topicCoverage.setInt(2, coverage.getSyllabusId());
                topicCoverage.setDouble(3, (double) item.getItemProperty("Hrs Spent").getValue());
                topicCoverage.setDouble(4, (double) item.getItemProperty("Proportion(%)").getValue());
                topicCoverage.setInt(5,  (item.getItemProperty("Re-U(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Re-U(Pick)").getValue());
                topicCoverage.setInt(6,  (item.getItemProperty("Re-A(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Re-A(Pick)").getValue());
                topicCoverage.setInt(7,  (item.getItemProperty("Un-U(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Un-U(Pick)").getValue());
                topicCoverage.setInt(8,  (item.getItemProperty("Un-A(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Un-A(Pick)").getValue());
                topicCoverage.setInt(9,  (item.getItemProperty("Ap-U(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Ap-U(Pick)").getValue());
                topicCoverage.setInt(10,  (item.getItemProperty("Ap-A(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Ap-A(Pick)").getValue());
                topicCoverage.setInt(11,  (item.getItemProperty("An-U(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("An-U(Pick)").getValue());
                topicCoverage.setInt(12,  (item.getItemProperty("An-A(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("An-A(Pick)").getValue());
                topicCoverage.setInt(13,  (item.getItemProperty("Ev-U(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Ev-U(Pick)").getValue());
                topicCoverage.setInt(14,  (item.getItemProperty("Ev-A(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Ev-A(Pick)").getValue());
                topicCoverage.setInt(15,  (item.getItemProperty("Cr-U(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Cr-U(Pick)").getValue());
                topicCoverage.setInt(16,  (item.getItemProperty("Cr-A(Pick)").getValue() == null) ? 0 : (int)item.getItemProperty("Cr-A(Pick)").getValue());
                topicCoverage.setDouble(17, (double) item.getItemProperty("Max Items").getValue());
                topicCoverage.executeUpdate();
            }
            
            Map<Integer, List<TQAnswerKey>> testMap = cellCaseItemKey;
            for (Map.Entry<Integer, List<TQAnswerKey>> entrySet : testMap.entrySet()) {
                Object cellCaseId = entrySet.getKey();
                
                tqCases = conn.prepareStatement("INSERT INTO tq_cases SET "
                    + "TqCoverageID = "+tqCoverageId+", "
                    + "CellCaseID = "+cellCaseId+" ");
                tqCases.executeUpdate();
                
                int tqCaseId = 0;            
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT last_insert_id() AS TqCaseId FROM tq_cases ");
                while(rs.next()){
                    tqCaseId = CommonUtilities.convertStringToInt(rs.getString("TqCaseId"));
                }
                
                List<TQAnswerKey> answerKeyList = entrySet.getValue();
                for(TQAnswerKey t : answerKeyList){
                    tqItems = conn.prepareStatement("INSERT INTO tq_items SET "
                        + "TqCaseID = ?, "
                        + "CellItemID = ?, "
                        + "ItemKeyID = ? ");
                    tqItems.setInt(1, tqCaseId);
                    tqItems.setInt(2, t.getCellItemId());
                    tqItems.setInt(3, t.getItemKeyId());
                    tqItems.executeUpdate();
                    
                    int tqItemId = 0;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT last_insert_id() AS TqCaseId FROM tq_cases ");
                    while(rs.next()){
                        tqItemId = CommonUtilities.convertStringToInt(rs.getString("TqCaseId"));
                    }
                    
                    tqAnswerKey = conn.prepareStatement("INSERT INTO tq_answer_key SET "
                            + "TqItemID = ?, "
                            + "ItemNo = ?, "
                            + "Answer = ? ");
                    tqAnswerKey.setInt(1, tqItemId);
                    tqAnswerKey.setInt(2, t.getItemNo());
                    tqAnswerKey.setString(3, t.getAnswer());
                    tqAnswerKey.executeUpdate();
                }
            }            
                        
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                tqCoverage.close();
                topicCoverage.close();
                tqCases.close();
                tqItems.close();
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static List<TQCoverage> getAllTQCoverage(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<TQCoverage> tqCoverageList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM tq_coverage ");
            while(rs.next()){
                TQCoverage tqc = new TQCoverage();
                tqc.setTqCoverageId(CommonUtilities.convertStringToInt(rs.getString("TqCoverageID")));
                tqc.setExamTitle(rs.getString("ExamTitle"));
                tqc.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                tqc.setDateCreated(CommonUtilities.parsingDateWithTime(rs.getString("DateCreated")));
                tqc.setTotalHoursCoverage(CommonUtilities.convertStringToDouble(rs.getString("TotalHoursCoverage")));
                tqc.setTotalItems(CommonUtilities.convertStringToInt(rs.getString("TotalItems")));
                tqc.setStatus(CommonUtilities.convertStringToInt(rs.getString("Status")));
                tqCoverageList.add(tqc);
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
        
        return tqCoverageList;
    }

    public static Map<Integer, Map<Integer, Integer>> getTQCoverage(int tqCoverageId){
        Connection conn = DBConnection.connectToDB();
        Statement cases = null;
        Statement items = null;
        ResultSet rsCases = null;
        ResultSet rsItems = null;
        Map<Integer, Map<Integer, Integer>> tqCoverage = new HashMap<>();
        Map<Integer, Integer> itemsAndKeys;
        int tqCaseId;
        
        try {
            cases = conn.createStatement();
            rsCases = cases.executeQuery("SELECT TQCaseID, CellCaseID FROM tq_cases "
                    + "WHERE TqCoverageID = "+tqCoverageId+" ");
            while(rsCases.next()){
//                tqCaseId = CommonUtilities.convertStringToInt(rsCases.getString("TqCaseID"));
                itemsAndKeys = new HashMap<>();
                items = conn.createStatement();
                rsItems = items.executeQuery("SELECT CellItemID, ItemKeyID FROM tq_items "
                        + "WHERE TqCaseID = "+CommonUtilities.convertStringToInt(rsCases.getString("TqCaseID"))+" ");
                while(rsItems.next()){
                    itemsAndKeys.put(CommonUtilities.convertStringToInt(rsItems.getString("CellItemID")), 
                            CommonUtilities.convertStringToInt(rsItems.getString("ItemKeyID")));
                }
                tqCoverage.put(CommonUtilities.convertStringToInt(rsCases.getString("CellCaseID")), itemsAndKeys);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cases.close();
                items.close();
                rsCases.close();
                rsItems.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return tqCoverage;
    }

    public static boolean approveTQCoverage(int tqCoverageId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE tq_coverage "
                    + "SET Status = 1 "
                    + "WHERE TqCoverageID = "+tqCoverageId+" ");
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }

    public static boolean isTQCoverageApproved(int tqCoverageId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Status FROM tq_coverage "
                    + "WHERE TqCoverageID = "+tqCoverageId+" ");
            while(rs.next()){
                if(rs.getString("Status").equals("1")){
                    result = true;
                }
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
        
        return result;
    }
    
    public static boolean deleteTQCoverage(int tqCoverageId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("DELETE FROM tq_coverage "
                    + "WHERE TqCoverageID = "+tqCoverageId+" ");
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(TQCoverageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static List<TQAnswerKey> getTQCoverageAnswerKey(int tqCoverageId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<TQAnswerKey> tqAnswerKeyList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CellItemID, ItemNo, Answer FROM tq_coverage_answer "
                    + "WHERE TqCoverageID = "+tqCoverageId+" "
                    + "ORDER BY ItemNo ASC ");
            while(rs.next()){
                TQAnswerKey  t = new TQAnswerKey();
                t.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                t.setItemNo(CommonUtilities.convertStringToInt(rs.getString("ItemNo")));
                t.setAnswer(CommonUtilities.escapeSingleQuote(rs.getString("Answer")));
                tqAnswerKeyList.add(t);
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
        
        return tqAnswerKeyList;
    }
}
