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
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CellCaseWindow extends Window {

    CellCaseService ccs = new CellCaseServiceImpl();
    ComboBox subject = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
    ComboBox topic = new ComboBox();
    TextArea caseTopic;
    
    private int cellCaseId;
    
    public CellCaseWindow(int cellCaseId) {
        this.cellCaseId = cellCaseId;
        
        setCaption("CELL CASE");
        setWidth("800px");
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
        subject.setWidth("50%");
        subject.addValueChangeListener((new CurriculumPropertyChangeListener(topic)));
        form.addComponent(subject);
        
        topic.setCaption("Topic: ");
        topic.setWidth("80%");
        topic.setInputPrompt("Select a Topic..");
        topic.addStyleName(ValoTheme.COMBOBOX_SMALL);
        form.addComponent(topic);
        
        caseTopic = new TextArea();
        caseTopic.setCaption("Case: ");
        caseTopic.setWidth("100%");    
        caseTopic.setRows(5);
        form.addComponent(caseTopic);
                
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setWidth("100%");       
        hlayout.setSpacing(true);
        
        Button save = new Button("SAVE");
        save.setWidth("200px");
        save.setIcon(FontAwesome.SAVE);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(buttonClickListener);  
        
        Button modify = new Button("MODIFY");
        modify.setWidth("200px");
        modify.setIcon(FontAwesome.EDIT);
        modify.addStyleName(ValoTheme.BUTTON_PRIMARY);
        modify.addStyleName(ValoTheme.BUTTON_SMALL);
        modify.addClickListener(buttonClickListener);
        
        Button approve = new Button("APPROVE");
        approve.setWidth("200px");
        approve.setIcon(FontAwesome.THUMBS_UP);
        approve.addStyleName(ValoTheme.BUTTON_PRIMARY);
        approve.addStyleName(ValoTheme.BUTTON_SMALL);
        approve.addClickListener(buttonClickListener);
        
        Button delete = new Button("DELETE");
        delete.setWidth("200px");
        delete.setIcon(FontAwesome.ERASER);
        delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_SMALL);
        delete.addClickListener(buttonClickListener);
        
        if(getCellCaseId() != 0){
            CellCase cc = ccs.getCellCaseById(getCellCaseId());
            subject.setValue(cc.getCurriculumId());
            topic.setValue(cc.getSyllabusId());
            caseTopic.setValue(cc.getCaseTopic());
                        
            hlayout.addComponent(approve);
            hlayout.setComponentAlignment(approve, Alignment.MIDDLE_RIGHT);
            
            hlayout.addComponent(modify);
            hlayout.setComponentAlignment(modify, Alignment.MIDDLE_RIGHT);
            
            hlayout.addComponent(delete);
            hlayout.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
        } else {
            hlayout.addComponent(save);
            hlayout.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
        }
                
        form.addComponent(hlayout);
        form.setComponentAlignment(hlayout, Alignment.MIDDLE_RIGHT);
        
        return form;
    }
    
    int getCellCaseId(){
        return cellCaseId;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
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
        c.setCellCaseId(getCellCaseId());
        
        switch (event.getButton().getCaption()){
            case "SAVE": 
                {
                    boolean result = ccs.insertNewCellCase(c);
                    if(result){
                        close();
                    }
                    break;
                }
            case "MODIFY":
                {
                    boolean result = ccs.modifyCellCase(c);
                    if(result){
                        close();
                    }
                    break;
                }
            case "APPROVE":
                {
                    boolean result = ccs.approveCellCase(getCellCaseId());
                    if(result){
                        close();
                    }
                    break;
                }
            default: 
            {
                boolean result = ccs.removeCellCase(getCellCaseId());
                if(result){
                    close();
                }
                break;
            }
        }        
    };
}
