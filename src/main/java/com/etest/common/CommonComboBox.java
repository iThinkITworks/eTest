/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;

/**
 *
 * @author jetdario
 */
public class CommonComboBox {

    Item item;
    
    public CommonComboBox(){        
    }
    
    public static ComboBox getYearLevelComboBox(String inputPrompt){
        ComboBox select = new ComboBox();
        select.setWidth("100%");       
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("y", String.class, "");
        select.setItemCaptionPropertyId("y");
        Item i = select.addItem(1);
        i.getItemProperty("y").setValue("1st Year");
        i = select.addItem(2);
        i.getItemProperty("y").setValue("2nd Year");
        i = select.addItem(3);
        i.getItemProperty("y").setValue("3rd Year");
        i = select.addItem(4);
        i.getItemProperty("y").setValue("4th Year");
        i = select.addItem(5);
        i.getItemProperty("y").setValue("5th Year");
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
    
    public static ComboBox getNormCourseOfferingComboBox(String inputPrompt){
        ComboBox select = new ComboBox();
        select.setWidth("100%");       
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("y", String.class, "");
        select.setItemCaptionPropertyId("y");
        Item i = select.addItem(1);
        i.getItemProperty("y").setValue("First Semester");
        i = select.addItem(2);
        i.getItemProperty("y").setValue("Second Semester");
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
}
