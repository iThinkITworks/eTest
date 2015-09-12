/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration;

import com.etest.model.CellItem;
import com.etest.service.HousekeepingService;
import com.etest.serviceprovider.HousekeepingServieImpl;
import com.etest.view.systemadministration.housekeeping.HousekeepingDataTable;
import com.etest.view.testbank.cellitem.ViewStemWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class HousekeepingMainUI extends VerticalLayout {

    HousekeepingService hs = new HousekeepingServieImpl();
    Table table = new HousekeepingDataTable();
    
    public HousekeepingMainUI() {
        setSizeFull();
        setSpacing(true);
        
        addComponent(populateDataTable());
    }
    
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(CellItem ci : hs.getAllItemsFromArchive()){
            VerticalLayout v = new VerticalLayout();
            v.setWidth("100%");
            
            Button recycle = new Button("recycle");
            recycle.setWidthUndefined();
            recycle.setData(ci.getCellItemId());
            recycle.setIcon(FontAwesome.RECYCLE);
            recycle.addStyleName(ValoTheme.BUTTON_LINK);
            recycle.addStyleName(ValoTheme.BUTTON_TINY);
            recycle.addStyleName(ValoTheme.BUTTON_QUIET);
            recycle.addStyleName("button-container");
            v.addComponent(recycle);
            v.setComponentAlignment(recycle, Alignment.MIDDLE_LEFT);
            
            Button view = new Button("view");
            view.setWidthUndefined();
            view.setData(ci.getCellItemId());
            view.setIcon(FontAwesome.COFFEE);
            view.addStyleName(ValoTheme.BUTTON_LINK);
            view.addStyleName(ValoTheme.BUTTON_TINY);
            view.addStyleName(ValoTheme.BUTTON_QUIET);
            view.addStyleName("button-container");
            view.addClickListener(buttonClickListener);
            v.addComponent(view);
            v.setComponentAlignment(view, Alignment.MIDDLE_LEFT);
            
            table.addItem(new Object[]{
                ci.getCellItemId(), 
                ci.getItem(),  
                v
            }, i);
            i++;
        }
        table.setPageLength(table.size());
        
        return table;
    }    
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(event.getButton().getCaption().equals("view")){
            Window sub = new ViewStemWindow((int) event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        }
    };
}
