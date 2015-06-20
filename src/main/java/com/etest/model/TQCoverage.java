/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

import java.util.Date;

/**
 *
 * @author jetdario
 */
public class TQCoverage extends TeamTeach {
    
    private int TqCoverageId;
    private String examTitle;
    private Date dateCreated;
    private double totalHoursCoverage;
    private int totalItems;
    private int status;

    public int getTqCoverageId() {
        return TqCoverageId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public double getTotalHoursCoverage() {
        return totalHoursCoverage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getStatus() {
        return status;
    }

    public void setTqCoverageId(int TqCoverageId) {
        this.TqCoverageId = TqCoverageId;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTotalHoursCoverage(double totalHoursCoverage) {
        this.totalHoursCoverage = totalHoursCoverage;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
