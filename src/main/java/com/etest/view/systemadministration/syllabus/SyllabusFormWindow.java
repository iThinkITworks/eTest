/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.syllabus;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.model.Syllabus;
import com.etest.service.CurriculumService;
import com.etest.service.SyllabusService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SyllabusFormWindow extends Window {

    CurriculumService cs = new CurriculumServiceImpl();
    SyllabusService ss = new SyllabusServiceImpl();
    Syllabus s = new Syllabus();
    
    ComboBox subjects = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
    TextField topicNo = new CommonTextField("Enter Topic No..", null);
    TextField estimatedTime = new CommonTextField("Enter Estimated Time..", null);
    TextArea topic = new TextArea();
    CheckBox enableNewSyllabusEntry = new CheckBox("Enter New Syllabus");
    
    private int syllabusId;
    private String buttonCaption;
    
    public SyllabusFormWindow(int syllabusId, 
            String buttonCaption) {
        this.syllabusId = syllabusId;
        this.buttonCaption = buttonCaption;
        
        setCaption("SYLLABUS FORM");
        setWidth("600px");
        setResizable(false);
        setModal(true);
        center();
        
        setContent(buildSyllabusForms());
        getContent().setHeightUndefined();
    }
    
    Component buildSyllabusForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        
        subjects.setCaption("Subject: ");
        subjects.setWidth("50%");
        subjects.setIcon(FontAwesome.BOOK);
        subjects.addStyleName(ValoTheme.COMBOBOX_SMALL);
        form.addComponent(subjects);
        
        topicNo.setCaption("Topic No: ");
        topicNo.setWidth("50%");
        topicNo.setIcon(FontAwesome.TAG);
        topicNo.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(topicNo);
        
        topic.setCaption("Topic: ");
        topic.setWidth("100%");
        topic.setIcon(FontAwesome.TAG);
        topic.setInputPrompt("Enter Topic..");
        topic.setRows(3);
        topic.addStyleName(ValoTheme.TEXTAREA_SMALL);
        form.addComponent(topic);
        
        estimatedTime.setCaption("Estimated Time: ");
        estimatedTime.setWidth("50%");
        estimatedTime.setIcon(FontAwesome.TAG);
        estimatedTime.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(estimatedTime);
               
        Button save = new Button("SAVE");
        save.setWidth("50%");
        save.setIcon(FontAwesome.SAVE);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(buttonClickListener);
        
        Button update = new Button("UPDATE");
        update.setWidth("60%");
        update.setIcon(FontAwesome.PENCIL);
        update.addStyleName(ValoTheme.BUTTON_PRIMARY);
        update.addStyleName(ValoTheme.BUTTON_SMALL);
        update.addClickListener(buttonClickListener);
        
        Button remove = new Button("REMOVE");
        remove.setWidth("60%");
        remove.setIcon(FontAwesome.TRASH_O);
        remove.addStyleName(ValoTheme.BUTTON_PRIMARY);
        remove.addStyleName(ValoTheme.BUTTON_SMALL);
        remove.addClickListener(buttonClickListener);
        
        if(getSyllabusId() != 0){
            s = ss.getSyllabusById(syllabusId);
            subjects.setValue(s.getCurriculumId());
            topicNo.setValue(String.valueOf(s.getTopicNo()));
            estimatedTime.setValue(String.valueOf(s.getEstimatedTime()));
            topic.setValue(s.getTopic());
            
            if(getButtonCaption().equals("edit")){
                form.addComponent(update);
            } else {
                form.addComponent(remove);
            }
        } else {
            form.addComponent(save);
        }        
                
        return form;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(subjects.getValue() == null){ requiredAllFields(); return; }
        
        if(topicNo.getValue() == null ||
                topicNo.getValue().trim().isEmpty()){
            requiredAllFields(); return;
        } else {
            if(!CommonUtilities.checkInputIfInteger(topicNo.getValue().trim())){
                Notification.show("Enter a numeric format for Topic No.", Notification.Type.ERROR_MESSAGE);
                return;
            }
        }
        if(estimatedTime.getValue() == null ||
                estimatedTime.getValue().trim().isEmpty()){
            requiredAllFields();
            return;
        } else {
            if(!CommonUtilities.checkInputIfFloat(estimatedTime.getValue().trim())){
                Notification.show("Enter a numeric format for Estimated Time.", Notification.Type.ERROR_MESSAGE);
                return;
            }
        }
                
        if(topic.getValue() == null || topic.getValue().trim().isEmpty()){ requiredAllFields(); return; }
        
        s.setCurriculumId((int) subjects.getValue());
        s.setTopicNo(CommonUtilities.convertStringToInt(topicNo.getValue().trim()));
        s.setEstimatedTime(CommonUtilities.convertStringToFloat(estimatedTime.getValue().trim()));
        s.setTopic(topic.getValue().trim());
        
        switch(event.getButton().getCaption()){
            case "SAVE":{
                boolean result = ss.insertNewSyllabus(s);
                if(result){
                    close();
                }
                break;
            }
            
            case "UPDATE":{
                s.setSyllabusId(getSyllabusId());
                boolean result = ss.updateSyllabus(s);
                if(result){
                    close();
                }
                break;
            }
            
            default:{
                boolean result = ss.removeSyllabus(getSyllabusId());
                if(result){
                    close();
                }
                break;
            }
        }
    };
      
    void requiredAllFields(){
        Notification.show("Required All Fields", Notification.Type.ERROR_MESSAGE);
    }
    
    int getSyllabusId(){
        return syllabusId;
    }
    
    String getButtonCaption(){
        return buttonCaption;
    }
}
