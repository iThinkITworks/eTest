/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.vaadin.ui.Window;

/**
 *
 * @author jetdario
 */
public class RemoveTeamLeaderWindow extends Window {

    private int teamTeachId;
    
    public RemoveTeamLeaderWindow(int teamTeachId) {
        this.teamTeachId = teamTeachId;
    }
    
    int getTeamTeachId(){
        return teamTeachId;
    }
}
