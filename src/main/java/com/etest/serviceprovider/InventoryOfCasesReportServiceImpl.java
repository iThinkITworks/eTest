/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.ReportsDAO;
import com.etest.model.InventoryOfCasesReport;
import com.etest.service.InventoryOfCasesReportService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class InventoryOfCasesReportServiceImpl implements InventoryOfCasesReportService {

    @Override
    public List<InventoryOfCasesReport> getInventoryOfCases() {
        return ReportsDAO.getInventoryOfCases();
    }

    @Override
    public List<Integer> getListOfSyllabusIdByCurriculumId(int curriculumId) {
        return ReportsDAO.getListOfSyllabusIdByCurriculumId(curriculumId);
    }

    @Override
    public int getTotalCellCasesBySyllabus(List<Integer> syllabusIdList) {
        return ReportsDAO.getTotalCellCasesBySyllabus(syllabusIdList);
    }

    @Override
    public int getTotalCellItemsByCellCaseId(List<Integer> cellCaseIdList) {
        return ReportsDAO.getTotalCellItemsByCellCaseId(cellCaseIdList);
    }

    @Override
    public List<Integer> getListOfCellCaseIdBySyllabusId(List<Integer> syllabusIdList) {
        return ReportsDAO.getListOfCellCaseIdBySyllabusId(syllabusIdList);
    }
    
}
