/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonComboBox;
import com.etest.model.TeamTeach;
import com.etest.service.FacultyService;
import com.etest.service.TeamTeachService;
import com.etest.service.UsersService;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.etest.serviceprovider.UsersServiceImpl;
import com.etest.serviceprovider.facultyServiceImpl;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class AddMembersWindow extends Window {

    TeamTeachService tts = new TeamTeachServiceImpl();
    FacultyService fs = new facultyServiceImpl();
    UsersService us = new UsersServiceImpl();
    private int teamTeachId;
    private int facultyId;
    
    Table table = new AddNewMembersTable();
    ComboBox faculty;
    Button addMemberBtn = new Button("ADD");
    
    public AddMembersWindow(int teamTeachId) {
        this.teamTeachId = teamTeachId;
        
        setCaption("ADD TEAM MEMBERS");
        setWidth("450px");
        setModal(true);
        center();
        
        facultyId = tts.getFacultyIdByTeamTeachId(teamTeachId);
        setContent(buildForms());
                
        getContent().setHeightUndefined();
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        form.setSpacing(true);
        
        faculty = CommonComboBox.getAllFacultyExceptTeamLeader("Add Team Member.. ", getFacultyId());
        faculty.setWidth("100%");
        faculty.setIcon(FontAwesome.USER);
        form.addComponent(faculty);
        
        addMemberBtn.setWidth("100%");
        addMemberBtn.setIcon(FontAwesome.SAVE);
        addMemberBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addMemberBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        addMemberBtn.addClickListener(buttonClickListener);
        form.addComponent(addMemberBtn);
        
        populateDataTable();
        form.addComponent(table);
        
        return form;
    }
    
    int getTeamTeachId(){
        return teamTeachId;
    }
    
    int getFacultyId(){
        return facultyId;
    }
    
    Table populateDataTable(){        
        table.removeAllItems();
        int i = 0;
        for(TeamTeach tt : tts.getAllMembersFromTeam(getTeamTeachId())){
            String position;
            boolean isFacultyTeamLeader = tts.isFacultyTeamLeader(getTeamTeachId(), tt.getFacultyId());
            if(isFacultyTeamLeader){
                position = "Team Leader";
            } else {
                position = "Member";
            }
            
            HorizontalLayout hlayout = new HorizontalLayout();
            hlayout.setWidth("100%");
            
            Button editPositionBtn = new Button("edit");
            editPositionBtn.setWidth("100%");
            editPositionBtn.setData(tt.getFacultyId());
            editPositionBtn.setIcon(FontAwesome.EDIT);
            editPositionBtn.addStyleName(ValoTheme.BUTTON_LINK);
            editPositionBtn.addStyleName(ValoTheme.BUTTON_TINY);
            editPositionBtn.addClickListener(modifyBtnClickListener);
            hlayout.addComponent(editPositionBtn);
            
            Button removeMemberBtn = new Button("del");
            removeMemberBtn.setWidth("100%");  
            removeMemberBtn.setData(tt.getFacultyId());
            removeMemberBtn.setIcon(FontAwesome.ERASER);
            removeMemberBtn.addStyleName(ValoTheme.BUTTON_LINK);
            removeMemberBtn.addStyleName(ValoTheme.BUTTON_TINY);
            removeMemberBtn.addClickListener(modifyBtnClickListener);
            hlayout.addComponent(removeMemberBtn);
            
            if(!position.equals("Member")){
                editPositionBtn.setEnabled(false);
                removeMemberBtn.setEnabled(false);
            }
            
            table.addItem(new Object[]{
                tt.getFacultyId(), 
                tt.getName(), 
                position, 
                hlayout
            }, i);
            i++;
        }
        table.setPageLength(table.size());
        
//        table.addItemClickListener((ItemClickEvent event) -> {
//            Property itemProperty = event.getItem().getItemProperty("id");
//            facultyIdFromTable = CommonUtilities.convertStringToInt(itemProperty.getValue().toString());
//            
//        });
        return table;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(faculty.getValue() == null){
            Notification.show("Select a Faculty!", Notification.Type.WARNING_MESSAGE);
            return;
        }
        
        boolean checkMemberIfExist = tts.isTeamMemberAlreadyExist(getTeamTeachId(), (int)faculty.getValue());
        if(checkMemberIfExist){
            Notification.show("Faculty was already added!", Notification.Type.WARNING_MESSAGE);
            return;
        }
        
        boolean result = tts.addTeamMember(getTeamTeachId(), (int)faculty.getValue());
        if(result){
            populateDataTable();
        }
    };
    
    Button.ClickListener modifyBtnClickListener = (Button.ClickEvent event) -> {
        Window sub;
        if(event.getButton().getCaption().equals("del")){
            sub = removeTeamMemberWindow((int) event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        } else {
            sub = editTeamMemberPositionWindow((int) event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        }
        
    };
    
    Window removeTeamMemberWindow(int facultyRowId){
        Window sub = new Window();
        sub.setCaption("REMOVE TEAM MEMBER");
        sub.setWidth("250px");
        sub.setModal(true);
        sub.center();
        
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setSizeFull();
        vlayout.setMargin(true);
        
        Button removeBtn = new Button("REMOVE");
        removeBtn.setWidth("100%");
        removeBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        removeBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        removeBtn.addClickListener((Button.ClickEvent event) -> {
            boolean result = tts.removeTeamMember(getTeamTeachId(), facultyRowId);
            if(result){
                populateDataTable();
                sub.close();
            }
        });
        vlayout.addComponent(removeBtn);
        
        sub.setContent(vlayout);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
    
    Window editTeamMemberPositionWindow(int facultyRowId){
        Window sub = new Window();
        sub.setCaption("REMOVE TEAM MEMBER");
        sub.setWidth("250px");
        sub.setModal(true);
        sub.center();
        
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setSizeFull();
        vlayout.setSpacing(true);
        vlayout.setMargin(true);
        
        String name = fs.getFacultyNameById(facultyRowId);
        vlayout.addComponent(new Label("Set "+name.toUpperCase()+" as Team Leader."));        
        
        Button updateBtn = new Button("UPDATE");
        updateBtn.setWidth("100%");
        updateBtn.setIcon(FontAwesome.USER);
        updateBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        updateBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        updateBtn.addClickListener((Button.ClickEvent event) -> {
            int userId = us.getUserIdByFacultyId(facultyRowId);
            boolean result = tts.updateTeamTeach(getTeamTeachId(), userId);
            if(result){
                sub.close();
                populateDataTable();
                close();
            }
        });
        vlayout.addComponent(updateBtn);
        
        sub.setContent(vlayout);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
}
