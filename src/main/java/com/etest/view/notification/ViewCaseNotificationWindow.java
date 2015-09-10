/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.notification;

import com.etest.global.ShowErrorNotification;
import com.etest.model.CellItem;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.service.ItemKeyService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.ItemKeyServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Property;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class ViewCaseNotificationWindow extends Window {

    CellCaseService ccs = new CellCaseServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    ItemKeyService k = new ItemKeyServiceImpl();
    private int cellCaseId;
    private int cellItemId;
    private int keyIndex = 0;
    private int keyIndexSize = 0;
    private List<String> keyList;
    private String stem;
    private String itemKey;
    
    Button approvalItemBtn = new Button("Approve ITEM");
    
    public ViewCaseNotificationWindow(int cellCaseId, 
            int cellItemId) {
        this.cellCaseId = cellCaseId;
        this.cellItemId = cellItemId;
        
        setCaption("CELL CASE");
        setWidthUndefined();
        setHeight("100%");
        setModal(true);
        center();
        
        setContent(buildForms());
    }
    
    VerticalLayout buildForms(){
        VerticalLayout v = new VerticalLayout();
        v.setWidth("700px");
        v.setMargin(true);
        v.setSpacing(true);
        
        Label cellCase = new Label();
        cellCase.setValue("<b>Case</b>: "+ccs.getCellCaseById(getCellCaseId()).getCaseTopic());
        cellCase.setContentMode(ContentMode.HTML);
        v.addComponent(cellCase);
        
        Label cellItem = new Label();
        cellItem.setContentMode(ContentMode.HTML);
        
        Button approvalBtn = new Button();
        approvalBtn.setCaption("Approve CASE");
        approvalBtn.setWidthUndefined();
        approvalBtn.addStyleName(ValoTheme.BUTTON_TINY);
        approvalBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        approvalBtn.addClickListener(buttonClickListener);
        if(ccs.getCellCaseById(getCellCaseId()).getApprovalStatus() == 0){ approvalBtn.setVisible(true); }
        else { approvalBtn.setVisible(false); }        
        v.addComponent(approvalBtn);
                
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setWidth("100%");     
        
        approvalItemBtn.setVisible(false);
        approvalItemBtn.setWidthUndefined();
        approvalItemBtn.addStyleName(ValoTheme.BUTTON_TINY);
        approvalItemBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        
        if(getCellItemId() != 0){
            approvalBtn.setCaption("Approve ITEM");
            CellItem ci = cis.getCellItemById(getCellItemId());        
            keyList = k.getAllItemKey(getCellItemId());
            keyIndexSize = keyList.size();
            if(keyList.isEmpty()){
                ShowErrorNotification.error("No Item Key was found for STEM: \n"
                        +ci.getItem());
                return null;
            }
            stem = ci.getItem().replace("{key}", "<u>"+keyList.get(getKeyIndex())+"</u>");
            cellItem.setValue("<b>STEM</b>: "+getStem());
            OptionGroup options = new OptionGroup();
            options.addItems(cis.getCellItemById(getCellItemId()).getOptionA(), 
                    cis.getCellItemById(getCellItemId()).getOptionB(), 
                    cis.getCellItemById(getCellItemId()).getOptionC(), 
                    cis.getCellItemById(getCellItemId()).getOptionD());                
            h1.addComponent(options);
            h1.setComponentAlignment(options, Alignment.MIDDLE_CENTER);
            
            if(cis.getCellItemById(getCellItemId()).getCellItemStatus() == 0){ approvalItemBtn.setVisible(true); }
            else { approvalItemBtn.setVisible(false); }
            approvalItemBtn.addClickListener(buttonClickListener);
            approvalItemBtn.setVisible(true);
        }                       
        v.addComponent(approvalBtn);
        v.addComponent(cellItem);
        v.addComponent(h1);
        v.addComponent(approvalItemBtn);
        
        Label separator = new Label("<HR>");
        separator.setContentMode(ContentMode.HTML);
        v.addComponent(separator);
        
        return v;
    }
    
    int getCellCaseId(){
        return cellCaseId;
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
        keyList = k.getAllItemKey(getCellItemId());
        itemKey = keyList.get(getKeyIndex());
        stem = ci.getItem().replace("{key}", getItemKey());
        return stem;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(event.getButton().getCaption().equals("Approve CASE")){
            boolean result = ccs.approveCellCase(getCellCaseId());
            if(result){
                close();
            }
        } else {
            close();
//            boolean result = cis.approveCellItem(getCellItemId());
//            if(result){
//                close();
//            }
        }
    };
}
