/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.SyllabusDAO;
import com.etest.model.Syllabus;
import com.etest.service.SyllabusService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class SyllabusServiceImpl implements SyllabusService {

    @Override
    public List<Syllabus> getAllSyllabus() {
        return SyllabusDAO.getAllSyllabus();
    }

    @Override
    public Syllabus getSyllabusById(int syllabusId) {
        return SyllabusDAO.getSyllabusById(syllabusId);
    }

    @Override
    public List<Syllabus> getSyllabusByCurriculum(int curriculumId) {
        return SyllabusDAO.getSyllabusByCurriculum(curriculumId);
    }

    @Override
    public boolean insertNewSyllabus(Syllabus s) {
        return SyllabusDAO.insertNewSyllabus(s);
    }

    @Override
    public boolean updateSyllabus(Syllabus s) {
        return SyllabusDAO.updateSyllabus(s);
    }

    @Override
    public boolean removeSyllabus(int syllabusId) {
        return SyllabusDAO.removeSyllabus(syllabusId);
    }
    
}
