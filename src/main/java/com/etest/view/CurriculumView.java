/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view;

import com.etest.common.CommonTextField;
import com.etest.common.CommonComboBox;
import com.etest.common.CommonVariableMap;
import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.etest.view.systemadministration.datagrid.CurriculumDataGrid;
import com.etest.view.systemadministration.datagrid.CurriculumDataTable;
import com.etest.view.systemadministration.datagrid.CurriculumWindow;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
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
public class CurriculumView extends VerticalLayout implements View {

    CurriculumService cs = new CurriculumServiceImpl();
    
    Table table = new CurriculumDataTable();
    ComboBox yearLevel;
    TextField subjectField;
    TextArea descriptiveTitleField;
    ComboBox normCourseOffering;
    
    public CurriculumView() {
        setWidth("100%");
        setMargin(true);
        setSpacing(true);
                
        yearLevel = CommonComboBox.getYearLevelComboBox("Year Level..");
        yearLevel.setWidth("200px");
        addComponent(yearLevel);
        
        subjectField = new CommonTextField("Subject Name..");
        subjectField.setWidth("200px");
        addComponent(subjectField);
        
        normCourseOffering = CommonComboBox.getNormCourseOfferingComboBox("Normal Course Offering..");
        normCourseOffering.setWidth("200px");
        addComponent(normCourseOffering);
        
        descriptiveTitleField = new TextArea();
        descriptiveTitleField.setWidth("500px");
        descriptiveTitleField.setRows(2);
        descriptiveTitleField.setInputPrompt("Descriptive Title..");
        addComponent(descriptiveTitleField);        
        
        Button saveBtn = new Button("Enter Curriculum");
        saveBtn.setWidth("200px");
        saveBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        saveBtn.addClickListener(buttonClickListener);
        addComponent(saveBtn);
        
        addComponent(getDataTablePanel());
    }

    Panel getDataGridPanel(){
        Panel panel = new Panel("List of All Curriculum");
        panel.setWidth("100%");
        panel.setHeight("500px");
        
        panel.setContent(new CurriculumDataGrid());
                
        return panel;
    }
    
    Panel getDataTablePanel(){
        Panel panel = new Panel("List of All Curriculum");
        panel.setWidth("100%");
        panel.setHeight("500px");
                
        panel.setContent(getCurriculumDataTable());
        return panel;
    }
    
    Table getCurriculumDataTable(){    
        table.removeAllItems();
        int i = 0;
        for(Curriculum c : cs.getAllCurriculum()){
            table.addItem(new Object[]{                
                c.getCurriculumId(), 
                CommonVariableMap.getYearLevel(c.getYearLevel()), 
                c.getSubject(), 
                c.getDescriptiveTitle(), 
                CommonVariableMap.getNormCourseOffering(c.getNormCourseOffering())
            }, new Integer[i]);
            i++;
        }
        
        for(Object listener : table.getListeners(ItemClickEvent.class)){
            table.removeListener(ItemClickEvent.class, listener);
        }
        
        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Object itemId = event.getItemId();
                Item item = table.getItem(itemId);
                
                int curriculumId = CommonUtilities.convertStringToInt(item.getItemProperty("id").getValue().toString());
                Window sub = new CurriculumWindow(curriculumId);
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
                sub.addCloseListener(windowCloseListener);
            }
        });
        
        return table;
    }
    
    void clearFields(){
        subjectField.setValue("");
        descriptiveTitleField.setValue("");
    }
    
    
    void requiredAllFields(){
        Notification.show("Fill up all Fields!", Notification.Type.WARNING_MESSAGE);
    }
            
    Button.ClickListener buttonClickListener = new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            if(yearLevel.getValue() == null){ requiredAllFields(); return; }
            if(subjectField.getValue() == null || 
                    subjectField.getValue().isEmpty()){ requiredAllFields(); return; }
            if(descriptiveTitleField.getValue() == null || 
                    descriptiveTitleField.getValue().isEmpty()){ requiredAllFields(); return; }
            if(normCourseOffering.getValue() == null){ requiredAllFields(); return; }
                
            System.out.println("year level: "+yearLevel.getValue());
            System.out.println("subject: "+subjectField.getValue());
            
            Curriculum c = new Curriculum();
            c.setYearLevel(CommonUtilities.convertStringToInt(yearLevel.getValue().toString()));
            c.setSubject(subjectField.getValue());
            c.setDescriptiveTitle(descriptiveTitleField.getValue());
            c.setNormCourseOffering(CommonUtilities.convertStringToInt(normCourseOffering.getValue().toString()));
                
            boolean result = cs.insertNewCurriculum(c);
            if(result){
                clearFields();
                getCurriculumDataTable();
            }
        }
    };
    
    Window.CloseListener windowCloseListener = new Window.CloseListener(){

        @Override
        public void windowClose(Window.CloseEvent e) {
            getCurriculumDataTable();
        }
            
    };
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO
    }
    
}
