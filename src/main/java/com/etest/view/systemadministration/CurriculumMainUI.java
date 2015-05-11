/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.common.CommonVariableMap;
import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.etest.view.systemadministration.curriculum.CurriculumDataGrid;
import com.etest.view.systemadministration.curriculum.CurriculumDataTable;
import com.etest.view.systemadministration.curriculum.CurriculumFormWindow;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
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
public class CurriculumMainUI extends VerticalLayout {
    
    CurriculumService cs = new CurriculumServiceImpl();
    Grid grid = new CurriculumDataGrid();  
    Table table = new CurriculumDataTable();
    
    ComboBox yearLevel;
    TextField subjectField;
    TextArea descriptiveTitleField;
    ComboBox normCourseOffering;
    
    public CurriculumMainUI() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        
//        addComponent(buildForms());
//        populateDataGrid();
        
        Button addNewCurriculumBtn = new Button("ADD NEW CURRICULUM");
        addNewCurriculumBtn.setWidth("220px");
        addNewCurriculumBtn.setIcon(FontAwesome.OPENID);
        addNewCurriculumBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addNewCurriculumBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        addNewCurriculumBtn.addClickListener(buttonClickListener);
        
        addComponent(addNewCurriculumBtn);        
        addComponent(dataGridPanel());
    }
        
    Panel dataGridPanel(){
        Panel panel = new Panel("List of All Curriculum");
        panel.setWidth("900px");
        
        populateDataTable();
        panel.setContent(table);
                
        return panel;
    }
            
//    Grid populateDataGrid(){
//        grid.getColumn("delete")
//                .setRenderer(new ButtonRenderer(e -> 
//                grid.getContainerDataSource()
//                .removeItem(e.getItemId())));
//        grid.getContainerDataSource().removeAllItems();        
//        for(Curriculum c : cs.getAllCurriculum()){         
//            
//            grid.addRow(c.getCurriculumId(), 
//                    CommonVariableMap.getYearLevel(c.getYearLevel()), 
//                    c.getSubject(), 
//                    c.getDescriptiveTitle(), 
//                    CommonVariableMap.getNormCourseOffering(c.getNormCourseOffering())                     
//            );
//        }        
//        grid.recalculateColumnWidths();
//        
//        return grid;
//    }
    
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(Curriculum c : cs.getAllCurriculum()){
            table.addItem(new Object[]{
                c.getCurriculumId(),
                CommonVariableMap.getYearLevel(c.getYearLevel()), 
                c.getSubject(), 
                c.getDescriptiveTitle(), 
                CommonVariableMap.getNormCourseOffering(c.getNormCourseOffering())
            }, new Integer(i));
            i++;
        }
        table.setPageLength(table.size());
        
        table.getListeners(ItemClickEvent.class).stream().forEach((listener) -> {
            table.removeListener(ItemClickEvent.class, listener);
        });
        
        table.addItemClickListener((ItemClickEvent event) -> {
            Property itemProperty = event.getItem().getItemProperty("id");
            
            Window sub = new CurriculumFormWindow(CommonUtilities.convertStringToInt(itemProperty.getValue().toString()));
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
            sub.addCloseListener((Window.CloseEvent e) -> {
                populateDataTable();
            });
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
            
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        Window sub = new CurriculumFormWindow(0);
        if(sub.getParent() == null){
            UI.getCurrent().addWindow(sub);
        }
        sub.addCloseListener((Window.CloseEvent e) -> {
            populateDataTable();
        });
    };    
}
