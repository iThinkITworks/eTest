/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class ModifyCellItemWindow extends Window {

    ComboBox bloomsTaxonomy = CommonComboBox.getBloomsTaxonomy("Select a Blooms Class..");
    TextArea stem;
    TextField key1;
    TextField option1;
    TextField key2;
    TextField option2;
    TextField key3;
    TextField option3;
    TextField key4;
    TextField option4;
    
    private int cellItemId;
    
    public ModifyCellItemWindow(int cellItemId) {
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
        form.addComponent(stem);
        
        key1 = new CommonTextField("Enter a Key..", "Key A: ");
        form.addComponent(key1);
        
        option1 = new CommonTextField("add option A", "Option A: ");
        form.addComponent(option1);
        
        key2 = new CommonTextField("Enter a Key..", "Key B: ");
        form.addComponent(key2);
        
        option2 = new CommonTextField("add option B", "Option B: ");
        form.addComponent(option2);
        
        key3 = new CommonTextField("Enter a Key..", "Key C: ");
        form.addComponent(key3);
        
        option3 = new CommonTextField("add option C", "Option C: ");
        form.addComponent(option3);
        
        key4 = new CommonTextField("Enter a Key..", "Key D: ");
        form.addComponent(key4);
        
        option4 = new CommonTextField("add option D", "Option D: ");
        form.addComponent(option4);
        
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        
        Button save = new Button("SAVE");
        save.setWidth("200px");
        save.setIcon(FontAwesome.SAVE);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(saveBtnClickListener);
        h.addComponent(save);
        h.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
        form.addComponent(h);
        
        return form;
    }
    
    int getCellItemId(){
        return cellItemId;
    }
    
    Button.ClickListener saveBtnClickListener = (Button.ClickEvent event) -> {
        String str = stem.getValue().replace("{key}", key1.getValue());
        System.out.println(str);
    };
}
