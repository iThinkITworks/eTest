/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank;

import com.etest.administrator.UserAccess;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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
//        System.out.println("user type: "+VaadinSession.getCurrent().getAttribute("userType"));
//        System.out.println("access: "+UserAccess.approve());
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
        approve.setEnabled(UserAccess.approve());
        approve.addClickListener(buttonClickListener);
        
        Button delete = new Button("DELETE");
        delete.setWidth("200px");
        delete.setIcon(FontAwesome.TRASH_O);
        delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_SMALL);
        delete.setEnabled(UserAccess.delete());
        delete.addClickListener(buttonClickListener);
        
        if(getCellCaseId() != 0){
            CellCase cc = ccs.getCellCaseById(getCellCaseId());
            subject.setValue(cc.getCurriculumId());
            topic.setValue(cc.getSyllabusId());
            caseTopic.setValue(cc.getCaseTopic());
                        
            approve.setVisible(cc.getApprovalStatus() == 0);
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
                
        CellCase cellCase = new CellCase();
        cellCase.setSyllabusId((int) topic.getValue());
        cellCase.setCaseTopic(CommonUtilities.escapeSingleQuote(caseTopic.getValue().trim()));
        cellCase.setUserId(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));        
        
        switch (event.getButton().getCaption()){
            case "SAVE": 
                {
                    boolean result = ccs.insertNewCellCase(cellCase);
                    if(result){
                        close();
                    }
                    break;
                }
            case "MODIFY":
                {
                    cellCase.setCellCaseId(getCellCaseId());
                    Window sub = modifyCaseWindow(cellCase);
                    break;
                }
            case "APPROVE":
                {
                    cellCase.setCellCaseId(getCellCaseId());
                    boolean result = ccs.approveCellCase(getCellCaseId());
                    if(result){
                        close();
                    }
                    break;
                }
            default: 
            {
                Window sub = deleteCaseWindow();
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
                break;
            }
        }        
    };
    
    Window modifyCaseWindow(CellCase cellCase){
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        v.setSpacing(true);
        
        Window sub = new Window("MODIFY");
        sub.setWidth("400px");
        sub.setModal(true);
        sub.center();
        
        ComboBox actionDone = new ComboBox("Action: ");
        actionDone.setWidth("70%");
        actionDone.addStyleName(ValoTheme.COMBOBOX_SMALL);
        actionDone.setNullSelectionAllowed(false);
        actionDone.addItem("resolved");
        actionDone.addItem("clarified");
        actionDone.addItem("modified");
        actionDone.setImmediate(true);
        v.addComponent(actionDone);
        
        TextArea remarks = new TextArea("Remarks: ");
        remarks.setWidth("100%");
        remarks.setRows(3);
        v.addComponent(remarks);
        
        Button modify = new Button("UPDATE");
        modify.setWidth("70%");
        modify.setIcon(FontAwesome.EDIT);
        modify.addStyleName(ValoTheme.BUTTON_PRIMARY);
        modify.addStyleName(ValoTheme.BUTTON_SMALL);
        modify.addClickListener((Button.ClickEvent event) -> { 
            if(remarks.getValue() == null || remarks.getValue().trim().isEmpty()){
                Notification.show("Add remarks!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            if(actionDone.getValue() == null){
                Notification.show("Add action!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            cellCase.setActionDone(actionDone.getValue().toString());
            cellCase.setRemarks(remarks.getValue().trim());
            boolean result = ccs.modifyCellCase(cellCase);
            if(result){
                Notification.show("Case has been Modified!", Notification.Type.TRAY_NOTIFICATION);
                sub.close();
                close();
            }
        });
        v.addComponent(modify);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }

    Window deleteCaseWindow(){
        Window sub = new Window("DELETE");
        sub.setWidth("250px");
        sub.setModal(true);
        sub.center();
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        
        Button delete = new Button("DELETE CASE?");
        delete.setWidth("100%");
        delete.setImmediate(true);
        delete.addClickListener((Button.ClickEvent event) -> {
            boolean result = ccs.removeCellCase(getCellCaseId());
            if(result){
                sub.close();
                close();
            }
        });
        
        v.addComponent(delete);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
}
