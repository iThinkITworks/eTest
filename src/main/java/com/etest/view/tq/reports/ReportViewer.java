/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.reports;

import com.etest.pdfgenerator.InventoryCasesReportPDF;
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
public class ReportViewer extends Panel {

    public ReportViewer(String reportType) {
        setHeight("100%");
        setCaption(reportType);
                
        StreamResource resource; //= new StreamResource(new InventoryCasesReportPDF(), "InventoryOfCases.pdf");   
        if(reportType.equals("Inventory of Cases")){
            resource = new StreamResource(new InventoryCasesReportPDF(), "InventoryOfCases.pdf");            
        } else {
            resource = new StreamResource(new InventoryItemsReportPDF(), "InventoryOfItems.pdf");
        }       
        resource.setMIMEType("application/pdf"); 
                
        Embedded e = new Embedded();
        e.setSource(resource);
        e.setSizeFull();
        e.setType(Embedded.TYPE_BROWSER);
        setContent(e);    
    }
    
}
