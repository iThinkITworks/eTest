/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.etest.model.CellItem;
import com.etest.model.ItemKeys;
import com.etest.service.CellItemService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.utilities.CommonUtilities;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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
    
    Table table = new ItemKeyTable();
    
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
                
//        Panel panel = new Panel();
//        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        
        label.setValue("<b>STEM</b>: "+getStem());
        label.setContentMode(ContentMode.HTML);
//        panel.setContent(label);label.setValue("<b>STEM</b>: "+getStem());
        form.addComponent(label);
        
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setWidth("100%");
        
        OptionGroup options = new OptionGroup();
        options.addItems(ci.getOptionA(), ci.getOptionB(), ci.getOptionC(), ci.getOptionD());
        options.addValueChangeListener((Property.ValueChangeEvent event) -> {    
            boolean result = cis.isAnswerCorrect(getCellItemId(), 
                    getItemKey(), 
                    CommonUtilities.escapeSingleQuote(event.getProperty().getValue()));
            if(result){
                Notification.show("Correct Answer!", Notification.Type.TRAY_NOTIFICATION);
            } else {
                Notification.show("Wrong Answer", Notification.Type.TRAY_NOTIFICATION);
            }
        });
        h1.addComponent(options);
        h1.setComponentAlignment(options, Alignment.MIDDLE_CENTER);
        form.addComponent(h1);
        
        GridLayout g = new GridLayout(2, 2);
        g.setWidth("100%");
        g.setSpacing(true);
        
        prev = new Button();
        prev.setWidth("50px");
        prev.setIcon(FontAwesome.ANGLE_DOUBLE_LEFT);
        prev.addStyleName(ValoTheme.BUTTON_PRIMARY);
        prev.addStyleName(ValoTheme.BUTTON_SMALL);
        prev.addClickListener(prevBtnClickListener);
        g.addComponent(prev, 0, 0);
        g.setComponentAlignment(prev, Alignment.MIDDLE_LEFT);
        
        next = new Button();
        next.setWidth("50px");
        next.setIcon(FontAwesome.ANGLE_DOUBLE_RIGHT);
        next.addStyleName(ValoTheme.BUTTON_PRIMARY);
        next.addStyleName(ValoTheme.BUTTON_SMALL);
        next.addClickListener(nextBtnClickListener);
        g.addComponent(next, 1, 0);
        g.setComponentAlignment(next, Alignment.MIDDLE_RIGHT);
        
        if(getKeyIndexSize() == 1){
            prev.setEnabled(false);
            next.setEnabled(false);
        }
        
        populateDataTable();
        g.addComponent(table, 0, 1, 1, 1);
        g.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
        form.addComponent(g);
        
        
        
        return form;
    }
    
//    Panel getStemPanel(){
//        Panel panel = new Panel();
//        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
//        panel.setWidth("400px");
//        
//        label.setValue("<b>STEM</b>: "+getStem());
//        label.setContentMode(ContentMode.HTML);
//        panel.setContent(label);
//        
//        return panel;
//    }
    
    Panel getDataTablePanel(){
        Panel panel = new Panel();
        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        
        populateDataTable();
        panel.setContent(table);
        
        return panel;
    }
    
    Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(ItemKeys key : cis.getItemKeysByCellItemId(getCellItemId())){
            Button delete = new Button("remove");
            delete.setWidth("100%");
            delete.setData(key.getItemKeyId());
            delete.setIcon(FontAwesome.TRASH_O);
            delete.addStyleName(ValoTheme.BUTTON_LINK);
            delete.addStyleName(ValoTheme.BUTTON_TINY);
            delete.addStyleName(ValoTheme.BUTTON_QUIET);
            delete.addClickListener(removeBtnClickListener);
            
            table.addItem(new Object[]{
                key.getItemKey(), 
                key.getAnswer(), 
                delete
            }, i);
            i++;
        }
        table.setPageLength(table.size());
        
        return table;
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
    
    Button.ClickListener removeBtnClickListener = (Button.ClickEvent event) -> {
        Window sub = removeKeyWindow((int) event.getButton().getData());
        if(sub.getParent() == null){
            UI.getCurrent().addWindow(sub);
        }
        sub.addCloseListener((Window.CloseEvent e) -> {
            populateDataTable();
        });
    };
    
    Window removeKeyWindow(int itemKeyId){
        Window sub = new Window("REMOVE KEY");
        sub.setWidth("300px");
        sub.setModal(true);
        sub.center();
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        
        Button remove = new Button("CONFIRM REMOVE KEY?");
        remove.setWidth("100%");
        remove.setIcon(FontAwesome.TRASH_O);
        remove.addStyleName(ValoTheme.BUTTON_PRIMARY);
        remove.addStyleName(ValoTheme.BUTTON_SMALL);
        remove.addClickListener((Button.ClickEvent event) -> {
            boolean result = cis.removeItemKey(itemKeyId);
            if(result){
                sub.close();
                close();
            }
        });
        v.addComponent(remove);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
}
