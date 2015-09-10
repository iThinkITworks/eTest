/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.EtestNotification;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface NotificationService {
        
    public boolean insertNotification(EtestNotification en);
    
    public List<EtestNotification> getAllNotificationByUser(int userId);
    
    public EtestNotification viewNotificationById(int notificationId);
    
    public void isReadNotification(int notificationId);
    
    public void isReplyNotification(int notificationId);
    
    public int totalUnreadNotification(int userId);
}
