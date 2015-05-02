/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.Faculty;
import com.etest.model.Users;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface FacultyService {
    
    public List<Users> getAllFaculty();
    
    public String getFacultyNameById(int facultyId);
    
    public int getFacultyIdByName(String name);
    
    public Users getFacultyInfoById(int facultyId);
    
    public boolean insertNewFaculty(Users users);
    
    public boolean updateFaculty(Users users);
    
    public boolean removeFaculty(int facultyId);
    
    public boolean updateFacultyColumnValue(String column, 
            String value, 
            int facultyId);
}
