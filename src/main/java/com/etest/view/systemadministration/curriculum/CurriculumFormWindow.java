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
    
    private int curriculumId;
    private String buttonCaption;
    
    public CurriculumFormWindow(int curriculumId, 
            String buttonCaption) {        
        this.curriculumId = curriculumId;
        this.buttonCaption = buttonCaption;
        
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
                        
        Button save = new Button("SAVE");
        save.setWidth("100%");
        save.setIcon(FontAwesome.SAVE);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(buttonClickListener);
        
        Button update = new Button("UPDATE");
        update.setWidth("100%");
        update.setIcon(FontAwesome.ADJUST);
        update.addStyleName(ValoTheme.BUTTON_PRIMARY);
        update.addStyleName(ValoTheme.BUTTON_SMALL);
        update.addClickListener(buttonClickListener);        
        
        Button delete = new Button("DELETE");
        delete.setWidth("100%");
        delete.setIcon(FontAwesome.ERASER);
        delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_SMALL);
        delete.addClickListener(buttonClickListener);        
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setWidth("100%");
        hlayout.setSpacing(true);
                
        if(getCurriculumId() != 0){
            hlayout.addComponent(update);
            hlayout.addComponent(delete);
            Curriculum c = cs.getCurriculumById(getCurriculumId());
            yearLevel.setValue(c.getYearLevel());
            subjectField.setValue(c.getSubject());
            normCourseOffering.setValue(c.getNormCourseOffering());
            descriptiveTitleField.setValue(c.getDescriptiveTitle());
            
            if(getButtonCaption().equals("edit")){
                delete.setVisible(false);
            } else {
                update.setVisible(false);
            }
        } else {
            hlayout.addComponent(save);
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
            
//        System.out.println("caption: "+event.getButton().getCaption());
//        System.out.println("year level:"+yearLevel.getValue());
//        System.out.println("subject: "+subjectField.getValue());
//        System.out.println("descriptive: "+descriptiveTitleField.getValue());
//        System.out.println("norm: "+normCourseOffering.getValue());
        
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
    
    String getButtonCaption(){
        return buttonCaption;
    }
    
    void requiredAllFields(){
        Notification.show("Fill up all Fields!", Notification.Type.ASSISTIVE_NOTIFICATION);
    }
}
