/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.model.Users;
import com.etest.service.FacultyService;
import com.etest.serviceprovider.facultyServiceImpl;
import com.etest.view.systemadministration.faculty.FacultyDataTable;
import com.etest.view.systemadministration.faculty.FacultyFormWindow;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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
public class FacultyMainUI extends VerticalLayout {
    
    FacultyService fs = new facultyServiceImpl();
    Users u;
    Table table = new FacultyDataTable();
    
    private int facultyId;
    private static final String BUTTON_CAPTION_NEW = "ADD NEW FACULTY";
    private static final String BUTTON_CAPTION_UPDATE = "UPDATE FACULTY INFO";
    private static final String BUTTON_CAPTION_DELETE = "DELETE FACULTY";
    
    public FacultyMainUI() {
        setSizeFull();
        setSpacing(true);
        setMargin(true);
                                
        Button formBtn = new Button("NEW FACULTY FORM");
        formBtn.setWidth("230px");
        formBtn.setIcon(FontAwesome.OPENID);
        formBtn.addStyleName(ValoTheme.BUTTON_LINK);
        formBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        formBtn.addClickListener(formBtnClickListener);
        addComponent(formBtn);
        
        addComponent(getDataTablePanel());                                 
    }
           
    Panel getDataTablePanel(){
        Panel panel = new Panel("Faculty");
        panel.setWidth("900px");
        
        populateDataTable();
        panel.setContent(table);
        
        return panel;
    }
        
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(Users u : fs.getAllFaculty()){
            HorizontalLayout hlayout = new HorizontalLayout();
            hlayout.setWidth("100%");
            
            Button edit = new Button("edit");
            edit.setWidth("100%");
            edit.setData(u.getFacultyId());
            edit.setIcon(FontAwesome.PENCIL);
            edit.addStyleName(ValoTheme.BUTTON_LINK);
            edit.addStyleName(ValoTheme.BUTTON_TINY);
            edit.addStyleName("button-container");
            edit.addClickListener(modifyBtnClickListener);
            hlayout.addComponent(edit);
            
            Button delete = new Button("del");
            delete.setWidth("100%");
            delete.setData(u.getFacultyId());
            delete.setIcon(FontAwesome.TRASH_O);
            delete.addStyleName(ValoTheme.BUTTON_LINK);
            delete.addStyleName(ValoTheme.BUTTON_TINY);
            delete.addStyleName("button-container");
            delete.addClickListener(modifyBtnClickListener);
            hlayout.addComponent(delete);
            
            table.addItem(new Object[]{
                u.getName().toUpperCase(), 
                u.getUsername_(), 
                u.getUserType(), 
                hlayout
            }, new Integer(i));
            i++;
        }       
        table.setPageLength(table.size());
        
//        table.getListeners(ItemClickEvent.class).stream().forEach((listener) -> {
//            table.removeListener(ItemClickEvent.class, listener);
//        });
        
//        table.addItemClickListener((ItemClickEvent event) -> {
//            Property itemProperty = event.getItem().getItemProperty("name");
//            facultyId = fs.getFacultyIdByName(itemProperty.getValue().toString());
//            
//            Window sub = new FacultyFormWindow(facultyId);
//            if(sub.getParent() == null){
//                UI.getCurrent().addWindow(sub);
//            }
//        });
        
        return table;
    }
        
    Button.ClickListener formBtnClickListener = (Button.ClickEvent event) -> {
        Window sub = new FacultyFormWindow(0, BUTTON_CAPTION_NEW);
        if(sub.getParent() == null){
            UI.getCurrent().addWindow(sub);
        }
        sub.addCloseListener((Window.CloseEvent e) -> {
            populateDataTable();
        });
    };
             
    Button.ClickListener modifyBtnClickListener = (Button.ClickEvent event) -> {
        Window sub;
        if(event.getButton().getCaption().equals("edit")){
            sub = new FacultyFormWindow((int)event.getButton().getData(), BUTTON_CAPTION_UPDATE);
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }            
        } else {
            sub = new FacultyFormWindow((int)event.getButton().getData(), BUTTON_CAPTION_DELETE);
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }            
        }
        sub.addCloseListener((Window.CloseEvent e) -> {
            populateDataTable();
        });
    };
    
    int getFacultyId(){
        return facultyId;
    }
    
    
}
