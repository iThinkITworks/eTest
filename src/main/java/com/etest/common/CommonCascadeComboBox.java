/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import com.etest.dao.CurriculumDAO;
import com.etest.dao.SyllabusDAO;
import com.etest.model.Syllabus;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class CommonCascadeComboBox {
    
    public static ComboBox getSubjectFromCurriculum(ComboBox topic, int curriculumId){
        topic.removeAllItems();
//        topic.setWidth("100%");       
        topic.setNullSelectionAllowed(false);
        topic.addContainerProperty("y", String.class, "");
        topic.setItemCaptionPropertyId("y");
        
        Item i;
        for(Syllabus s: SyllabusDAO.getSyllabusByCurriculum(curriculumId)){
            i = topic.addItem(s.getSyllabusId());
            i.getItemProperty("y").setValue(s.getTopic());
        }
        
        topic.addStyleName("small");
        topic.setImmediate(true);
        return topic;
    }
    
}
