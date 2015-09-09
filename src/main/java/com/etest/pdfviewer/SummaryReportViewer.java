/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfviewer;

import com.etest.pdfgenerator.InventoryCasesReportPDF;
import com.etest.pdfgenerator.InventoryCasesReportPDF;
import com.etest.pdfgenerator.InventoryItemsReportPDF;
import com.etest.pdfgenerator.InventoryItemsReportPDF;
import com.etest.pdfgenerator.TQCoveragePDF;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author jetdario
 */
public class SummaryReportViewer extends Window {

    public SummaryReportViewer(String summaryType) {
        setCaption(summaryType);        
        setWidth("900px");
        setHeight("600px");
        center();
                
        StreamResource resource; //= new StreamResource(new InventoryCasesReportPDF(), "InventoryOfCases.pdf");   
        if(summaryType.equals("Summary: Case vs Items")){
            resource = new StreamResource(new InventoryCasesReportPDF(), "InventoryOfCases.pdf");            
        } else {
            resource = new StreamResource(new InventoryItemsReportPDF(), "InventoryOfItems.pdf");
        }       
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
    
}
