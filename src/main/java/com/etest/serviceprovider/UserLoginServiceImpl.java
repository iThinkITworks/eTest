/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.UserLoginDAO;
import com.etest.service.UserLoginService;

/**
 *
 * @author jetdario
 */
public class UserLoginServiceImpl implements UserLoginService {

    @Override
    public boolean loginResult(String username, String password) {
        return UserLoginDAO.loginResult(username, password);
    }
    
}
