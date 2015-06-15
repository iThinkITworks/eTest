/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.global.ShowErrorNotification;
import com.etest.model.CellItem;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.service.ItemKeyService;
import com.etest.service.SyllabusService;
import com.etest.service.TQCoverageService;
import com.etest.service.TeamTeachService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.ItemKeyServiceImpl;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class TQCoverageWindow extends Window {

    SyllabusService ss = new SyllabusServiceImpl();
    TeamTeachService tts = new TeamTeachServiceImpl();
    TQCoverageService tq = new TQCoverageServiceImpl();
    CellCaseService ccs = new CellCaseServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    ItemKeyService k = new ItemKeyServiceImpl();
    
    Grid grid = new Grid();
//    Label caseTopic = new Label();
//    Label stem = new Label();
    
    private int curriculumId;
    private int syllabusId;
    private int teamTeachId;
    private int totalTestItems;
    List<Integer> CellItemIdList = new ArrayList<>();
    
    public TQCoverageWindow(Grid grid, 
            int curriculumId, 
            int totalTestItems){
        this.grid = grid;
        this.curriculumId = curriculumId;
        this.totalTestItems = totalTestItems;
        
        setCaption("TEST COVERAGE");
        setWidth("800px");
        setHeight("100%");
        setModal(true);
        center();        
        
        teamTeachId = tts.getTeamTeachIdByUserId(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
        
        System.out.println("user id: "+VaadinSession.getCurrent().getAttribute("userId"));
        System.out.println("team teach id: "+getTeamTeachId());
        System.out.println("curriculum id: "+getCurriculumId());
        System.out.println("total Hrs Coverage: "+tq.calculateTotalHourSpent(getTQCoverageGrid()));
        System.out.println("total test items: "+getTotalTestItems());
        
        setContent(buildForms());
//        getContent().setHeightUndefined();
    }

    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        form.setSpacing(true);
        
        List<CellItem> cellItemIdList = tq.getItemIdByDiscriminationIndex(grid);
        int i = 1;        
        for(CellItem ci : cellItemIdList){   
            
            Label caseTopic = new Label();
            caseTopic.setValue(ccs.getCellCaseById(ccs.getCellCaseIdByCellItemId(ci.getCellItemId()).getCellCaseId()).getCaseTopic());
            caseTopic.setContentMode(ContentMode.HTML);                        
            form.addComponent(caseTopic);

            List<String> keyList = k.getAllItemKey(ci.getCellItemId());
            if(keyList.isEmpty()){
                System.out.println("case: "+ccs.getCellCaseById(ccs.getCellCaseIdByCellItemId(ci.getCellItemId()).getCellCaseId()).getCaseTopic());
                ShowErrorNotification.error("No Item Key was found for STEM: \n"
                        +cis.getCellItemById(ci.getCellItemId()).getItem());
                return null;
            }
            Label stem = new Label();            
            stem.setValue(i+". "+cis.getCellItemById(ci.getCellItemId()).getItem().replace("{key}", keyList.get(0)));
            stem.setContentMode(ContentMode.HTML);
            form.addComponent(stem);
            
            GridLayout glayout = new GridLayout(2, 2);
            glayout.setWidth("100%");
            
            glayout.addComponent(new Label("A) "+cis.getCellItemById(ci.getCellItemId()).getOptionA(), ContentMode.HTML), 0, 0);
            glayout.addComponent(new Label("C) "+cis.getCellItemById(ci.getCellItemId()).getOptionC(), ContentMode.HTML), 0, 1);
            glayout.addComponent(new Label("B) "+cis.getCellItemById(ci.getCellItemId()).getOptionB(), ContentMode.HTML), 1, 0);
            glayout.addComponent(new Label("D) "+ cis.getCellItemById(ci.getCellItemId()).getOptionD(), ContentMode.HTML), 1, 1);
            form.addComponent(glayout);
            
            i++;
        }            
        
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
