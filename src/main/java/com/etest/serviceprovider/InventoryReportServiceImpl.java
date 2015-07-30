/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.InventoryReportDAO;
import com.etest.model.InventoryOfCasesReport;
import com.etest.service.InventoryReportService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class InventoryReportServiceImpl implements InventoryReportService {

    @Override
    public List<InventoryOfCasesReport> getInventoryOfCases() {
        return InventoryReportDAO.getInventoryOfCases();
    }

    @Override
    public List<Integer> getListOfSyllabusIdByCurriculumId(int curriculumId) {
        return InventoryReportDAO.getListOfSyllabusIdByCurriculumId(curriculumId);
    }

    @Override
    public int getTotalCellCasesBySyllabus(List<Integer> syllabusIdList) {
        return InventoryReportDAO.getTotalCellCasesBySyllabus(syllabusIdList);
    }

    @Override
    public int getTotalCellItemsByCellCaseId(List<Integer> cellCaseIdList) {
        return InventoryReportDAO.getTotalCellItemsByCellCaseId(cellCaseIdList);
    }

    @Override
    public List<Integer> getListOfCellCaseIdBySyllabusId(List<Integer> syllabusIdList) {
        return InventoryReportDAO.getListOfCellCaseIdBySyllabusId(syllabusIdList);
    }

    @Override
    public int getTotalItemsByBloomsTaxonomy(List<Integer> cellCaseIdList, 
            int bloomsClassId) {
        return InventoryReportDAO.getTotalItemsByBloomsTaxonomy(cellCaseIdList, 
                bloomsClassId);
    }
    
}
