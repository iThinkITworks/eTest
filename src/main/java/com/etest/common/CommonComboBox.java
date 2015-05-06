/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import com.etest.dao.CurriculumDAO;
import com.etest.dao.FacultyDAO;
import com.etest.dao.TeamTeachDAO;
import com.etest.model.Users;
import com.etest.service.CurriculumService;
import com.etest.service.FacultyService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.facultyServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class CommonComboBox {

    CurriculumService cs = new CurriculumServiceImpl();
    FacultyService fs = new facultyServiceImpl();
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
    
    public static ComboBox getSubjectFromCurriculum(String inputPrompt){
        ComboBox select = new ComboBox();
        select.setWidth("100%");       
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("y", String.class, "");
        select.setItemCaptionPropertyId("y");
        Item i;
        for(Map.Entry<Integer, String> entry : CurriculumDAO.getSubjectsFromCurriculum().entrySet()){
            i = select.addItem(entry.getKey());
            i.getItemProperty("y").setValue(entry.getValue());
        }
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
    
    public static ComboBox getAllFaculty(String inputPrompt){
        ComboBox select = new ComboBox();
        select.setWidth("100%");
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("y", String.class, "");
        select.setItemCaptionPropertyId("y");
        Item i;
        for(Users u : FacultyDAO.getAllFaculty()){
            i = select.addItem(u.getFacultyId());
            i.getItemProperty("y").setValue(u.getName());
        }
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
    
    public static ComboBox getFacultyPosition(String inputPrompt){
        ComboBox select = new ComboBox();
        select.setWidth("100%");       
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("y", String.class, "");
        select.setItemCaptionPropertyId("y");
        Item i = select.addItem(1);
        i.getItemProperty("y").setValue("Faculty");
        i = select.addItem(2);
        i.getItemProperty("y").setValue("Dean");
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
    
    public static ComboBox getFacultyUserType(String inputPrompt){
        ComboBox select = new ComboBox();
        select.setWidth("100%");       
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("y", String.class, "");
        select.setItemCaptionPropertyId("y");
        Item i = select.addItem(1);
        i.getItemProperty("y").setValue("Year Level Coordinator");
        i = select.addItem(2);
        i.getItemProperty("y").setValue("Team Leader");
        i = select.addItem(3);
        i.getItemProperty("y").setValue("Member");
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
    
    public static ComboBox getSchoolYear(String inputPrompt){
        ComboBox select = new ComboBox();
        select.setWidth("100%");       
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        
        for(int i = 0; i < 6; i++){
            select.addItem((year-1)+" - "+year);
            year++;
        }
        
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
    
    public static ComboBox getAllFacultyExceptTeamLeader(String inputPrompt, int facultyId){
        ComboBox select = new ComboBox();
        select.setWidth("100%");
        select.setInputPrompt(inputPrompt);
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("y", String.class, "");
        select.setItemCaptionPropertyId("y");
        Item i;
        for(Users u : TeamTeachDAO.getAllFacultyExceptTeamLeader(facultyId)){
            i = select.addItem(u.getFacultyId());
            i.getItemProperty("y").setValue(u.getName());
        }
        select.addStyleName("small");
        select.setImmediate(true);
        return select;
    }
}
