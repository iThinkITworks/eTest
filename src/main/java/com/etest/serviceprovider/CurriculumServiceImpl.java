/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.CurriculumDAO;
import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class CurriculumServiceImpl implements CurriculumService {

    @Override
    public boolean insertNewCurriculum(Curriculum curriculum) {
        return CurriculumDAO.insertNewCurriculum(curriculum);
    }

    @Override
    public boolean updateCurriculum(Curriculum curriculum) {
        return CurriculumDAO.updateCurriculum(curriculum);
    }

    @Override
    public List<Curriculum> getAllCurriculum() {
        return CurriculumDAO.getAllCurriculum();
    }

    @Override
    public boolean removeCurriculum(int curriculumId) {
        return CurriculumDAO.removeCurriculum(curriculumId);
    }

    @Override
    public Curriculum getCurriculumById(int curriculumId) {
        return CurriculumDAO.getCurriculumById(curriculumId);
    }
    
}
