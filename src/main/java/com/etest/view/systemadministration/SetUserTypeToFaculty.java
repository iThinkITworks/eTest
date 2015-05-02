/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonComboBox;
import com.etest.service.UsersService;
import com.etest.serviceprovider.UsersServiceImpl;
import com.etest.utilities.CommonUtilities;
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
public class SetUserTypeToFaculty extends Window {

    UsersService us = new UsersServiceImpl();
    
    ComboBox faculty;
    ComboBox userType;
    
    public SetUserTypeToFaculty() {
        setCaption("SET USER TYPE");
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
        
        userType = CommonComboBox.getFacultyUserType("Select User Type..");
        form.addComponent(userType);
        
        Button saveBtn = new Button("UPDATE USER TYPE");
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
            if(userType.getValue() == null){ requiredAllFields(); return; }
                                    
            boolean result = us.updateUsersColumnValue("UserType", 
                    userType.getItem(userType.getValue()).toString(), 
                    CommonUtilities.convertStringToInt(faculty.getValue().toString()));
            if(result){
                close();
            }
        }
    };
    
    void requiredAllFields(){
        Notification.show("Required All Fields", Notification.Type.ERROR_MESSAGE);
    }
}
