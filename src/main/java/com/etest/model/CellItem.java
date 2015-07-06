/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class CellItem extends CellCase {
    
    private int cellItemId;
    private int bloomsClassId;
    private String item;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int approveItemStatus;
    private int cellItemStatus;
    private double difficultIndex;
    private double discriminationIndex;
    private Date dateCreated; 
    private int analyze;
    private Map<String, String> itemKeys;

    public int getCellItemId() {
        return cellItemId;
    }

    public int getBloomsClassId() {
        return bloomsClassId;
    }

    public String getItem() {
        return item;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }
    
    public int getApproveItemStatus(){
        return approveItemStatus;
    }

    public int getCellItemStatus() {
        return cellItemStatus;
    }

    public double getDifficultIndex() {
        return difficultIndex;
    }

    public double getDiscriminationIndex() {
        return discriminationIndex;
    }

    public int getAnalyze() {
        return analyze;
    }

    public Map<String, String> getItemKeys() {
        return itemKeys;
    }

    public void setCellItemId(int cellItemId) {
        this.cellItemId = cellItemId;
    }

    public void setBloomsClassId(int bloomsClassId) {
        this.bloomsClassId = bloomsClassId;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public void setApproveItemStatus(int approveItemStatus){
        this.approveItemStatus = approveItemStatus;
    }
    
    public void setCellItemStatus(int cellItemStatus) {
        this.cellItemStatus = cellItemStatus;
    }

    public void setDifficultIndex(double difficultIndex) {
        this.difficultIndex = difficultIndex;
    }

    public void setDiscriminationIndex(double discriminationIndex) {
        this.discriminationIndex = discriminationIndex;
    }

    public void setAnalyze(int analyze) {
        this.analyze = analyze;
    }

    public void setItemKeys(Map<String, String> itemKeys) {
        this.itemKeys = itemKeys;
    }
    
}
