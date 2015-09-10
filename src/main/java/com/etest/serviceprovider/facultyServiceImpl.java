/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.FacultyDAO;
import com.etest.model.Users;
import com.etest.service.FacultyService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class facultyServiceImpl implements FacultyService {

    @Override
    public List<Users> getAllFaculty() {
        return FacultyDAO.getAllFaculty();
    }

    @Override
    public String getFacultyNameById(int facultyId) {
        return FacultyDAO.getFacultyNameById(facultyId);
    }

    @Override
    public int getFacultyIdByName(String name) {
        return FacultyDAO.getFacultyIdByName(name);
    }

    @Override
    public Users getFacultyInfoById(int facultyId) {
        return FacultyDAO.getFacultyInfoById(facultyId);
    }

    @Override
    public boolean insertNewFaculty(Users users) {
        return FacultyDAO.insertNewFaculty(users);
    }

    @Override
    public boolean updateFaculty(Users users) {
        return FacultyDAO.updateFaculty(users);
    }

    @Override
    public boolean removeFaculty(int facultyId) {
        return FacultyDAO.removeFaculty(facultyId);
    }

    @Override
    public boolean updateFacultyColumnValue(String column, 
            String value, 
            int facultyId) {
        return FacultyDAO.updateFacultyColumnValue(column, 
                value, 
                facultyId);
    }

    @Override
    public String getFacultyPositionById(int facultyId) {
        return FacultyDAO.getFacultyPositionById(facultyId);
    }
   
}
