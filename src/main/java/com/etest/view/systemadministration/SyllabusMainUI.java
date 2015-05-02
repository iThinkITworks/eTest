/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.model.Syllabus;
import com.etest.service.CurriculumService;
import com.etest.service.SyllabusService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.etest.view.systemadministration.datagrid.SyllabusDataGrid;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SyllabusMainUI extends VerticalLayout {

    ComboBox subjects = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
    TextField topicNo = new CommonTextField("Enter Topic No..");
    TextField estimatedTime = new CommonTextField("Enter Estimated Time..");
    TextArea topic = new TextArea();
    Button formBtn = new Button();
    CheckBox enableNewSyllabusEntry = new CheckBox("Enter New Syllabus");
    
    CurriculumService cs = new CurriculumServiceImpl();
    SyllabusService ss = new SyllabusServiceImpl();
    Syllabus s = new Syllabus();
    Grid grid = new SyllabusDataGrid();
    
    private static final String BUTTON_SAVE_CAPTION = "ENTER NEW SYLLABUS";
    private static final String BUTTON_UPDATE_CAPTION = "UPDATE SYLLABUS";
    
    private int syllabusId;
    
    public SyllabusMainUI() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        
        addComponent(buildSyllabusForms());
        populateDataGrid();
        addComponent(grid);
        
        enableNewSyllabusEntry.setVisible(false);
    }
    
    Component buildSyllabusForms(){
        FormLayout form = new FormLayout();
        form.setWidth("500px");
        form.setSpacing(true);
        
        subjects.setCaption("Subject: ");
        subjects.setWidth("200px");
        subjects.setIcon(FontAwesome.BOOK);
        subjects.addStyleName(ValoTheme.COMBOBOX_SMALL);
        form.addComponent(subjects);
        
        topicNo.setCaption("Topic No: ");
        topicNo.setWidth("200px");
        topicNo.setIcon(FontAwesome.TAG);
        topicNo.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(topicNo);
        
        estimatedTime.setCaption("Estimated Time: ");
        estimatedTime.setWidth("200px");
        estimatedTime.setIcon(FontAwesome.TAG);
        estimatedTime.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(estimatedTime);
        
        topic.setCaption("Topic: ");
        topic.setWidth("100%");
        topic.setIcon(FontAwesome.TAG);
        topic.setInputPrompt("Enter Topic..");
        topic.setRows(3);
        topic.addStyleName(ValoTheme.TEXTAREA_SMALL);
        form.addComponent(topic);
        
        formBtn.setCaption(BUTTON_SAVE_CAPTION);
        formBtn.setWidth("100%");
        formBtn.setIcon(FontAwesome.SAVE);
        formBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        formBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        formBtn.addClickListener(buttonClickListener);
        form.addComponent(formBtn);
        
        enableNewSyllabusEntry.addValueChangeListener(checkBoxValueListener);
        form.addComponent(enableNewSyllabusEntry);
        
        return form;
    }
    
    Grid populateDataGrid(){
        grid.getContainerDataSource().removeAllItems();
        for(Syllabus s: ss.getAllSyllabus()){
            grid.addRow(s.getSyllabusId(), 
                    s.getSubject(), 
                    s.getDescriptiveTitle(), 
                    s.getTopicNo(), 
                    s.getTopic(), 
                    s.getEstimatedTime());
        }
        grid.recalculateColumnWidths();
        
        grid.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Object itemId = event.getItemId();
                Item item = grid.getContainerDataSource().getItem(itemId);
                
                syllabusId = CommonUtilities.convertStringToInt(item.getItemProperty("ID").getValue().toString());
                
                s = ss.getSyllabusById(syllabusId);
                subjects.setValue(s.getCurriculumId());
                topicNo.setValue(String.valueOf(s.getTopicNo()));
                estimatedTime.setValue(String.valueOf(s.getEstimatedTime()));
                topic.setValue(s.getTopic());
                formBtn.setCaption(BUTTON_UPDATE_CAPTION);
                enableNewSyllabusEntry.setVisible(true);
                enableNewSyllabusEntry.setValue(false);
            }
        });
        
        return grid;
    }
    
    Button.ClickListener buttonClickListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {            
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
            
            
            if(topic.getValue() == null || 
                    topic.getValue().trim().isEmpty()){ requiredAllFields(); return; }
                        
            s.setCurriculumId((int) subjects.getValue());
            s.setTopicNo(CommonUtilities.convertStringToInt(topicNo.getValue().trim()));
            s.setEstimatedTime(CommonUtilities.convertStringToFloat(estimatedTime.getValue().trim()));
            s.setTopic(topic.getValue().trim());
            
            if(event.getButton().getCaption().equals(BUTTON_SAVE_CAPTION)){
                boolean result = ss.insertNewSyllabus(s);
                if(result){
                    populateDataGrid();
                    clearForms();
                }
            } else {
                s.setSyllabusId(getSyllabusId());
                boolean result = ss.updateSyllabus(s);
                if(result){
                    populateDataGrid();
                    clearForms();
                    formBtn.setCaption(BUTTON_SAVE_CAPTION);
                }
            }
            
            enableNewSyllabusEntry.setVisible(false);
            enableNewSyllabusEntry.setValue(false);
        }
    };
        
    CheckBox.ValueChangeListener checkBoxValueListener = new CheckBox.ValueChangeListener() {

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if((Boolean)event.getProperty().getValue() == true){
                formBtn.setCaption(BUTTON_SAVE_CAPTION);
                enableNewSyllabusEntry.setValue(false);
                enableNewSyllabusEntry.setVisible(false);
                clearForms();
            }
        }
    };
    
    void requiredAllFields(){
        Notification.show("Required All Fields", Notification.Type.ERROR_MESSAGE);
    }   
    
    void clearForms(){
        topicNo.setValue("");
        estimatedTime.setValue("");
        topic.setValue("");
    }
    
    int getSyllabusId(){
        return syllabusId;
    }
}
