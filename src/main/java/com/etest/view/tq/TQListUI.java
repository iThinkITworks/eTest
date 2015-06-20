/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.model.TQCoverage;
import com.etest.service.CurriculumService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class TQListUI extends TQListTableProperties{

    TQCoverageService coverageService = new TQCoverageServiceImpl();
    CurriculumService cs = new CurriculumServiceImpl();
    
    public TQListUI() {
        populateTable();
    }
    
    void populateTable(){
        removeAllItems();
        int i = 0;
        for(TQCoverage tq : coverageService.getAllTQCoverage()){
            HorizontalLayout h = new HorizontalLayout();
            h.setWidth("100%");
            
            Button view = new Button("view");
            view.setSizeFull();
            view.setData(tq.getTqCoverageId());
            view.setIcon(FontAwesome.PENCIL);
            view.addStyleName(ValoTheme.BUTTON_LINK);
            view.addStyleName(ValoTheme.BUTTON_TINY);
            view.addStyleName(ValoTheme.BUTTON_QUIET);
            view.addStyleName("button-container");
//            edit.addClickListener(modifyBtnClickListener);
            h.addComponent(view);
            h.setComponentAlignment(view, Alignment.MIDDLE_LEFT);
            
            Button approve = new Button("status");
            approve.setSizeFull();
            approve.setData(tq.getTqCoverageId());            
            approve.addStyleName(ValoTheme.BUTTON_LINK);
            approve.addStyleName(ValoTheme.BUTTON_TINY);
            approve.addStyleName(ValoTheme.BUTTON_QUIET);
            approve.addStyleName("button-container");
            h.addComponent(approve);
            h.setComponentAlignment(approve, Alignment.MIDDLE_LEFT);
            
            if(tq.getStatus() == 0){ approve.setIcon(FontAwesome.THUMBS_DOWN); }
            else { approve.setIcon(FontAwesome.THUMBS_UP); } 
            
            addItem(new Object[]{
                tq.getExamTitle(), 
                cs.getCurriculumById(tq.getCurriculumId()).getSubject(), 
                tq.getDateCreated(), 
                tq.getTotalHoursCoverage(), 
                tq.getTotalItems(), 
                h
            }, i);
            i++;
        }
    }
}
