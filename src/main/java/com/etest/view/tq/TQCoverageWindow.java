/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.service.SyllabusService;
import com.etest.service.TQCoverageService;
import com.etest.service.TeamTeachService;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author jetdario
 */
public class TQCoverageWindow extends Window {

    SyllabusService ss = new SyllabusServiceImpl();
    TeamTeachService tts = new TeamTeachServiceImpl();
    TQCoverageService tq = new TQCoverageServiceImpl();
    
    Grid grid = new Grid();
    Label caseTopic = new Label();
    Label stem = new Label();
    
    private int curriculumId;
    private int syllabusId;
    private int teamTeachId;
    private int totalTestItems;
    
    public TQCoverageWindow(Grid grid, 
            int curriculumId, 
            int totalTestItems){
        this.grid = grid;
        this.curriculumId = curriculumId;
        this.totalTestItems = totalTestItems;
        
        setCaption("TEST COVERAGE");
        setWidth("800px");
        setModal(true);
        center();        
        
        teamTeachId = tts.getTeamTeachIdByUserId(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
        
        System.out.println("user id: "+VaadinSession.getCurrent().getAttribute("userId"));
        System.out.println("team teach id: "+getTeamTeachId());
        System.out.println("curriculum id: "+getCurriculumId());
        System.out.println("total Hrs Coverage: "+tq.calculateTotalHourSpent(getTQCoverageGrid()));
        System.out.println("total test items: "+getTotalTestItems());
        
        setContent(buildForms());
        getContent().setHeightUndefined();
    }

    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        form.setSpacing(true);
        
        Collection c = getTQCoverageGrid().getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            Item item = getTQCoverageGrid().getContainerDataSource().getItem(iterator.next());
            Collection x = item.getItemPropertyIds();
            Iterator it = x.iterator();
            Object propertyId;
            while (it.hasNext()) {
                propertyId = it.next();
                if(propertyId.toString().contains("Pick")){
                    System.out.println("propertyId: "+item.getItemProperty(propertyId).getValue());
                }                
            }            
        }
        
        caseTopic.setValue("<i>"+""+"</i>");
        caseTopic.setContentMode(ContentMode.HTML);
        form.addComponent(caseTopic);
        
        stem.setValue(" ");
        stem.setContentMode(ContentMode.HTML);
        form.addComponent(stem);
        
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setWidth("100%");
        
        
        return form;
    }
    
    Grid getTQCoverageGrid(){
        return grid;
    }
    
    int getCurriculumId(){
        return curriculumId;
    }
    
    int getSyllabusId(){
        return syllabusId;
    }
    
    int getTeamTeachId(){
        return teamTeachId;
    }
    
    int getTotalTestItems(){
        return totalTestItems;
    }
}
