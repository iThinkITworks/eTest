/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.CellCaseDAO;
import com.etest.model.CellCase;
import com.etest.service.CellCaseService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class CellCaseServiceImpl implements CellCaseService {

    @Override
    public List<CellCase> getAllCellCase() {
        return CellCaseDAO.getAllCellCase();
    }

    @Override
    public List<CellCase> getCellCaseByTopic(int syllabusId) {
        return CellCaseDAO.getCellCaseByTopic(syllabusId);
    }

    @Override
    public CellCase getCellCaseById(int cellCaseId) {
        return CellCaseDAO.getCellCaseById(cellCaseId);
    }

    @Override
    public List<CellCase> getCellCaseByAuthor(int userId) {
        return CellCaseDAO.getCellCaseByAuthor(userId);
    }

    @Override
    public boolean insertNewCellCase(CellCase cc) {
        return CellCaseDAO.insertNewCellCase(cc);
    }

    @Override
    public boolean modifyCellCase(CellCase cc) {
        return CellCaseDAO.modifyCellCase(cc);
    }

    @Override
    public boolean approveCellCase(int cellCaseId) {
        return CellCaseDAO.approveCellCase(cellCaseId);
    }

    @Override
    public boolean removeCellCase(int cellCaseId) {
        return CellCaseDAO.removeCellCase(cellCaseId);
    }

    @Override
    public boolean isCellCaseApproved(int cellCaseId) {
        return CellCaseDAO.isCellCaseApproved(cellCaseId);
    }
    
}
