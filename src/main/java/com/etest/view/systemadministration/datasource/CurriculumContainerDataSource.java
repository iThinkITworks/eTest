/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.datasource;

import com.etest.common.CommonVariableMap;
import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

/**
 *
 * @author jetdario
 */
public class CurriculumContainerDataSource extends IndexedContainer {

    CurriculumService cs = new CurriculumServiceImpl();
    
    public CurriculumContainerDataSource() {
        addContainerProperty("ID", Integer.class, null);
        addContainerProperty("year level", String.class, null);
        addContainerProperty("subject", String.class, null);
        addContainerProperty("descriptive title", String.class, null);
        addContainerProperty("normal course offering", String.class, null);
        
        for(Curriculum c : cs.getAllCurriculum()){
            Item item = getItem(this.addItem());
            item.getItemProperty("ID").setValue(c.getCurriculumId());
            item.getItemProperty("year level").setValue(CommonVariableMap.getYearLevel(c.getYearLevel()));
            item.getItemProperty("subject").setValue(c.getSubject());
            item.getItemProperty("descriptive title").setValue(c.getDescriptiveTitle());
//            System.out.println("norm course: "+CommonVariableMap.getNormCourseOffering(c.getNormCourseOffering()));
            item.getItemProperty("normal course offering").setValue(CommonVariableMap.getNormCourseOffering(c.getNormCourseOffering()));
        }
    }
    
}
