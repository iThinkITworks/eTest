/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.UsersDAO;
import com.etest.service.UsersService;

/**
 *
 * @author jetdario
 */
public class UsersServiceImpl implements UsersService {

    @Override
    public boolean loginResult(String username, String password) {
        return UsersDAO.loginResult(username, password);
    }

    @Override
    public boolean updateUsersColumnValue(String column, 
            String value, 
            int facultyId) {
        return UsersDAO.updateUsersColumnValue(column, 
                value, 
                facultyId);
    }
    
}
