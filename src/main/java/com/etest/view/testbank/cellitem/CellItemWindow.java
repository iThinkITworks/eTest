/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.model.CellItem;
import com.etest.service.CellItemService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class CellItemWindow extends Window {

    CellItemService cis = new CellItemServiceImpl();
    
    ComboBox bloomsTaxonomy = CommonComboBox.getBloomsTaxonomy("Select a Blooms Class..");
    TextArea stem;
    TextField keyA;
    TextField optionA;
    TextField keyB;
    TextField optionB;
    TextField keyC;
    TextField optionC;
    TextField keyD;
    TextField optionD;
    
    private int cellCaseId;
    private int cellItemId;
    
    private boolean isStemChanged = false;
    private boolean isBloomsChanged = false;
        
    public CellItemWindow(int cellCaseId, int cellItemId) {
        this.cellCaseId = cellCaseId;
        this.cellItemId = cellItemId;
        
        setCaption("CELL ITEM");
        setWidth("800px");
        setModal(true);
        center();
        
        setContent(buildForms());
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        form.setSpacing(true);
                
        bloomsTaxonomy.setCaption("Blooms Class: ");
        bloomsTaxonomy.setWidth("30%");
        form.addComponent(bloomsTaxonomy);
        
        stem = new TextArea("Stem: ");
        stem.setWidth("100%");
        stem.setRows(5);
        stem.setWordwrap(true);
        form.addComponent(stem);        
        
        optionA = new CommonTextField("add option A", "Option A:");
        form.addComponent(optionA); 
        
        keyA = new CommonTextField("Enter a Key for Option A", "Key A");
        form.addComponent(keyA);               
        
        optionB = new CommonTextField("add option B", "Option B:");
        form.addComponent(optionB);
        
        keyB = new CommonTextField("Enter a Key for Option B", "Key B");
        form.addComponent(keyB);        
        
        optionC = new CommonTextField("add option C", "Option C:");
        form.addComponent(optionC);
        
        keyC = new CommonTextField("Enter a Key for Option C", "Key C");
        form.addComponent(keyC);         
        
        optionD = new CommonTextField("add option D", "Option D:");
        form.addComponent(optionD);  
                
        keyD = new CommonTextField("Enter a Key for Option D", "Key D");
        form.addComponent(keyD); 
        
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        
        Button save = new Button("SAVE");
        save.setWidth("200px");
        save.setIcon(FontAwesome.SAVE);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(saveBtnClickListener);        
                
        Button remove = new Button("REMOVE ITEM?");
        remove.setWidth("200px");
        remove.setIcon(FontAwesome.THUMBS_O_UP);
        remove.addStyleName(ValoTheme.BUTTON_PRIMARY);
        remove.addStyleName(ValoTheme.BUTTON_SMALL);
        remove.addClickListener(removeBtnClickListener);
        
        Button approve = new Button("APPROVE ITEM?");
        approve.setWidth("200px");
        approve.setIcon(FontAwesome.THUMBS_O_UP);
        approve.addStyleName(ValoTheme.BUTTON_PRIMARY);
        approve.addStyleName(ValoTheme.BUTTON_SMALL);
        approve.addClickListener(approveBtnClickListener);
        
        Button edit = new Button("UPDATE");
        edit.setWidth("200px");
        edit.setIcon(FontAwesome.SAVE);
        edit.addStyleName(ValoTheme.BUTTON_PRIMARY);
        edit.addStyleName(ValoTheme.BUTTON_SMALL);
        edit.addClickListener(saveBtnClickListener);
                
        if(getCellItemId() != 0){
            CellItem ci = cis.getCellItemById(getCellItemId());
            bloomsTaxonomy.setValue(ci.getBloomsClassId());
            bloomsTaxonomy.addValueChangeListener((Property.ValueChangeEvent event) -> {
                isBloomsChanged = true;
            });
            
            stem.setValue(ci.getItem());
            stem.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(!stem.getValue().trim().equals(event.getText().trim())){
                    isStemChanged = true;
                }
            });
            
            /**
             * OPTION A
             */
            optionA.setValue(ci.getOptionA());
            boolean isOptionAKeyExist = cis.isKeyExist(getCellItemId(), ci.getOptionA());
            if(isOptionAKeyExist){
                keyA.setValue(cis.getItemKey(getCellItemId(), ci.getOptionA()));                
            } 
            keyA.setData(cis.getItemKeyId(getCellItemId(), ci.getOptionA()));
            
            optionA.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                optionA.removeListener(TextChangeEvent.class, listener);
            });
            
            optionA.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(optionA.getValue() == null || 
                        optionA.getValue().trim().isEmpty()){
                    Notification.show("Option A is Empty!", Notification.Type.WARNING_MESSAGE);
                    return;
                } else {
                    boolean result = cis.modifyItemOption(getCellItemId(), 
                            "OptionA", 
                            event.getText().trim(), 
                            isOptionAKeyExist, 
                            (int) keyA.getData());
                    if(result){
                        Notification.show("Option A was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
            
            keyA.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                keyA.removeListener(TextChangeEvent.class, listener);
            });
            
            keyA.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(event.getText() == null || 
                        event.getText().trim().isEmpty()){
                    if(isOptionAKeyExist){
                        boolean result = cis.removeItemKey((int) keyA.getData());
                        if(result){
                            Notification.show("Key A has been removed!", Notification.Type.TRAY_NOTIFICATION);
//                            close();
                        }
                    }
                } else {
                    boolean result = cis.modifyItemKey((int) keyA.getData(), 
                            getCellItemId(),                             
                            event.getText().trim(), 
                            optionA.getValue().trim(), 
                            isOptionAKeyExist);
                    if(result){
                        Notification.show("Key A was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
            
            /**
             * OPTION B
             */
            optionB.setValue(ci.getOptionB()); 
            boolean isOptionBKeyExist = cis.isKeyExist(getCellItemId(), ci.getOptionB());
            if(cis.isKeyExist(getCellItemId(), ci.getOptionB())){
                keyB.setValue(cis.getItemKey(getCellItemId(), ci.getOptionB()));                
            }
            keyB.setData(cis.getItemKeyId(getCellItemId(), ci.getOptionB()));
            
            optionB.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                optionB.removeListener(TextChangeEvent.class, listener);
            });
            
            optionB.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(optionB.getValue() == null || 
                        optionB.getValue().trim().isEmpty()){
                    Notification.show("Option B is Empty!", Notification.Type.WARNING_MESSAGE);
                    return;
                } else {
                    boolean result = cis.modifyItemOption(getCellItemId(), 
                            "OptionB", 
                            event.getText().trim(), 
                            isOptionBKeyExist, 
                            (int) keyB.getData());
                    if(result){
                        Notification.show("Option B was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
            
            keyB.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                keyB.removeListener(TextChangeEvent.class, listener);
            });
            
            keyB.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(event.getText() == null || 
                        event.getText().trim().isEmpty()){
                    if(isOptionBKeyExist){
                        boolean result = cis.removeItemKey((int) keyB.getData());
                        if(result){
                            Notification.show("Key B has been removed!", Notification.Type.TRAY_NOTIFICATION);
//                            close();
                        }
                    }
                } else {
                    boolean result = cis.modifyItemKey((int) keyB.getData(), 
                            getCellItemId(),                             
                            event.getText().trim(), 
                            optionB.getValue().trim(), 
                            isOptionBKeyExist);
                    if(result){
                        Notification.show("Key B was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
            
            /**
             * OPTION C
             */
            optionC.setValue(ci.getOptionC());   
            boolean isOptionCKeyExist = cis.isKeyExist(getCellItemId(), ci.getOptionC());
            if(cis.isKeyExist(getCellItemId(), ci.getOptionC())){
                keyC.setValue(cis.getItemKey(getCellItemId(), ci.getOptionC()));                
            }
            keyC.setData(cis.getItemKeyId(getCellItemId(), ci.getOptionC()));
            
            optionC.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                optionC.removeListener(TextChangeEvent.class, listener);
            });
            
            optionC.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(optionC.getValue() == null || 
                        optionC.getValue().trim().isEmpty()){
                    Notification.show("Option C is Empty!", Notification.Type.WARNING_MESSAGE);
                    return;
                } else {
                    boolean result = cis.modifyItemOption(getCellItemId(), 
                            "OptionC", 
                            event.getText().trim(), 
                            isOptionCKeyExist, 
                            (int) keyC.getData());
                    if(result){
                        Notification.show("Option C was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
            
            keyC.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                keyC.removeListener(TextChangeEvent.class, listener);
            });
            
            keyC.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(event.getText() == null || 
                        event.getText().trim().isEmpty()){
                    if(isOptionBKeyExist){
                        boolean result = cis.removeItemKey((int) keyC.getData());
                        if(result){
                            Notification.show("Key C has been removed!", Notification.Type.TRAY_NOTIFICATION);
//                            close();
                        }
                    }
                } else {
                    boolean result = cis.modifyItemKey((int) keyC.getData(), 
                            getCellItemId(),                             
                            event.getText().trim(), 
                            optionC.getValue().trim(), 
                            isOptionCKeyExist);
                    if(result){
                        Notification.show("Key C was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
            
            /**
             * OPTION D
             */
            optionD.setValue(ci.getOptionD());
            boolean isOptionDKeyExist = cis.isKeyExist(getCellItemId(), ci.getOptionD());
            if(cis.isKeyExist(getCellItemId(), ci.getOptionD())){
                keyD.setValue(cis.getItemKey(getCellItemId(), ci.getOptionD()));                
            }
            keyD.setData(cis.getItemKeyId(getCellItemId(), ci.getOptionD()));
            
            optionD.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                optionD.removeListener(TextChangeEvent.class, listener);
            });
            
            optionD.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(optionD.getValue() == null || 
                        optionD.getValue().trim().isEmpty()){
                    Notification.show("Option D is Empty!", Notification.Type.WARNING_MESSAGE);
                    return;
                } else {
                    boolean result = cis.modifyItemOption(getCellItemId(), 
                            "OptionD", 
                            event.getText().trim(), 
                            isOptionDKeyExist, 
                            (int) keyD.getData());
                    if(result){
                        Notification.show("Option D was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
                    
            keyD.getListeners(TextChangeEvent.class).stream().forEach((listener) -> {
                keyD.removeListener(TextChangeEvent.class, listener);
            });
            
            keyD.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(event.getText() == null || 
                        event.getText().trim().isEmpty()){
                    if(isOptionBKeyExist){
                        boolean result = cis.removeItemKey((int) keyD.getData());
                        if(result){
                            Notification.show("Key D has been removed!", Notification.Type.TRAY_NOTIFICATION);
//                            close();
                        }
                    }
                } else {
                    boolean result = cis.modifyItemKey((int) keyD.getData(), 
                            getCellItemId(),                             
                            event.getText().trim(), 
                            optionD.getValue().trim(), 
                            isOptionDKeyExist);
                    if(result){
                        Notification.show("Key D was modified", Notification.Type.TRAY_NOTIFICATION);
//                        close();
                    }
                }
            });
                    
            h.addComponent(remove);
            h.setComponentAlignment(remove, Alignment.MIDDLE_RIGHT);
            
            h.addComponent(approve);
            h.setComponentAlignment(approve, Alignment.MIDDLE_RIGHT);
            
            h.addComponent(edit);
            h.setComponentAlignment(edit, Alignment.MIDDLE_RIGHT);            
            form.addComponent(h);
        } else {
            h.addComponent(save);
            h.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
            form.addComponent(h);
        }
        
        return form;
    }
    
    int getCellCaseId(){
        return cellCaseId;
    }
    
    int getCellItemId(){
        return cellItemId;
    }
        
    Button.ClickListener saveBtnClickListener = (Button.ClickEvent event) -> {
        if(bloomsTaxonomy.getValue() == null){
            Notification.show("Bloom Class Required!", Notification.Type.WARNING_MESSAGE);
            return;
        }
        
        if(stem.getValue() == null || stem.getValue().trim().isEmpty()){
            Notification.show("Stem is Required!", Notification.Type.WARNING_MESSAGE);
            return;
        }
        
        if(optionA.getValue() == null || optionA.getValue().isEmpty()){ requiredAllOptions(); return; }
        if(optionB.getValue() == null || optionB.getValue().isEmpty()){ requiredAllOptions(); return; }
        if(optionC.getValue() == null || optionC.getValue().isEmpty()){ requiredAllOptions(); return; }
        if(optionD.getValue() == null || optionD.getValue().isEmpty()){ requiredAllOptions(); return; }
                
        if(keyA.getValue().trim().isEmpty() && keyB.getValue().trim().isEmpty() && 
                keyC.getValue().trim().isEmpty() && keyD.getValue().trim().isEmpty()){
            Notification.show("Provide at least one Key", Notification.Type.ERROR_MESSAGE);
            return;
        }
        
        Map<String, String> keys = new HashMap<>();
        if(keyA.getValue().trim().isEmpty()){            
        } else {
            keys.put(keyA.getValue().trim(), optionA.getValue().trim());
        }
        
        if(keyA.getValue().trim().isEmpty()){
        } else {
            keys.put(keyB.getValue().trim(), optionB.getValue().trim());
        }
        
        if(keyC.getValue().trim().isEmpty()){
        } else {
            keys.put(keyC.getValue().trim(), optionC.getValue().trim());
        }
        
        if(keyD.getValue().trim().isEmpty()){
        } else {
            keys.put(keyD.getValue().trim(), optionD.getValue().trim());
        }
        
//        List<String> list = new ArrayList<>();
//        Collections.shuffle(list);
//        for (String list1 : list) {
//            System.out.println("values in random order: " + list1);
//        }
        
        CellItem ci = new CellItem();
        ci.setCellCaseId(getCellCaseId());
        ci.setBloomsClassId((int) bloomsTaxonomy.getValue());
        ci.setItem(stem.getValue().trim());
        ci.setOptionA(optionA.getValue().trim());
        ci.setOptionB(optionB.getValue().trim());
        ci.setOptionC(optionC.getValue().trim());
        ci.setOptionD(optionD.getValue().trim());
        ci.setUserId(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
        ci.setItemKeys(keys);
        ci.setCellItemId(getCellItemId());
        
        if(event.getButton().getCaption().equals("SAVE")){
            boolean result = cis.insertNewCellItem(ci);
            if(result){
                close();
            }
        } else {    
            if(isStemChanged || isBloomsChanged){
                boolean result = cis.modifyCellItem(ci);
                if(result){
                    close();
                }
            } else {
                Notification.show("No changes was made in Item or Bloom's class.. ", Notification.Type.TRAY_NOTIFICATION);
                close();
            }
            
            
            
            
        }
                
//        String str = stem.getValue().replace("{key}", keyA.getValue());
//        System.out.println(str);
    };
        
    Button.ClickListener approveBtnClickListener = (Button.ClickEvent event) -> {
        boolean result = cis.approveCellItem(cellItemId);
        if(result){
            close();
        }
    };
    
    Button.ClickListener removeBtnClickListener = (Button.ClickEvent event) -> {
        boolean result = cis.removeCellItem(cellItemId);
        if(result){
            close();
        }
    };
    
    void requiredAllOptions(){
        Notification.show("Fill up all  Options!", Notification.Type.WARNING_MESSAGE);
    }    
}
