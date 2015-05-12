/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import com.etest.model.Syllabus;
import com.etest.service.CurriculumService;
import com.etest.service.SyllabusService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;

/**
 *
 * @author jetdario
 */
public class CurriculumPropertyChangeListener extends ComboBox implements Property.ValueChangeListener {

    CurriculumService cs = new CurriculumServiceImpl();
    SyllabusService ss = new SyllabusServiceImpl();
    
    private ComboBox topic;
    private int curriculumId;
    
    public CurriculumPropertyChangeListener(ComboBox topic) {
        this.topic = topic;        
    }
    
    ComboBox getTopic(){
        return topic;
    }
        
    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        if(event.getProperty().getValue() == null){            
        } else {
            CommonCascadeComboBox.getSubjectFromCurriculum(topic, (int) event.getProperty().getValue());
        }
        
    }
    
}
