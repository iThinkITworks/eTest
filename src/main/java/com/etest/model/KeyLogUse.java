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
public class KeyLogUse extends ItemKeys {
    
    private int keyLogUseId;
    private Date dateUsed;
    private int tqCoverageId;
    private int usedItemKey;

    public int getKeyLogUseId() {
        return keyLogUseId;
    }

    public Date getDateUsed() {
        return dateUsed;
    }

    public int getTqCoverageId() {
        return tqCoverageId;
    }

    public int getUsedItemKey() {
        return usedItemKey;
    }

    public void setKeyLogUseId(int keyLogUseId) {
        this.keyLogUseId = keyLogUseId;
    }

    public void setDateUsed(Date dateUsed) {
        this.dateUsed = dateUsed;
    }

    public void setTqCoverageId(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
    }

    public void setUsedItemKey(int usedItemKey) {
        this.usedItemKey = usedItemKey;
    }
    
}
