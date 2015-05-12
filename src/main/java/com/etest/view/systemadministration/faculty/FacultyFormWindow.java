/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.faculty;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonVariableMap;
import com.etest.model.Users;
import com.etest.service.FacultyService;
import com.etest.serviceprovider.facultyServiceImpl;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class FacultyFormWindow extends Window {

    FacultyService fs = new facultyServiceImpl();
    Users u;
    
    TextField firstname = new TextField("Firstname: ");
    TextField middlename = new TextField("Middlename: ");
    TextField lastname = new TextField("Lastname: ");
    ComboBox userType = CommonComboBox.getFacultyUserType("Select User Type..");
    TextField username = new TextField("LoginName: ");
    PasswordField password1 = new PasswordField("Enter Password: ");
    PasswordField password2 = new PasswordField("Re-Enter Password: ");
    Button facultyBtn = new Button();
    CheckBox enableAddNewFaculty;
    CheckBox removeFaculty;
        
    private int facultyId;
    private String buttonCaption;
    
    public FacultyFormWindow(int facultyId, String buttonCaption) {
        this.facultyId = facultyId;
        this.buttonCaption = buttonCaption;
        
        setCaption("FACULTY FORM");
        setWidth("420px");
        setResizable(false);
        setModal(true);
        center();
        
        System.out.println("caption: "+getButtonCaption());
        
        setContent(buildForms());
        getContent().setHeightUndefined();
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setSpacing(true);
        form.setMargin(true);
        
        firstname.setWidth("100%");
        firstname.setIcon(FontAwesome.INFO);
        firstname.setRequired(true);
        firstname.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(firstname);
        
        middlename.setWidth("100%");
        middlename.setIcon(FontAwesome.INFO);
        middlename.setRequired(true);
        middlename.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(middlename);
        
        lastname.setWidth("100%");
        lastname.setIcon(FontAwesome.INFO);
        lastname.setRequired(true);
        lastname.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(lastname);
        
        userType.setCaption("User Type: ");
        userType.setIcon(FontAwesome.USER_MD);
        userType.setRequired(true);
        form.addComponent(userType);
        
        username.setWidth("100%");
        username.setIcon(FontAwesome.USER);
        username.setRequired(true);
        username.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(username);
        
        password1.setWidth("100%");
        password1.setIcon(FontAwesome.CODE);
        password1.setRequired(true);
        password1.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(password1);        
        
        password2.setWidth("100%");
        password2.setIcon(FontAwesome.CODE);
        password2.setRequired(true);
        password2.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(password2);
                
        facultyBtn.setCaption(getButtonCaption());
        facultyBtn.setWidth("100%");
        facultyBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        facultyBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        facultyBtn.addClickListener(buttonClickListener);
        form.addComponent(facultyBtn);
                          
        if(getFacultyId() != 0){
            Users u = fs.getFacultyInfoById(getFacultyId());
            firstname.setValue(u.getFirstname());
            middlename.setValue(u.getMiddlename());
            lastname.setValue(u.getLastname());
            username.setValue(u.getUsername_());
            userType.setValue(CommonVariableMap.getFacultyUserType(u.getUserType()));            
            password1.setValue(u.getPassword_());
            password2.setValue(u.getPassword_());            
        }
        
        return form;
    }
    
    int getFacultyId(){
        return facultyId;
    }
    
    String getButtonCaption(){
        return buttonCaption;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(firstname.getValue() == null ||
                firstname.getValue().trim().isEmpty()) { requiredAllFields(); return; }
        if(middlename.getValue() == null ||
                middlename.getValue().trim().isEmpty()) { requiredAllFields(); return; }
        if(lastname.getValue() == null ||
                lastname.getValue().trim().isEmpty()) { requiredAllFields(); return; }
        if(middlename.getValue() == null ||
                middlename.getValue().trim().isEmpty()) { requiredAllFields(); return; }
        if(lastname.getValue() == null ||
                lastname.getValue().trim().isEmpty()) { requiredAllFields(); return; }
        if(userType.getValue() == null){ requiredAllFields(); return; }
        if(password1.getValue() == null ||
                password1.getValue().trim().isEmpty()) { requiredAllFields(); return; }
        if(password1.getValue() == null ||
                password1.getValue().trim().isEmpty()) { requiredAllFields(); return; }
        
        if(!password1.getValue().trim().equals(password2.getValue().trim())){
            Notification.show("Passwords do not match!", Notification.Type.ERROR_MESSAGE);
            return;
        }
        
        u = new Users();
        u.setFirstname(firstname.getValue().trim().toLowerCase());
        u.setMiddlename(middlename.getValue().trim().toLowerCase());
        u.setLastname(lastname.getValue().trim().toLowerCase());
        u.setUserType(userType.getItem(userType.getValue()).toString());
        u.setUsername_(username.getValue().trim().toLowerCase());
        u.setPassword_(password1.getValue().trim().toLowerCase());
        u.setFacultyId(getFacultyId());
        
        switch(event.getButton().getCaption()) {
            case "ADD NEW FACULTY":
                {
                    boolean result = fs.insertNewFaculty(u);
                    if(result){
                        close();
                    }
                    break;
                }
            case "UPDATE FACULTY INFO": 
                {
                    boolean result = fs.updateFaculty(u);
                    if(result){
                        close();
                    }
                    break;
                }
            default: 
                boolean result = fs.removeFaculty(getFacultyId());
                if(result){
                    close();
                }
        }        
    };
    
    void requiredAllFields(){
        Notification.show("Required All Fields!", Notification.Type.WARNING_MESSAGE);
    }      
}
