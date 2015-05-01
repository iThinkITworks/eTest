/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.UsersLoginDAO;
import com.etest.service.UsersLoginService;

/**
 *
 * @author jetdario
 */
public class UsersLoginServiceImpl implements UsersLoginService {

    @Override
    public boolean loginResult(String username, String password) {
        return UsersLoginDAO.loginResult(username, password);
    }
    
}
