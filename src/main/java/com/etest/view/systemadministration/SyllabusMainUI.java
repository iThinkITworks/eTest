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
import com.etest.view.systemadministration.syllabus.SyllabusDataGrid;
import com.etest.view.systemadministration.syllabus.SyllabusDataTable;
import com.etest.view.systemadministration.syllabus.SyllabusFormWindow;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SyllabusMainUI extends VerticalLayout {
    
    CurriculumService cs = new CurriculumServiceImpl();
    SyllabusService ss = new SyllabusServiceImpl();
    Syllabus s = new Syllabus();
    Grid grid = new SyllabusDataGrid();
    Table table = new SyllabusDataTable();
    
    private static final String BUTTON_SAVE_CAPTION = "ENTER NEW SYLLABUS";
    private static final String BUTTON_UPDATE_CAPTION = "UPDATE SYLLABUS";
    
    private int syllabusId;
    
    public SyllabusMainUI() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        
        Button addNew = new Button("ADD NEW SYLLABUS");
        addNew.setWidth("220px");
        addNew.setIcon(FontAwesome.OPENID);
        addNew.addStyleName(ValoTheme.BUTTON_LINK);
        addNew.addStyleName(ValoTheme.BUTTON_SMALL);
        addNew.addClickListener(buttonClickListener);
        
        addComponent(addNew);
        addComponent(dataGridPanel());        
    }
        
    Panel dataGridPanel(){
        Panel panel = new Panel("List of All Syllabus");
        panel.setWidth("1000px");
//        panel.setHeight("500px");
        
        populateDataTable();
        panel.setContent(table);
                
        return panel;
    }
    
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(Syllabus s: ss.getAllSyllabus()){
            HorizontalLayout h = new HorizontalLayout();
            h.setWidth("100%");
            
            Button edit = new Button("edit");
            edit.setWidth("100%");
            edit.setData(s.getSyllabusId());
            edit.setIcon(FontAwesome.PENCIL);
            edit.addStyleName(ValoTheme.BUTTON_LINK);
            edit.addStyleName(ValoTheme.BUTTON_TINY);
            edit.addStyleName("button-container");
            edit.addClickListener(buttonClickListener);
            h.addComponent(edit);
            
            Button delete = new Button("del");
            delete.setWidth("100%");
            delete.setData(s.getSyllabusId());
            delete.setIcon(FontAwesome.TRASH_O);
            delete.addStyleName(ValoTheme.BUTTON_LINK);
            delete.addStyleName(ValoTheme.BUTTON_TINY);
            delete.addStyleName("button-container");
            delete.addClickListener(buttonClickListener);
            h.addComponent(delete);
            
            Label descriptiveTitle = new Label(s.getTopic(),ContentMode.HTML);
            descriptiveTitle.setStyleName("label-padding");
            
            Label topic = new Label(s.getTopic(),ContentMode.HTML);
            topic.setStyleName("label-padding");
            
            table.addItem(new Object[]{
//                s.getSyllabusId(), 
                s.getSubject(), 
                descriptiveTitle, 
                s.getTopicNo(), 
                topic, 
                s.getEstimatedTime(), 
                h
            }, i);
            i++;
        }
        table.setPageLength(table.size());
        
        return table;
    }
    
//    Grid populateDataGrid(){
//        grid.getContainerDataSource().removeAllItems();        
//        for(Syllabus s: ss.getAllSyllabus()){
//            grid.addRow(s.getSyllabusId(), 
//                    s.getSubject(), 
//                    s.getDescriptiveTitle(), 
//                    s.getTopicNo(), 
//                    s.getTopic(), 
//                    s.getEstimatedTime());
//        }
//        grid.recalculateColumnWidths();
//        
//        grid.addItemClickListener((ItemClickEvent event) -> {
//            Object itemId = event.getItemId();
//            Item item = grid.getContainerDataSource().getItem(itemId);
//                        
//            syllabusId = CommonUtilities.convertStringToInt(item.getItemProperty("ID").getValue().toString());
//            
//            s = ss.getSyllabusById(syllabusId);
//            subjects.setValue(s.getCurriculumId());
//            topicNo.setValue(String.valueOf(s.getTopicNo()));
//            estimatedTime.setValue(String.valueOf(s.getEstimatedTime()));
//            topic.setValue(s.getTopic());
//            formBtn.setCaption(BUTTON_UPDATE_CAPTION);
//            enableNewSyllabusEntry.setVisible(true);
//            enableNewSyllabusEntry.setValue(false);
//        });
//        
//        return grid;
//    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        Window sub;
        switch(event.getButton().getCaption()){
            case "ADD NEW SYLLABUS":{
                sub = new SyllabusFormWindow(0, "save");
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
                sub.addCloseListener((Window.CloseEvent e) -> {
                    populateDataTable();
                });
                break;
            }
            
            case "edit":{
                sub = new SyllabusFormWindow((int) event.getButton().getData(), "edit");
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
                sub.addCloseListener((Window.CloseEvent e) -> {
                    populateDataTable();
                });
                break;
            }
            
            default:{
                sub = new SyllabusFormWindow((int) event.getButton().getData(), "del");
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
                sub.addCloseListener((Window.CloseEvent e) -> {
                    populateDataTable();
                });
            }            
        }
    };   
    
    int getSyllabusId(){
        return syllabusId;
    }
}
