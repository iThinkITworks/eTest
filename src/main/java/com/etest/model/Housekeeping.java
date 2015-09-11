/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

/**
 *
 * @author jetdario
 */
public class Housekeeping extends CellItem {
    
    private int cellItemsArchiveId;
    private int recycled;

    public int getCellItemsArchiveId() {
        return cellItemsArchiveId;
    }

    public int getRecycled() {
        return recycled;
    }

    public void setCellItemsArchiveId(int cellItemsArchiveId) {
        this.cellItemsArchiveId = cellItemsArchiveId;
    }

    public void setRecycled(int recycled) {
        this.recycled = recycled;
    }
    
}
