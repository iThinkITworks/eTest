/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import com.etest.connection.DBConnection;
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
public class BloomsTaxonomy {
    
    private int bloomsTaxonomyId;
    private String bloomsTaxonomy;

    public int getBloomsTaxonomyId() {
        return bloomsTaxonomyId;
    }

    public String getBloomsTaxonomy() {
        return bloomsTaxonomy;
    }

    public void setBloomsTaxonomyId(int bloomsTaxonomyId) {
        this.bloomsTaxonomyId = bloomsTaxonomyId;
    }

    public void setBloomsTaxonomy(String bloomsTaxonomy) {
        this.bloomsTaxonomy = bloomsTaxonomy;
    }
    
    public static List<BloomsTaxonomy> getListOfBloomsTaxonomy(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<BloomsTaxonomy> bloomsTaxonomyList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM blooms_taxonomy");
            while(rs.next()){
                BloomsTaxonomy bt = new BloomsTaxonomy();
                bt.setBloomsTaxonomyId(CommonUtilities.convertStringToInt(rs.getString("BloomsClassID")));
                bt.setBloomsTaxonomy(rs.getString("BloomsClass"));
                bloomsTaxonomyList.add(bt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BloomsTaxonomy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {            
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BloomsTaxonomy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return bloomsTaxonomyList;
    }
}
