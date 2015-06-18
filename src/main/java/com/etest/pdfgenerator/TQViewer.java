/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author jetdario
 */
public class TQViewer extends Window {

    Grid grid = new Grid();    
    private final int curriculumId;
    private final int totalTestItems;
    
    public TQViewer(Grid grid, 
            int curriculumId, 
            int totalTestItems) {
        this.grid = grid;
        this.curriculumId = curriculumId;
        this.totalTestItems = totalTestItems;
        
        setSizeFull();
        setWidth("900px");
        setHeight("600px");
        center();
                
        StreamResource resource = new StreamResource(new TQCoveragePDF(getTQCoverageGrid(), getCurriculumId(), getTotalTestItems()), "TQ.pdf");
        resource.setMIMEType("application/pdf");       

        VerticalLayout v = new VerticalLayout();
        v.setSizeFull();
        Embedded e = new Embedded();
        e.setSource(resource);
        e.setSizeFull();
        e.setType(Embedded.TYPE_BROWSER);
        v.addComponent(e);
        
        setContent(v);
    }
    
    Grid getTQCoverageGrid(){
        return grid;
    }
    
    int getCurriculumId(){
        return curriculumId;
    }
        
    int getTotalTestItems(){
        return totalTestItems;
    }
}
