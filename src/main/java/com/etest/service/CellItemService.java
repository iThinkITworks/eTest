/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.CellItem;
import com.etest.model.ItemKeys;
import java.util.List;
import java.util.Map;

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
    
    public int getTotalCellItemByTopic(int bloomsClassId);
    
    public int getTotalUnanalyzeItem(int syllabusId, 
            int bloomsClassId);
    
    public int getTotalAnalyzeItem(int syllabusId, 
            int bloomsClassId);
    
    public Map<String, Character> getOptionAnswer(int cellItemId);
}
