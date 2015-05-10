/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.service.TeamTeachService;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author jetdario
 */
public class RemoveSemestralTeamWindow extends Window {

    TeamTeachService tts = new TeamTeachServiceImpl();
    private int teamTeachId;
    
    public RemoveSemestralTeamWindow(int teamTeachId) {
        this.teamTeachId = teamTeachId;
        
        setCaption("DELETE WINDOW");
        setWidth("270px");
        setModal(true);
        center();
        
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setSizeFull();
        vlayout.setMargin(true);
        
        Button removeBtn = new Button("REMOVE SEMESTRAL TEAM?");
        removeBtn.setWidth("100%");
        removeBtn.addClickListener((Button.ClickEvent event) -> {
            boolean result = tts.removeSemestralTeam(getTeamTeachId());
            if(result){
                close();
            }
        });
        removeBtn.setImmediate(true);
        vlayout.addComponent(removeBtn);
        
        setContent(vlayout);
        getContent().setHeightUndefined();
    }
    
    int getTeamTeachId(){
        return teamTeachId;
    }
}
