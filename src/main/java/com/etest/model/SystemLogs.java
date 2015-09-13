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
public class SystemLogs extends Users {
    
    private int systemLogId;
    private Date entryDateTime;
    private String activity;

    public int getSystemLogId() {
        return systemLogId;
    }

    public Date getEntryDateTime() {
        return entryDateTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setSystemLogId(int systemLogId) {
        this.systemLogId = systemLogId;
    }

    public void setEntryDateTime(Date entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    
}
