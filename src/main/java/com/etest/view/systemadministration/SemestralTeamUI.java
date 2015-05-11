/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonVariableMap;
import com.etest.model.TeamTeach;
import com.etest.service.TeamTeachService;
import com.etest.service.UsersService;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.etest.serviceprovider.UsersServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.etest.view.systemadministration.SemestralTeam.AddSemestralTeamMembersWindow;
import com.etest.view.systemadministration.SemestralTeam.RemoveSemestralTeamWindow;
import com.etest.view.systemadministration.SemestralTeam.SemestralTeamDataGrid;
import com.etest.view.systemadministration.SemestralTeam.SemestralTeamDataTable;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SemestralTeamUI extends VerticalLayout {

    TeamTeachService tts = new TeamTeachServiceImpl();
    
    ComboBox schoolYear;
    ComboBox semester;
    ComboBox subjects;
    ComboBox faculty;
    
    Table table = new SemestralTeamDataTable();
    Grid grid = new SemestralTeamDataGrid();    
    
    public SemestralTeamUI() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
                
        addComponent(buildForms());
//        populateDataGrid();
        populateDataTable();
        addComponent(dataTablePanel());
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("400px");
        form.setSpacing(true);
        
        subjects = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
        subjects.setCaption("Semester: ");
        subjects.setIcon(FontAwesome.SEARCH);
        form.addComponent(subjects);
        
        schoolYear = CommonComboBox.getSchoolYear("School Year..");
        schoolYear.setCaption("School Year: ");
        schoolYear.setIcon(FontAwesome.SEARCH);
        form.addComponent(schoolYear);
        
        semester = CommonComboBox.getNormCourseOfferingComboBox("Semester..");
        semester.setCaption("Semester: ");
        semester.setIcon(FontAwesome.SEARCH);
        form.addComponent(semester);
                
        faculty = CommonComboBox.getAllFaculty("Select Faculty..");
        faculty.setCaption("Team Leader: ");
        faculty.setIcon(FontAwesome.USER);
        form.addComponent(faculty);
        
        Button saveBtn = new Button("Enroll Semestral Team");
        saveBtn.setWidth("100%");
        saveBtn.setIcon(FontAwesome.SAVE);
        saveBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        saveBtn.addClickListener(saveBtnClickListener);
        saveBtn.setImmediate(true);
        form.addComponent(saveBtn);
        
        return form;
    }
    
    Panel dataTablePanel(){
        Panel panel = new Panel();
        panel.setWidth("100%");
                
        panel.setContent(table);        
        return panel;
    }
    
//    Grid populateDataGrid(){
//        grid.getContainerDataSource().removeAllItems();                
//        for(TeamTeach tt : tts.getAllSemestralTeamTeach()){
//            membersBtn = new Button();
//            membersBtn.setData(tt.getTeamTeachId());
//            grid.addRow(tt.getSchoolYear(), 
//                CommonVariableMap.getNormCourseOffering(tt.getNormCourseOffering()), 
//                CommonVariableMap.getYearLevel(tt.getYearLevel()), 
//                tt.getSubject(), 
//                tt.getTeamLeader(), 
//                membersBtn
//            );
//        }
//        grid.recalculateColumnWidths();
//        
//        return grid;
//    }
    
    void populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(TeamTeach tt : tts.getAllSemestralTeamTeach()){
            HorizontalLayout hlayout = new HorizontalLayout();
            hlayout.setWidth("100%");
            
            Button membersBtn = new Button();
            membersBtn.setWidth("100%");
            membersBtn.setData(tt.getTeamTeachId());
            if(tts.countTeamMembers(tt.getTeamTeachId()) < 2){
                membersBtn.setCaption("add");
                membersBtn.setIcon(FontAwesome.USER);
            } else {
                membersBtn.setCaption("view");
                membersBtn.setIcon(FontAwesome.USERS);
            }
            
            Button removeTLBtn = new Button("del");
            removeTLBtn.setWidth("100%");
            removeTLBtn.setIcon(FontAwesome.ERASER);
            removeTLBtn.setData(tt.getTeamTeachId());
            
            hlayout.addComponent(membersBtn);
            hlayout.addComponent(removeTLBtn);
            
            table.addItem(new Object[]{ 
                tt.getSchoolYear(), 
                CommonVariableMap.getNormCourseOffering(tt.getNormCourseOffering()), 
                CommonVariableMap.getYearLevel(tt.getYearLevel()), 
                tt.getSubject(), 
                tt.getTeamLeader(), 
                hlayout
            }, new Integer(i));
            i++;
        
            membersBtn.addStyleName(ValoTheme.BUTTON_LINK);
            membersBtn.addStyleName(ValoTheme.BUTTON_TINY);
            membersBtn.addClickListener(modifyBtnListener);
            
            removeTLBtn.addStyleName(ValoTheme.BUTTON_LINK);
            removeTLBtn.addStyleName(ValoTheme.BUTTON_TINY);
            removeTLBtn.addClickListener(modifyBtnListener);
        }
        table.setPageLength(table.size());        
    }
    
    Button.ClickListener saveBtnClickListener = (Button.ClickEvent event) -> {
        if(subjects.getValue() == null){ requiredAllFields(); return; }
        if(schoolYear.getValue() == null ){ requiredAllFields(); return; }
        if(semester.getValue() == null){ requiredAllFields(); return; }
        if(faculty.getValue() == null){ requiredAllFields(); return; }
        
        UsersService us = new UsersServiceImpl();
        TeamTeach tt = new TeamTeach();
        tt.setCurriculumId(CommonUtilities.convertStringToInt(subjects.getValue().toString()));
        tt.setSchoolYear(schoolYear.getValue().toString());
        tt.setNormCourseOffering(CommonUtilities.convertStringToInt(semester.getValue().toString()));
        tt.setUserId(us.getUserIdByFacultyId((int)faculty.getValue()));
        tt.setFacultyId((int)faculty.getValue());
        
        boolean result = tts.insertNewTeamTeach(tt);
        if(result){
            populateDataTable();
        }
    };
    
    Button.ClickListener modifyBtnListener = (Button.ClickEvent event) -> {
        Window sub;
        if(event.getButton().getCaption().equals("del")){
            sub = new RemoveSemestralTeamWindow((int)event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
            sub.addCloseListener((Window.CloseEvent e) -> {
                populateDataTable();
            });
        } else {
            sub = new AddSemestralTeamMembersWindow((int)event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
            sub.addCloseListener((Window.CloseEvent e) -> {
                populateDataTable();
            });
        }        
    };
    
    void requiredAllFields(){
        Notification.show("Required All Fields", Notification.Type.ERROR_MESSAGE);
    }   
    
    Window.CloseListener windowCloseListener = (Window.CloseEvent e) -> {
        populateDataTable();
    };
}
