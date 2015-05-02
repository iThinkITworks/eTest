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
public interface UsersService {
    
    public boolean loginResult(String username, String password);
    
    public boolean updateUsersColumnValue(String column, 
            String value, 
            int facultyId);
    
}
