/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import com.etest.dao.SyllabusDAO;
import com.etest.dao.TQCoverageDAO;
import com.etest.model.Syllabus;
import com.etest.model.TQCoverage;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CommonCascadeComboBox {
    
    public static ComboBox getTopicFromCurriculum(ComboBox topic, int curriculumId){
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
        
        topic.addStyleName(ValoTheme.COMBOBOX_SMALL);
        topic.setImmediate(true);
        return topic;
    }
    
    public static ComboBox getApprovedTqFromCurriculum(ComboBox approvedTq, int curriculumId){
        approvedTq.removeAllItems();
//        approvedTq.setWidth("100%");       
        approvedTq.setNullSelectionAllowed(false);
        approvedTq.addContainerProperty("y", String.class, "");
        approvedTq.setItemCaptionPropertyId("y");
        
        Item i;
        for(TQCoverage t: TQCoverageDAO.getApprovedTqCoverageByCurriculumId(curriculumId)){
            i = approvedTq.addItem(t.getTqCoverageId());
            i.getItemProperty("y").setValue(t.getExamTitle());
        }
        
        approvedTq.setImmediate(true);
        return approvedTq;
    }
    
    public static ComboBox getTqTicketNoFromCurriculum(ComboBox approvedTq, int curriculumId){
        approvedTq.removeAllItems();
//        approvedTq.setWidth("100%");       
        approvedTq.setNullSelectionAllowed(false);
        approvedTq.addContainerProperty("y", String.class, "");
        approvedTq.setItemCaptionPropertyId("y");
        
        Item i;
        for(TQCoverage t: TQCoverageDAO.getApprovedTqCoverageByCurriculumId(curriculumId)){
            i = approvedTq.addItem(t.getTqCoverageId());
            i.getItemProperty("y").setValue(TQCoverageDAO.getTqCoverageTicketNo(t.getTqCoverageId()));
        }
        
        approvedTq.setImmediate(true);
        return approvedTq;
    }
}
