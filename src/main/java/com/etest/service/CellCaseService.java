/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.CellCase;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface CellCaseService {
    
    public List<CellCase> getAllCellCase();
    
    public List<CellCase> getCellCaseByTopic(int syllabusId);
    
    public CellCase getCellCaseById(int cellCaseId);
    
    public List<CellCase> getCellCaseByAuthor(int userId);
    
    public boolean insertNewCellCase(CellCase cc);
    
    public boolean modifyCellCase(CellCase cc);
    
    public boolean approveCellCase(int cellCaseId);
    
    public boolean removeCellCase(int cellCaseId);
    
    public boolean isCellCaseApproved(int cellCaseId);
    
    public CellCase getCellCaseIdByCellItemId(int cellItemId);   
    
    public int getCellCaseAuthorById(int cellCaseId);
}
