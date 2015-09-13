/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.SystemLogs;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface SystemLogService {
    
    public List<SystemLogs> getAllLogActivity();
    
}
