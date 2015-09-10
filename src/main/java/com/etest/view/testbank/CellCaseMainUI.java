/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank;

import com.etest.view.testbank.cellitem.CellCaseItemWindow;
import com.etest.common.CommonComboBox;
import com.etest.common.CurriculumPropertyChangeListener;
import com.etest.model.CellCase;
import com.etest.pdfviewer.MultipleChoiceHelpViewer;
import com.etest.service.CellCaseService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
        setWidth("100%");
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
        createCellBtn.setWidthUndefined();
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
        
        Button helpBtn = new Button("HELP");
        helpBtn.setWidthUndefined();
        helpBtn.setIcon(FontAwesome.TASKS);
        helpBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        helpBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        helpBtn.addClickListener((Button.ClickEvent event) -> {
            Window sub = new MultipleChoiceHelpViewer();
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        });
        hlayout.addComponent(helpBtn);
        hlayout.setComponentAlignment(helpBtn, Alignment.MIDDLE_LEFT);
        
        return hlayout;
    }
    
    Panel getCellCasePanel(){
        Panel panel = new Panel();
        panel.setWidth("100%");
        
        populateDataTable();
        panel.setContent(table);
        return panel;
    }
    
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(CellCase cc: ccs.getCellCaseByTopic(getSyllabusId())){
            VerticalLayout v = new VerticalLayout();
            v.setWidth("100%");
            
            Button edit = new Button("modify");
            edit.setSizeFull();
            edit.setData(cc.getCellCaseId());
            edit.setIcon(FontAwesome.PENCIL);
            edit.addStyleName(ValoTheme.BUTTON_LINK);
            edit.addStyleName(ValoTheme.BUTTON_TINY);
            edit.addStyleName(ValoTheme.BUTTON_QUIET);
            edit.addStyleName("button-container");
            edit.addClickListener(modifyBtnClickListener);
            v.addComponent(edit);
            v.setComponentAlignment(edit, Alignment.MIDDLE_LEFT);
            
            Button approve = new Button("status");
            approve.setSizeFull();
            approve.setData(cc.getCellCaseId());            
            approve.addStyleName(ValoTheme.BUTTON_LINK);
            approve.addStyleName(ValoTheme.BUTTON_TINY);
            approve.addStyleName(ValoTheme.BUTTON_QUIET);
            approve.addStyleName("button-container");
            v.addComponent(approve);
            v.setComponentAlignment(approve, Alignment.MIDDLE_LEFT);
            
            if(cc.getApprovalStatus() == 0){ approve.setIcon(FontAwesome.THUMBS_DOWN); }
            else { approve.setIcon(FontAwesome.THUMBS_UP); } 
            
            Button stem = new Button("stems");
            stem.setSizeFull();
            stem.setData(cc.getCellCaseId());   
            stem.setIcon(FontAwesome.BRIEFCASE);
            stem.addStyleName(ValoTheme.BUTTON_LINK);
            stem.addStyleName(ValoTheme.BUTTON_TINY);
            stem.addStyleName(ValoTheme.BUTTON_QUIET);
            stem.addStyleName("button-container");
            stem.addClickListener(stemBtnClickListener);
            v.addComponent(stem);
            v.setComponentAlignment(stem, Alignment.MIDDLE_LEFT);
            
            Label label = new Label(cc.getCaseTopic(),ContentMode.HTML);
            label.setStyleName("label-padding");
            
            table.addItem(new Object[]{
//                cc.getCellCaseId(),
                label, 
                cc.getUsername_(), 
                cc.getDateCreated(), 
                v
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
    
    Button.ClickListener stemBtnClickListener = (Button.ClickEvent event) -> {
        Window sub = new CellCaseItemWindow((int) event.getButton().getData());
        if(sub.getParent() == null){
            UI.getCurrent().addWindow(sub);
        }
    };
}
