/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.CellItem;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface CellItemService {

    public List<CellItem> getAllCellItem();
    
    public List<CellItem> getCellItemByCase(int cellCaseId);
    
    public CellItem getCellItemById(int cellItemId);
    
    public boolean insertNewCellItem(CellItem ci);
    
    public boolean modifyCellItem(CellItem ci);
    
    public boolean approveCellItem(int cellItemId);
    
    public boolean removeCellItem(int cellItemId);
    
    public List<String> getAllItemKey(int cellItemId);
    
    public boolean addItemKey(int cellItemId, 
            String itemKey, 
            String answer);
    
    public String getItemKey(int cellItemId, 
            String answer);
    
    public boolean isKeyExist(int cellItemId, 
            String answer);
    
    public int getItemKeyId(int cellItemId, 
            String answer);
    
    public boolean modifyItemOption(int cellItemId, 
            String optionColumn, 
            String optionValue, 
            boolean isOptionAKeyExist, 
            int itemKeyId);
    
    public boolean modifyItemKey(int itemKeyId, 
            int cellItemId, 
            String keyValue, 
            String answer, 
            boolean isOptionAKeyExist);
    
    public boolean removeItemKey(int itemKeyId);
    
    public boolean isAnswerCorrect(int cellItemId, 
            String key, 
            String answer);
}
