/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

/**
 *
 * @author jetdario
 */
public interface ReportService {
    
    public int getTotalCases();
    
    public int getTotalItems();
    
    public int getTotalCasesBySubject(int curriculumId);
    
    public int getTotalItemsBySubject(int curriculumId);
    
    public int getTotalItemsByBloomsCass(int curriculumId, int bloomsClassId);
}
