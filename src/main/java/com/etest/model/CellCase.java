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
public class CellCase extends Syllabus {
    
    private int cellCaseId;
    private String caseTopic;
    private Date dateCreated;
    private int approvalStatus;
    private String remarks;
    private String actionDone;

    public int getCellCaseId() {
        return cellCaseId;
    }

    public String getCaseTopic() {
        return caseTopic;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getActionDone() {
        return actionDone;
    }

    public void setCellCaseId(int cellCaseId) {
        this.cellCaseId = cellCaseId;
    }

    public void setCaseTopic(String caseTopic) {
        this.caseTopic = caseTopic;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setActionDone(String actionDone) {
        this.actionDone = actionDone;
    }
    
}
