/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.common.CommonVariableMap;
import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.etest.view.systemadministration.datagrid.CurriculumDataGrid;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CurriculumMainUI extends VerticalLayout {
    
    CurriculumService cs = new CurriculumServiceImpl();
    Grid grid = new CurriculumDataGrid();    
    
    ComboBox yearLevel;
    TextField subjectField;
    TextArea descriptiveTitleField;
    ComboBox normCourseOffering;
    
    public CurriculumMainUI() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        
        addComponent(buildForms());
        populateDataGrid();
        addComponent(dataGridPanel());
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setSizeFull();
        
        yearLevel = CommonComboBox.getYearLevelComboBox("Year Level..");
        yearLevel.setWidth("200px");
        form.addComponent(yearLevel);
        
        subjectField = new CommonTextField("Subject Name..");
        subjectField.setWidth("200px");
        form.addComponent(subjectField);
        
        normCourseOffering = CommonComboBox.getNormCourseOfferingComboBox("Normal Course Offering..");
        normCourseOffering.setWidth("200px");
        form.addComponent(normCourseOffering);
        
        descriptiveTitleField = new TextArea();
        descriptiveTitleField.setWidth("500px");
        descriptiveTitleField.setRows(2);
        descriptiveTitleField.setInputPrompt("Descriptive Title..");
        form.addComponent(descriptiveTitleField);        
        
        Button saveBtn = new Button("Enter Curriculum");
        saveBtn.setWidth("200px");
        saveBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        saveBtn.addClickListener(buttonClickListener);
        form.addComponent(saveBtn);        
        
        return form;
    }
    
    Panel dataGridPanel(){
        Panel panel = new Panel("List of All Curriculum");
        panel.setWidth("100%");
        panel.setHeight("500px");
        
        panel.setContent(grid);
                
        return panel;
    }
            
    Grid populateDataGrid(){
        grid.getContainerDataSource().removeAllItems();
        for(Curriculum c : cs.getAllCurriculum()){
            grid.addRow(c.getCurriculumId(), 
                    CommonVariableMap.getYearLevel(c.getYearLevel()), 
                    c.getSubject(), 
                    c.getDescriptiveTitle(), 
                    CommonVariableMap.getNormCourseOffering(c.getNormCourseOffering())
            );
        }        
        grid.recalculateColumnWidths();
        
        return grid;
    }
    
    void clearFields(){
        subjectField.setValue("");
        descriptiveTitleField.setValue("");
    }
    
    
    void requiredAllFields(){
        Notification.show("Fill up all Fields!", Notification.Type.WARNING_MESSAGE);
    }
            
    Button.ClickListener buttonClickListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            if(yearLevel.getValue() == null){ requiredAllFields(); return; }
            if(subjectField.getValue() == null || 
                    subjectField.getValue().isEmpty()){ requiredAllFields(); return; }
            if(descriptiveTitleField.getValue() == null || 
                    descriptiveTitleField.getValue().isEmpty()){ requiredAllFields(); return; }
            if(normCourseOffering.getValue() == null){ requiredAllFields(); return; }
                            
            Curriculum c = new Curriculum();
            c.setYearLevel(CommonUtilities.convertStringToInt(yearLevel.getValue().toString()));
            c.setSubject(subjectField.getValue());
            c.setDescriptiveTitle(descriptiveTitleField.getValue());
            c.setNormCourseOffering(CommonUtilities.convertStringToInt(normCourseOffering.getValue().toString()));
                
            boolean result = cs.insertNewCurriculum(c);
            if(result){
                clearFields();
                populateDataGrid();
            }
        }
    };
    
    Window.CloseListener windowCloseListener = new Window.CloseListener(){

        @Override
        public void windowClose(Window.CloseEvent e) {
            //TODO
            populateDataGrid();
        }
            
    };
}
