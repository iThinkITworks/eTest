/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.global.ShowErrorNotification;
import com.etest.model.TQItems;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.service.ItemKeyService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.ItemKeyServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class TQCoverageWindow extends Window {

    TQCoverageService tq = new TQCoverageServiceImpl();
    CellCaseService ccs = new CellCaseServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    ItemKeyService k = new ItemKeyServiceImpl();
    TQItems tqItems = new TQItems();
    
    TQListUI tqListUI = new TQListUI();
    private int tqCoverageId;
    private Button status;
    
    public TQCoverageWindow(int tqCoverageId){
        this.tqCoverageId = tqCoverageId;
        
        setCaption("TEST COVERAGE");
        setWidth("800px");
        setHeight("100%");
        setModal(true);
        center();        
        
        setContent(buildForms());
    }

    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        
        int itemNo = 1;
        Label caseTopic;
        Label stem ;
        Map<Integer, Map<Integer, Integer>> tqCoverage = tq.getTQCoverage(getTQCoverageId());
        for (Map.Entry<Integer, Map<Integer, Integer>> tqCases : tqCoverage.entrySet()) {
            Integer tqCaseId = tqCases.getKey();
            
            caseTopic = new Label();
            caseTopic.setValue(ccs.getCellCaseById(tqCaseId).getCaseTopic());
            caseTopic.setContentMode(ContentMode.HTML);                        
            form.addComponent(caseTopic);
            
            Map<Integer, Integer> value = tqCases.getValue();
            for (Map.Entry<Integer, Integer> itemIds : value.entrySet()) {
                Integer itemId = itemIds.getKey();
                Integer itemKeyId = itemIds.getValue();
                
                List<String> keyList = k.getAllItemKey(itemId);
                if(keyList.isEmpty()){
                    ShowErrorNotification.error("No Item Key was found for STEM: \n"
                            +cis.getCellItemById(itemId).getItem());
                    return null;
                }
                
                stem = new Label();            
                stem.setValue(itemNo+". "+cis.getCellItemById(itemId).getItem().replace("{key}", keyList.get(0)));
                stem.setContentMode(ContentMode.HTML);
                form.addComponent(stem);
                
                GridLayout glayout = new GridLayout(2, 2);
                glayout.setWidth("100%");
                glayout.setSpacing(true);

                glayout.addComponent(new Label("A) "+cis.getCellItemById(itemId).getOptionA(), ContentMode.HTML), 0, 0);
                glayout.addComponent(new Label("C) "+cis.getCellItemById(itemId).getOptionC(), ContentMode.HTML), 0, 1);
                glayout.addComponent(new Label("B) "+cis.getCellItemById(itemId).getOptionB(), ContentMode.HTML), 1, 0);
                glayout.addComponent(new Label("D) "+ cis.getCellItemById(itemId).getOptionD(), ContentMode.HTML), 1, 1);
                form.addComponent(glayout);
                
                itemNo++;
            }
        }
        
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        
        Button delete = new Button("DELETE");
        delete.setWidth("200px");
        delete.setIcon(FontAwesome.TRASH_O);
        delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_SMALL);
        delete.addClickListener(buttonClickListener);
        h.addComponent(delete);
        
        Button approve = new Button("APPROVE");
        approve.setWidth("200px");
        approve.setIcon(FontAwesome.THUMBS_UP);
        approve.addStyleName(ValoTheme.BUTTON_PRIMARY);
        approve.addStyleName(ValoTheme.BUTTON_SMALL);
        approve.addClickListener(buttonClickListener);
        h.addComponent(approve);        
        
        if(tq.isTQCoverageApproved(getTQCoverageId())){
            approve.setVisible(false);
        } else {
            approve.setVisible(true);
        }
        
        form.addComponent(h);
        
        return form;
    }
    
    int getTQCoverageId(){
        return tqCoverageId;
    }
    
    Button getStatus(){
        return status;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent e) -> {
        if(e.getButton().getCaption().equals("DELETE")){
            //TODO
            Window sub = confirmDeleteWindow();
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        } else {
            boolean result = tq.approveTQCoverage(getTQCoverageId());
            if(result){
                close();
            }
        }
    };
    
    Window confirmDeleteWindow(){        
        Window sub = new Window("TQ Coverage");
        sub.setWidth("250px");
        sub.setResizable(false);
        sub.setModal(true);
        sub.center();
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
             
        Button delete  = new Button("DELETE TQ?");
        delete.setWidth("100%");
        delete.setIcon(FontAwesome.TRASH_O);
        delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_SMALL);
        delete.addClickListener((Button.ClickEvent event) -> {
            boolean result = tq.deleteTQCoverage(getTQCoverageId());
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
