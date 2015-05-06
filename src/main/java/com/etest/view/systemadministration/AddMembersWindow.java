/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonComboBox;
import com.etest.model.TeamTeach;
import com.etest.service.TeamTeachService;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
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
    private int teamTeachId;
    private int facultyId;
    
    Table table = new Table();
    ComboBox faculty;
    Button addMemberBtn = new Button("ADD");
    
    public AddMembersWindow(int teamTeachId) {
        this.teamTeachId = teamTeachId;
        
        setCaption("ADD TEAM MEMBERS");
        setWidth("350px");
        setModal(true);
        center();
        
        facultyId = tts.getFacultyIdByTeamTeachId(teamTeachId);
        setContent(buildForms());
                
//        getContent().setWidthUndefined();
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
        table.setWidth("100%");
        table.setSelectable(true);
        table.addStyleName(ValoTheme.TABLE_SMALL);
        table.setImmediate(true);
        
        table.addContainerProperty("id", Integer.class, null);
        table.addContainerProperty("member", String.class, null);
        
        table.removeAllItems();
        int i = 0;
        for(TeamTeach tt : tts.getAllMembersFromTeam(getTeamTeachId())){
            table.addItem(new Object[]{
                tt.getFacultyId(), 
                tt.getName()
            }, i);
            i++;
        }
        table.setPageLength(table.size());
        
        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Property itemProperty = event.getItem().getItemProperty("id");      
                
                if(event.getPropertyId().equals("id")){
                    int f_id = CommonUtilities.convertStringToInt(itemProperty.getValue().toString());
                    boolean result = tts.isFacultyTeamLeader(getTeamTeachId(), f_id);
                    if(result){
                        Notification.show("Cannot Remove a Team Leader!", Notification.Type.WARNING_MESSAGE);
                        return;
                    } else {
                        Window sub = removeTeamMemberWindow(f_id);
                        if(sub.getParent() == null){
                            UI.getCurrent().addWindow(sub);
                        }
                    }                    
                }
                
            }
        });
        return table;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(faculty.getValue() == null){
            Notification.show("Select a Faculty!", Notification.Type.WARNING_MESSAGE);
            return;
        }
        
        boolean result = tts.addTeamMember(getTeamTeachId(), (int)faculty.getValue());
        if(result){
            populateDataTable();
        }
    };
    
    Window removeTeamMemberWindow(int facultyId){
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
        removeBtn.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean result = tts.removeTeamMember(getTeamTeachId(), facultyId);
                if(result){
                    populateDataTable();
                    sub.close();
                }
            }
        });
        vlayout.addComponent(removeBtn);
        
        sub.setContent(vlayout);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
}
