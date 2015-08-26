/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.view.systemadministration.syllabus.SyllabusMainUI;
import com.etest.view.tq.TQCoverageUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author jetdario
 */
public class SystemAdministrationUI extends TabSheet implements TabSheet.SelectedTabChangeListener {

    public SystemAdministrationUI() {
        setWidth("100%");
        addStyleName("bar");
        
        VerticalLayout v = new VerticalLayout();
        v.setCaption("Curriculum");
        v.setWidth("100%");
        v.addComponent(new CurriculumMainUI());
//        v.setMargin(true);
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Faculty Member");
        v.setWidth("100%");
        v.addComponent(new FacultyMainUI());
//        v.setMargin(true);
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Syllabus");
        v.setWidth("100%");
        v.addComponent(new SyllabusMainUI());
//        v.setMargin(true);
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Semestral Team");
        v.setWidth("100%");
        v.addComponent(new SemestralTeamUI());
//        v.setMargin(true);
        addComponent(v);
        
        v = new VerticalLayout();
        v.setCaption("Housekeeping");
        v.setWidth("100%");
//        v.addComponent(new CurriculumMainUI());
//        v.setMargin(true);
        addComponent(v);
    }

    @Override
    public void selectedTabChange(SelectedTabChangeEvent event) {
        //TODO
    }
    
}
