/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.InventoryOfCasesReport;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface InventoryOfCasesReportService {
    
    public List<InventoryOfCasesReport> getInventoryOfCases();
    
    public List<Integer> getListOfSyllabusIdByCurriculumId(int curriculumId);
    
    public int getTotalCellCasesBySyllabus(List<Integer> syllabusIdList);
    
    public int getTotalCellItemsByCellCaseId(List<Integer> cellCaseIdList);
    
    public List<Integer> getListOfCellCaseIdBySyllabusId(List<Integer> syllabusIdList);
}
