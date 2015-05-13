/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.curriculum;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CurriculumFormWindow extends Window {

    CurriculumService cs = new CurriculumServiceImpl();
    
    ComboBox yearLevel = CommonComboBox.getYearLevelComboBox("Year Level..");
    TextField subjectField= new CommonTextField("Subject..", null);
    TextArea descriptiveTitleField = new TextArea();
    ComboBox normCourseOffering = CommonComboBox.getNormCourseOfferingComboBox("Normal Course Offering..");
    
    int curriculumId;
    
    public CurriculumFormWindow(int curriculumId) {
        this.curriculumId = curriculumId;
        
        setCaption("Curriculum");
        setWidth("350px");
        setResizable(false);
        setModal(true);
        center();
        
        setContent(buildForms());
        getContent().setHeightUndefined();
    }
            
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setMargin(true);
               
        yearLevel.setWidth("75%");
        form.addComponent(yearLevel);  
                
        subjectField.setWidth("75%");
        form.addComponent(subjectField);
              
        normCourseOffering.setWidth("75%");
        form.addComponent(normCourseOffering);
        
        descriptiveTitleField.setWidth("100%");
        descriptiveTitleField.setRows(3);
        descriptiveTitleField.setInputPrompt("Descriptive Title..");        
        form.addComponent(descriptiveTitleField);    
                        
        Button newCurriculumn = new Button("SAVE");
        newCurriculumn.setWidth("100%");
        newCurriculumn.setIcon(FontAwesome.SAVE);
        newCurriculumn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newCurriculumn.addStyleName(ValoTheme.BUTTON_SMALL);
        newCurriculumn.addClickListener(buttonClickListener);
        
        Button updateCurriculumn = new Button("UPDATE");
        updateCurriculumn.setWidth("100%");
        updateCurriculumn.setIcon(FontAwesome.ADJUST);
        updateCurriculumn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        updateCurriculumn.addStyleName(ValoTheme.BUTTON_SMALL);
        updateCurriculumn.addClickListener(buttonClickListener);        
        
        Button deleteCurriculumn = new Button("DELETE");
        deleteCurriculumn.setWidth("100%");
        deleteCurriculumn.setIcon(FontAwesome.ERASER);
        deleteCurriculumn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        deleteCurriculumn.addStyleName(ValoTheme.BUTTON_SMALL);
        deleteCurriculumn.addClickListener(buttonClickListener);        
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setWidth("100%");
        hlayout.setSpacing(true);
                
        if(getCurriculumId() != 0){
            hlayout.addComponent(updateCurriculumn);
            hlayout.addComponent(deleteCurriculumn);
            Curriculum c = cs.getCurriculumById(getCurriculumId());
            yearLevel.setValue(c.getYearLevel());
            subjectField.setValue(c.getSubject());
            normCourseOffering.setValue(c.getNormCourseOffering());
            descriptiveTitleField.setValue(c.getDescriptiveTitle());
        } else {
            hlayout.addComponent(newCurriculumn);
        }        
        
        form.addComponent(hlayout);
        
        return form;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
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
            
        System.out.println("caption: "+event.getButton().getCaption());
        System.out.println("year level:"+yearLevel.getValue());
        System.out.println("subject: "+subjectField.getValue());
        System.out.println("descriptive: "+descriptiveTitleField.getValue());
        System.out.println("norm: "+normCourseOffering.getValue());
        
        switch (event.getButton().getCaption()) {
            case "SAVE":
                {
                    boolean result = cs.insertNewCurriculum(c);
                    if(result){
                        close();
                    }      
                    break;
                }
            case "UPDATE":
                {
                    boolean result = cs.updateCurriculum(c);
                    if(result){
                        close();
                    }      
                    break;                    
                }
            default:
                boolean result = cs.removeCurriculum(getCurriculumId());
                if(result){
                    close();
                }       
                break;
        }
    };
    
    int getCurriculumId(){
        return curriculumId;
    }
    
    void requiredAllFields(){
        Notification.show("Fill up all Fields!", Notification.Type.ASSISTIVE_NOTIFICATION);
    }
}
