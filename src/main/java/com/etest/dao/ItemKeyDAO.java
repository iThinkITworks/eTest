/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.ItemKeys;
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
public class ItemKeyDAO {
    
    public static boolean addItemKey(int cellItemId, 
            String itemKey, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO item_keys SET "
                    + "CellItemID = ?, "
                    + "ItemKey = ?, "
                    + "Answer = ? ");
            pstmt.setInt(1, cellItemId);
            pstmt.setString(2, itemKey);
            pstmt.setString(3, answer);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static List<String> getAllItemKey(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<String> keyList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKey FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" ");
            while(rs.next()){
                keyList.add(rs.getString("ItemKey"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return keyList;
    }
    
    public static List<ItemKeys> getItemKeysByCellItemId(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<ItemKeys> keyList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" ");
            while(rs.next()){
                ItemKeys k = new ItemKeys();
                k.setItemKeyId(CommonUtilities.convertStringToInt(rs.getString("ItemKeyID")));
                k.setItemKey(rs.getString("ItemKey"));
                k.setAnswer(rs.getString("Answer"));
                keyList.add(k);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return keyList;
    }
    
    public static List<Integer> getItemKeyIdsByCellItemId(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> itemKeyIdList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKeyID FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" ");
            while(rs.next()){
                itemKeyIdList.add(CommonUtilities.convertStringToInt(rs.getString("ItemKeyID")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return itemKeyIdList;
    }
    
    public static String getItemKey(int cellItemId, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        String key = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKey FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND Answer = '"+answer+"' "
                    + "ORDER BY ItemKeyID DESC LIMIT 1");
            while(rs.next()){
                key = rs.getString("ItemKey");
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return key;
    }
    
    public static boolean isKeyExist(int cellItemId, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS itemKey FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND Answer = '"+answer+"' ");
            while(rs.next()){
                if(rs.getString("itemKey").equals("1")){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int getItemKeyId(int cellItemId, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int itemKeyId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKeyID FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND Answer = '"+answer+"' ");
            while(rs.next()){
                itemKeyId = CommonUtilities.convertStringToInt(rs.getString("ItemKeyID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return itemKeyId;
    }
    
    public static boolean modifyItemOption(int cellItemId, 
            String optionColumn, 
            String optionValue, 
            boolean isOptionAKeyExist, 
            int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE cell_items SET "
                    + ""+optionColumn+" = '"+optionValue+"' "
                    + "WHERE cellItemId = "+cellItemId+" ");
            pstmt.executeUpdate();
            
            if(isOptionAKeyExist){
                pstmt = conn.prepareStatement("UPDATE item_keys SET "
                        + "Answer = '"+optionValue+"' "
                        + "WHERE ItemKeyID = "+itemKeyId+" ");
                pstmt.executeUpdate();
            }
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean modifyItemKey(int itemKeyId, 
            int cellItemId, 
            String keyValue, 
            String answer, 
            boolean isOptionKeyExist){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            if(isOptionKeyExist){
                pstmt = conn.prepareStatement("UPDATE item_keys SET "
                        + "ItemKey = '"+keyValue+"' "
                        + "WHERE ItemKeyID = "+itemKeyId+" ");
                pstmt.executeUpdate();
            } else {
                pstmt = conn.prepareStatement("INSERT item_keys SET "
                        + "CellItemID = ?, "
                        + "ItemKey = ?, "
                        + "Answer = ? ");
                pstmt.setInt(1, cellItemId);
                pstmt.setString(2, keyValue);
                pstmt.setString(3, answer);
                pstmt.executeUpdate();
            }
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean removeItemKey(int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("DELETE FROM item_keys "
                    + "WHERE ItemKeyID = "+itemKeyId+" ");
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean isAnswerCorrect(int cellItemId, 
            String key, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS result FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND ItemKey = '"+key+"' "
                    + "AND Answer = '"+answer+"' "
                    + "ORDER BY ItemKeyID DESC LIMIT 1");
            while(rs.next()){
                if(rs.getString("result").equals("1")){
                    result = true;   
                }
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static String getAnswerByItemKeyId(int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        String answer = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Answer FROM item_keys "
                    + "WHERE ItemKeyId = "+itemKeyId+" ");
            while(rs.next()){
                answer = rs.getString("Answer");
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return answer;
    }
}
