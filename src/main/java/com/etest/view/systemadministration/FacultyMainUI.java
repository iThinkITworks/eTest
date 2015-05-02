/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.model.Users;
import com.etest.service.FacultyService;
import com.etest.serviceprovider.facultyServiceImpl;
import com.etest.view.systemadministration.datagrid.FacultyDataGrid;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class FacultyMainUI extends VerticalLayout {

    TextField firstname = new TextField("Firstname: ");
    TextField middlename = new TextField("Middlename: ");
    TextField lastname = new TextField("Lastname: ");
    TextField username = new TextField("LoginName: ");
    PasswordField password1 = new PasswordField("Enter Password: ");
    PasswordField password2 = new PasswordField("Re-Enter Password: ");
    Button facultyBtn = new Button();
    CheckBox enableAddNewFaculty;
    CheckBox removeFaculty;
    
    private static final String BUTTON_SAVE_CAPTION = "ADD NEW FACULTY";
    private static final String BUTTON_UPDATE_CAPTION = "UPDATE FACULTY INFO";
    
    FacultyService fs = new facultyServiceImpl();
    Users u;
    Grid grid = new FacultyDataGrid();
    
    Button setPositionBtn = new Button("SET POSITION TO FACULTY");
    Button setUserTypeBtn = new Button("SET USER TYPE TO FACULTY");
    
    private int facultyId;
    
    public FacultyMainUI() {
        setSizeFull();
        setSpacing(true);
        setMargin(true);
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setWidth("900px");  
        hlayout.setSpacing(true);
                
        populateDataGrid();
        hlayout.addComponent(getDataGridPanel());
        hlayout.addComponent(buildForm());        
        
        enableAddNewFaculty.setVisible(false);
        removeFaculty.setVisible(false);
                
        addComponent(hlayout);
        
        setPositionBtn.setWidth("400px");
        setPositionBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        setPositionBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        setPositionBtn.addClickListener(positionBtnListener);
        addComponent(setPositionBtn);
        
        setUserTypeBtn.setWidth("400px");
        setUserTypeBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        setUserTypeBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        setUserTypeBtn.addClickListener(userTypeBtnListener);
        addComponent(setUserTypeBtn);
    }
        
    FormLayout buildForm(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setSpacing(true);
        
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
        
        facultyBtn.setCaption(BUTTON_SAVE_CAPTION);
        facultyBtn.setWidth("100%");
        facultyBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        facultyBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        facultyBtn.addClickListener(buttonClickListener);
        form.addComponent(facultyBtn);
                  
        enableAddNewFaculty = new CheckBox("Add New Faculty");
        enableAddNewFaculty.addValueChangeListener(checkBoxValueListener);
        form.addComponent(enableAddNewFaculty);
                
        removeFaculty = new CheckBox("Remove Account");
        removeFaculty.addValueChangeListener(checkBoxRemoveListener);
        form.addComponent(removeFaculty);
        
        return form;
    } 
        
    Panel getDataGridPanel(){
        Panel panel = new Panel("Faculty");
        panel.setSizeFull();
        
        panel.setContent(grid);
        
        return panel;
    }
    
    Grid populateDataGrid(){
        grid.getContainerDataSource().removeAllItems();        
        for(Users u : fs.getAllFaculty()){
            grid.addRow(u.getName().toUpperCase(), 
                    u.getUsername_());
        }
        
        grid.recalculateColumnWidths();
        
        for(Object listener : grid.getListeners(ItemClickEvent.class)){
            grid.removeListener(ItemClickEvent.class, listener);
        }
        
        grid.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Object itemId = event.getItemId();
                Item item = grid.getContainerDataSource().getItem(itemId);
                                
                facultyId = fs.getFacultyIdByName(item.getItemProperty("Name").getValue().toString().toLowerCase());
                
                Users u = fs.getFacultyInfoById(facultyId);
                firstname.setValue(u.getFirstname());
                middlename.setValue(u.getMiddlename());
                lastname.setValue(u.getLastname());
                username.setValue(u.getUsername_());
                password1.setValue(u.getPassword_());
                password2.setValue(u.getPassword_());
                facultyBtn.setCaption(BUTTON_UPDATE_CAPTION);
                
                enableAddNewFaculty.setVisible(true);
                enableAddNewFaculty.setValue(false);
                
                removeFaculty.setVisible(true);
                removeFaculty.setValue(false);
            }
        });
        
        return grid;
    }
    
    Button.ClickListener buttonClickListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
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
            u.setUsername_(username.getValue().trim().toLowerCase());
            u.setPassword_(password1.getValue().trim().toLowerCase());            
            
            if(event.getButton().getCaption().equals(BUTTON_SAVE_CAPTION)){                                
                boolean result = fs.insertNewFaculty(u);
                if(result){
                    clearAllFields();
                    populateDataGrid();
                }
            } else {
                boolean result = fs.updateFaculty(u);
                if(result){
                    clearAllFields();
                    populateDataGrid();
                }
            }
        }    
    
    };
        
    Button.ClickListener positionBtnListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Window sub = new SetPositionToFacultyWindow();
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        }
    };
    
    Button.ClickListener userTypeBtnListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Window sub = new SetUserTypeToFaculty();
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        }
    };
    
    CheckBox.ValueChangeListener checkBoxValueListener = new CheckBox.ValueChangeListener() {

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if((Boolean)event.getProperty().getValue() == true){
                clearAllFields();
                facultyBtn.setCaption(BUTTON_SAVE_CAPTION);
                enableAddNewFaculty.setVisible(false);
                
                removeFaculty.setValue(false);
                removeFaculty.setVisible(false);
            }
        }
    };
    
    CheckBox.ValueChangeListener checkBoxRemoveListener = new CheckBox.ValueChangeListener() {

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if((Boolean)event.getProperty().getValue() == true){
                Window sub = removeAccountWindow();
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
            }
        }
    };
    
    void requiredAllFields(){
        Notification.show("Required All Fields!", Notification.Type.WARNING_MESSAGE);
    }
    
    void clearAllFields(){
        firstname.setValue("");
        middlename.setValue("");
        lastname.setValue("");
        username.setValue("");
        password1.setValue("");
        password2.setValue("");
    }
    
    Window removeAccountWindow(){
        final Window sub = new Window("REMOVE ACCOUNT");
        sub.setWidth("300px");
        sub.setHeight("150px");
        sub.setModal(true);
        sub.isResizable();
        
        GridLayout glayout = new GridLayout();
        glayout.setWidth("100%");
        glayout.setMargin(true);
        
        Button removeBtn = new Button("DELETE THIS ACCOUNT?");
        removeBtn.setWidth("100%");
        removeBtn.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean result = fs.removeFaculty(getFacultyId());
                if(result){
                    populateDataGrid();
                    clearAllFields();
                    enableAddNewFaculty.setValue(false);
                    enableAddNewFaculty.setVisible(false);
                    removeFaculty.setValue(false);
                    removeFaculty.setVisible(false);
                    facultyBtn.setCaption(BUTTON_SAVE_CAPTION);
                    sub.close();
                }
            }
        });
        glayout.addComponent(removeBtn);
        
        sub.setContent(glayout);
        
        return sub;
    }
    
    int getFacultyId(){
        return facultyId;
    }
    
}
