/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank;

import com.etest.common.CommonComboBox;
import com.etest.common.CurriculumPropertyChangeListener;
import com.etest.model.CellCase;
import com.etest.service.CellCaseService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CreateNewCellWindow extends Window {

    CellCaseService ccs = new CellCaseServiceImpl();
    ComboBox subject = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
    ComboBox topic = new ComboBox();
    
    public CreateNewCellWindow() {
        setCaption("CREATE CELL CASE");
        setWidth("600px");
        setModal(true);
        center();
        
        setContent(buildForms());
        getContent().setHeightUndefined();
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
                
        subject.setCaption("Subject: ");
        subject.setWidth("60%");
        subject.addValueChangeListener((new CurriculumPropertyChangeListener(topic)));
        form.addComponent(subject);
        
        topic.setCaption("Topic: ");
        topic.setWidth("100%");
        topic.setInputPrompt("Select a Topic..");
        topic.addStyleName(ValoTheme.COMBOBOX_SMALL);
        form.addComponent(topic);
        
        TextArea caseTopic = new TextArea();
        caseTopic.setCaption("Case: ");
        caseTopic.setWidth("100%");
        caseTopic.setRows(4);        
        form.addComponent(caseTopic);
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setWidth("100%");
        
        Button saveBtn = new Button("SAVE");
        saveBtn.setWidth("200px");
        saveBtn.setIcon(FontAwesome.SAVE);
        saveBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        saveBtn.addClickListener((Button.ClickEvent event) -> {
            if(subject.getValue() == null){
                Notification.show("Select a Subject", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            if(topic.getValue() == null){
                Notification.show("Select a Topic", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            CellCase c = new CellCase();
            c.setSyllabusId((int) topic.getValue());
            c.setCaseTopic(caseTopic.getValue().trim());
            c.setUserId(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            
            boolean result = ccs.insertNewCellCase(c);
            if(result){
                close();
            }
        });
        hlayout.addComponent(saveBtn);
        hlayout.setComponentAlignment(saveBtn, Alignment.MIDDLE_RIGHT);
        form.addComponent(hlayout);
        
        return form;
    }
}
