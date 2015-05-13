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
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CellCaseMainUI extends VerticalLayout {

    CellCaseService ccs = new CellCaseServiceImpl();
    
    ComboBox subject = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
    ComboBox topic = new ComboBox();
    Table table = new CellCaseDataTable();
    
    private int syllabusId;
    
    public CellCaseMainUI() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
                
        addComponent(getHlayout());
        addComponent(getCellCasePanel());
    }
    
    HorizontalLayout getHlayout(){
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);        
        
        subject.setWidth("200px");
        subject.addValueChangeListener((new CurriculumPropertyChangeListener(topic)));
        hlayout.addComponent(subject);
        hlayout.setComponentAlignment(subject, Alignment.MIDDLE_LEFT);
        
        topic.setInputPrompt("Select a Topic..");
        topic.addStyleName(ValoTheme.COMBOBOX_SMALL);
        topic.setWidth("500px");
        topic.addValueChangeListener((Property.ValueChangeEvent event) -> {
            if(event.getProperty().getValue() == null){                
            } else {
                syllabusId = (int) event.getProperty().getValue();
                populateDataTable();
            }            
        });
        hlayout.addComponent(topic);
        hlayout.setComponentAlignment(topic, Alignment.MIDDLE_LEFT);
        
        Button createCellBtn = new Button("CREATE");
        createCellBtn.setWidth("150px");
        createCellBtn.setIcon(FontAwesome.OPENID);
        createCellBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        createCellBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        createCellBtn.addClickListener((Button.ClickEvent event) -> {
            Window sub = new CellCaseWindow(0);
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
            sub.addCloseListener((Window.CloseEvent e) -> {
                populateDataTable();
            });
        });
        hlayout.addComponent(createCellBtn);
        hlayout.setComponentAlignment(createCellBtn, Alignment.MIDDLE_LEFT);
        
        return hlayout;
    }
    
    Panel getCellCasePanel(){
        Panel panel = new Panel();
        panel.setWidth("900");
        
        populateDataTable();
        panel.setContent(table);
        return panel;
    }
    
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(CellCase cc: ccs.getCellCaseByTopic(getSyllabusId())){
            HorizontalLayout hlayout = new HorizontalLayout();
            hlayout.setSizeFull();
            hlayout.addStyleName("button-container");
            
            Button edit = new Button();
            edit.setWidth("100%");
            edit.setData(cc.getCellCaseId());
            edit.setIcon(FontAwesome.PENCIL);
            edit.addStyleName(ValoTheme.BUTTON_LINK);
            edit.addStyleName(ValoTheme.BUTTON_SMALL);
            edit.addStyleName(ValoTheme.BUTTON_QUIET);
            edit.addClickListener(modifyBtnClickListener);
            hlayout.addComponent(edit);
            
            Button approve = new Button();
            approve.setWidth("100%");
            approve.setData(cc.getCellCaseId());            
            approve.addStyleName(ValoTheme.BUTTON_LINK);
            approve.addStyleName(ValoTheme.BUTTON_SMALL);
            approve.addStyleName(ValoTheme.BUTTON_QUIET);
            hlayout.addComponent(approve);
            
            if(cc.getApprovalStatus() == 0){ approve.setIcon(FontAwesome.THUMBS_DOWN); }
            else { approve.setIcon(FontAwesome.THUMBS_UP); } 
            
            Label label = new Label(cc.getCaseTopic(),ContentMode.HTML);
            label.setStyleName("label-padding");
            
            table.addItem(new Object[]{
                cc.getCellCaseId(),
                label, 
                cc.getUsername_(), 
                cc.getDateCreated(), 
                hlayout
            }, i);
            i++;
        }
        table.setPageLength(table.size());
        
        return table;
    }
    
    int getSyllabusId(){
        return syllabusId;
    }
    
    Button.ClickListener modifyBtnClickListener = (Button.ClickEvent event) -> {
        Window sub = new CellCaseWindow((int) event.getButton().getData());
        if(sub.getParent() == null){
            UI.getCurrent().addWindow(sub);
        }
        sub.addCloseListener((Window.CloseEvent e) -> {
            populateDataTable();
        });
    };
}
