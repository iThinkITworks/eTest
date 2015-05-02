/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonComboBox;
import com.etest.model.Users;
import com.etest.service.FacultyService;
import com.etest.serviceprovider.facultyServiceImpl;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SetPositionToFacultyWindow extends Window {

    FacultyService fs = new facultyServiceImpl();
    
    ComboBox faculty;
    ComboBox position;
    
    public SetPositionToFacultyWindow() {
        setCaption("SET POSITION");
        setWidth("300px");
        setHeight("200px");  
        setModal(true);
        
        setContent(buildForms());
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setSizeFull();
        form.setSpacing(true);
        form.setMargin(true);
        
        faculty = CommonComboBox.getAllFaculty("Select Faculty..");
        form.addComponent(faculty);
        
        position = CommonComboBox.getFacultyPosition("Select Position..");
        form.addComponent(position);
        
        Button saveBtn = new Button("UPDATE FACULTY POSITION");
        saveBtn.setWidth("100%");
        saveBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        saveBtn.addClickListener(saveBtnListener);
        form.addComponent(saveBtn);
        
        return form;
    }
    
    Button.ClickListener saveBtnListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            if(faculty.getValue() == null){requiredAllFields(); return; }
            if(position.getValue() == null){ requiredAllFields(); return; }
                
            boolean result = fs.updateFacultyColumnValue("Position", 
                    position.getItem(position.getValue()).toString(), 
                    (int)faculty.getValue());
            if(result){
                close();
            }
        }
    };
    
    void requiredAllFields(){
        Notification.show("Required All Fields", Notification.Type.ERROR_MESSAGE);
    }
    
}
