/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.etest.model.CellItem;
import com.etest.service.CellItemService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class TQWindow extends Window {

    CellItemService cis = new CellItemServiceImpl();
    private int cellItemId;
    private int keyIndex = 0;
    private int keyIndexSize = 0;
    private List<String> keyList;
    private String stem;
    private String itemKey;
    
    Button prev;
    Button next;
    Label label = new Label();
    
    public TQWindow(int cellItemId) {
        this.cellItemId = cellItemId;
        
        setCaption("TEST QUESTIONAIRE");
        setWidth("800px");
        setModal(true);
        center();        
        
        setContent(buildForms());
        getContent().setHeightUndefined();
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        form.setSpacing(true);
    
        CellItem ci = cis.getCellItemById(getCellItemId());        
        keyList = cis.getAllItemKey(getCellItemId());
        keyIndexSize = keyList.size();
        stem = ci.getItem().replace("{key}", keyList.get(getKeyIndex()));
                
        Panel panel = new Panel();
        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        
        label.setValue("<b>STEM</b>: "+getStem());
        label.setContentMode(ContentMode.HTML);
        panel.setContent(label);label.setValue("<b>STEM</b>: "+getStem());
        form.addComponent(label);
        
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        
        OptionGroup options = new OptionGroup();
        options.addItems(ci.getOptionA(), ci.getOptionB(), ci.getOptionC(), ci.getOptionD());
        options.addValueChangeListener((Property.ValueChangeEvent event) -> {    
            boolean result = cis.isAnswerCorrect(getCellItemId(), getItemKey(),event.getProperty().getValue().toString());
            if(result){
                Notification.show("Correct Answer!", Notification.Type.TRAY_NOTIFICATION);
            } else {
                Notification.show("Wrong Answer", Notification.Type.TRAY_NOTIFICATION);
            }
        });
        h.addComponent(options);
        h.setComponentAlignment(options, Alignment.MIDDLE_CENTER);
        form.addComponent(h);
        
        GridLayout g = new GridLayout(2, 1);
        g.setWidth("100%");
        
        prev = new Button();
        prev.setWidth("50px");
        prev.setIcon(FontAwesome.TOGGLE_LEFT);
        prev.addStyleName(ValoTheme.BUTTON_PRIMARY);
        prev.addStyleName(ValoTheme.BUTTON_SMALL);
        prev.addClickListener(prevBtnClickListener);
        g.addComponent(prev, 0, 0);
        g.setComponentAlignment(prev, Alignment.MIDDLE_LEFT);
        
        next = new Button();
        next.setWidth("50px");
        next.setIcon(FontAwesome.TOGGLE_RIGHT);
        next.addStyleName(ValoTheme.BUTTON_PRIMARY);
        next.addStyleName(ValoTheme.BUTTON_SMALL);
        next.addClickListener(nextBtnClickListener);
        g.addComponent(next, 1, 0);
        g.setComponentAlignment(next, Alignment.MIDDLE_RIGHT);
        
        if(getKeyIndexSize() == 1){
            prev.setEnabled(false);
            next.setEnabled(false);
        }
        
        form.addComponent(g);
        
        return form;
    }
    
    Panel getStemPanel(){
        Panel panel = new Panel();
        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        
        label.setValue("<b>STEM</b>: "+getStem());
        label.setContentMode(ContentMode.HTML);
        panel.setContent(label);
        
        return panel;
    }
    
    int getCellItemId(){
        return cellItemId;
    }
    
    int getKeyIndex(){
        return keyIndex;
    }
    
    int getKeyIndexSize(){
        return keyIndexSize;
    }
    
    List<String> getKeyList(){
        return keyList;
    }
    
    String getItemKey(){
        return itemKey;
    }
    
    String getStem(){
        CellItem ci = cis.getCellItemById(getCellItemId());
        keyList = cis.getAllItemKey(getCellItemId());
        itemKey = keyList.get(getKeyIndex());
        stem = ci.getItem().replace("{key}", getItemKey());
        return stem;
    }
    
    Button.ClickListener prevBtnClickListener = (Button.ClickEvent event) -> {
        keyIndex--;
        if(getKeyIndex() == 0){
            prev.setEnabled(false);
            next.setEnabled(true);
        } else {
            prev.setEnabled(true);
            next.setEnabled(true);
        }
                
        label.setValue("<b>STEM</b>: "+getStem());
    };
    
    Button.ClickListener nextBtnClickListener = (Button.ClickEvent event) -> {  
        keyIndex++;
        if((getKeyIndex()+1) == getKeyIndexSize()){
            next.setEnabled(false);
            prev.setEnabled(true);
        } else {            
            next.setEnabled(true);
            prev.setEnabled(true);
        }
        
        label.setValue("<b>STEM</b>: "+getStem());
    };
}
