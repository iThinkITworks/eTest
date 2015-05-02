/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.Syllabus;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface SyllabusService {
    
    public List<Syllabus> getAllSyllabus();
    
    public Syllabus getSyllabusById(int syllabusId);
    
    public List<Syllabus> getSyllabusByCurriculum(int curriculumId);
    
    public boolean insertNewSyllabus(Syllabus s);
    
    public boolean updateSyllabus(Syllabus s);
    
    public boolean removeSyllabus(int syllabusId);
    
}
