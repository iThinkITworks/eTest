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
import com.etest.view.systemadministration.datagrid.SemestralDataGrid;
import com.etest.view.systemadministration.datagrid.SemestralTeamDataTable;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
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
    Grid grid = new SemestralDataGrid();    
    
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
        saveBtn.addClickListener(buttonClickListener);
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
            Button membersBtn = new Button();
            membersBtn.setWidth("100%");
            membersBtn.setData(tt.getTeamTeachId());
            if(tts.countTeamMembers(tt.getTeamTeachId()) < 2){
                membersBtn.setCaption("Add Members");
            } else {
                membersBtn.setCaption("View Members");
            }
            
            table.addItem(new Object[]{ 
                tt.getSchoolYear(), 
                CommonVariableMap.getNormCourseOffering(tt.getNormCourseOffering()), 
                CommonVariableMap.getYearLevel(tt.getYearLevel()), 
                tt.getSubject(), 
                tt.getTeamLeader(), 
                membersBtn
            }, new Integer(i));
        i++;
        
        membersBtn.addStyleName(ValoTheme.BUTTON_LINK);
            membersBtn.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Window sub = new AddMembersWindow((int)membersBtn.getData());
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }
                }
            });
        }
        table.setPageLength(table.size());        
    }
    
    Button.ClickListener buttonClickListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
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
//                populateDataGrid();
                populateDataTable();
            }
        }
    };
    
    void requiredAllFields(){
        Notification.show("Required All Fields", Notification.Type.ERROR_MESSAGE);
    }   
    
}
