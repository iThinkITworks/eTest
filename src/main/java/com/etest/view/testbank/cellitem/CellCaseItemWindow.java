/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.etest.model.CellCase;
import com.etest.model.CellItem;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
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
public class CellCaseItemWindow extends Window {

    CellCaseService ccs = new CellCaseServiceImpl();
    CellItemService cis = new CellItemServiceImpl();    
    Table table = new CellItemDataTable();
    
    private int cellCaseId;
    
    public CellCaseItemWindow(int cellCaseId) {
        this.cellCaseId = cellCaseId;
        
        setCaption("CELL CASE ITEM");
        setWidth("800px");
        setHeight("100%");
        setModal(true);
        center();
                
        populateDataTable();
        VerticalLayout v = new VerticalLayout();
        v.setMargin(true);
        v.addComponent(buildForms());
        
        setContent(v);
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        form.setSpacing(true);
                
        CellCase cc = ccs.getCellCaseById(getCellCaseId());
        Panel panel = new Panel();
        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        panel.setContent(new Label("<b>CASE</b>: "+cc.getCaseTopic(), ContentMode.HTML));
        form.addComponent(panel);
        
        form.addComponent(table);
                
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        
        Button create = new Button("CREATE NEW STEM");
        create.setWidth("200px");         
        create.addStyleName(ValoTheme.BUTTON_LINK);
        create.addStyleName(ValoTheme.BUTTON_TINY);
        create.addStyleName(ValoTheme.BUTTON_QUIET);
        create.addClickListener(modifyBtnClickListener);
        h.addComponent(create);
        h.setComponentAlignment(create, Alignment.MIDDLE_RIGHT);
        form.addComponent(h);
        
        return form;
    }
    
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(CellItem ci : cis.getCellItemByCase(getCellCaseId())){
            VerticalLayout v = new VerticalLayout();
            v.setSizeFull();
            
            Button edit = new Button("modify");
            edit.setWidth("100%");
            edit.setData(ci.getCellItemId());
            edit.setIcon(FontAwesome.PENCIL);
            edit.addStyleName(ValoTheme.BUTTON_LINK);
            edit.addStyleName(ValoTheme.BUTTON_TINY);
            edit.addStyleName(ValoTheme.BUTTON_QUIET);
            edit.addClickListener(modifyBtnClickListener);
            v.addComponent(edit);
            v.setComponentAlignment(edit, Alignment.MIDDLE_LEFT);
            
            Button approve = new Button("status");
            approve.setWidth("100%");
            approve.setData(ci.getCellItemId());            
            approve.addStyleName(ValoTheme.BUTTON_LINK);
            approve.addStyleName(ValoTheme.BUTTON_TINY);
            approve.addStyleName(ValoTheme.BUTTON_QUIET);
            v.addComponent(approve);
            v.setComponentAlignment(approve, Alignment.MIDDLE_LEFT);
            
            Button view = new Button("view");
            view.setWidth("100%");
            view.setData(ci.getCellItemId());  
            view.setIcon(FontAwesome.PLAY_CIRCLE);
            view.addStyleName(ValoTheme.BUTTON_LINK);
            view.addStyleName(ValoTheme.BUTTON_TINY);
            view.addStyleName(ValoTheme.BUTTON_QUIET);
            view.addClickListener(modifyBtnClickListener);
            v.addComponent(view);
            v.setComponentAlignment(view, Alignment.MIDDLE_LEFT);
            
            if(ci.getApproveItemStatus() == 0){ approve.setIcon(FontAwesome.THUMBS_DOWN); }
            else { approve.setIcon(FontAwesome.THUMBS_UP); }
            
            Label label = new Label(ci.getItem());
            label.setStyleName("label-padding");
            
            table.addItem(new Object[]{
//                ci.getCellItemId(), 
                label, 
                v
            }, i);
            i++;
        }        
        table.setPageLength(table.size());
        
        return table;
    }
    
    int getCellCaseId(){
        return cellCaseId;
    }
    
    Button.ClickListener modifyBtnClickListener = (Button.ClickEvent event) -> {
        switch (event.getButton().getCaption()) {
            case "CREATE NEW STEM":
                {
                    Window sub = new CellItemWindow(getCellCaseId(), 0);
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }       
                    sub.addCloseListener((Window.CloseEvent e) -> {
                        populateDataTable();
                    });     
                    break;
                }
            case "modify":
            {
                Window sub = new CellItemWindow(getCellCaseId(), (int) event.getButton().getData());
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }       
                sub.addCloseListener((Window.CloseEvent e) -> {
                    populateDataTable();
                });     
                break;
            }
            
            default:{
                Window sub = new ViewStemWindow((int) event.getButton().getData());
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
            }
        }
        
    };
      
}
