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
public class EtestNotification extends Users {
    
    private int notificationId;
    private int senderId;
    private String notice;
    private String remarks;
    private Date noteDate;
    private int status;

    public int getNotificationId() {
        return notificationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getNotice() {
        return notice;
    }

    public String getRemarks() {
        return remarks;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public int getStatus() {
        return status;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
