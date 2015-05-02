/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.datagrid;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CurriculumWindow extends Window {

    CurriculumService cs = new CurriculumServiceImpl();
    
    ComboBox yearLevel = CommonComboBox.getYearLevelComboBox("Year Level..");
    TextField subjectField= new CommonTextField("Subject..");
    TextArea descriptiveTitleField = new TextArea();
    ComboBox normCourseOffering = CommonComboBox.getNormCourseOfferingComboBox("Normal Course Offering..");
    
    int curriculumId;
    
    public CurriculumWindow(int curriculumId) {
        this.curriculumId = curriculumId;
        
        setCaption("Curriculum");
        setWidth("500px");
        setHeight("450px");
        setResizable(false);
        setModal(true);
        
        GridLayout glayout = new GridLayout(2, 5);
        glayout.setWidth("100%");
        glayout.setSpacing(true);
        glayout.setMargin(true);
        Responsive.makeResponsive(glayout);
        
        Curriculum c = cs.getCurriculumById(curriculumId);
        
        yearLevel.setCaption("Year Level: ");
        yearLevel.setValue(c.getYearLevel());
        yearLevel.setWidth("50%");
        glayout.addComponent(yearLevel, 0, 0, 1, 0);
        
        subjectField.setCaption("Subject: ");
        subjectField.setValue(c.getSubject());
        subjectField.setWidth("50%");
        glayout.addComponent(subjectField, 0, 1, 1, 1);
        
        normCourseOffering.setCaption("Normal Course Offering: ");
        normCourseOffering.setValue(c.getNormCourseOffering());
        normCourseOffering.setWidth("50%");
        glayout.addComponent(normCourseOffering, 0, 2, 1, 2);
        
        descriptiveTitleField.setCaption("Descriptive Title: ");
        descriptiveTitleField.setWidth("100%");
        descriptiveTitleField.setRows(2);
        descriptiveTitleField.setInputPrompt("Descriptive Title..");
        descriptiveTitleField.setValue(c.getDescriptiveTitle());
        glayout.addComponent(descriptiveTitleField, 0, 3, 1, 3);    
                        
        Button update = new Button("UPDATE");
        update.setWidth("100%");
        update.addStyleName(ValoTheme.BUTTON_PRIMARY);
        update.addClickListener(buttnEventListener);
        glayout.addComponent(update, 0, 4);
        
        Button delete = new Button("DELETE");
        delete.setWidth("100%");
        delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addClickListener(buttnEventListener);
        glayout.addComponent(delete, 1, 4);
        
        setContent(glayout);
    }
        
    Button.ClickListener buttnEventListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            if(event.getButton().getCaption().equals("UPDATE")){
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
                c.setCurriculumId(getCurriculumId());
                
                boolean result = cs.updateCurriculum(c);
                if(result){
                    close();
                }
            } else {
                boolean result = cs.removeCurriculum(getCurriculumId());
                if(result){
                    close();
                }
            }
        }
    };
    
    int getCurriculumId(){
        return curriculumId;
    }
    
    void requiredAllFields(){
        Notification.show("Fill up all Fields!", Notification.Type.ASSISTIVE_NOTIFICATION);
    }
}
