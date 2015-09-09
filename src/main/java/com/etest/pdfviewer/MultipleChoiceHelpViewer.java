/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfviewer;

import com.etest.pdfgenerator.ItemAnalysisReportPDF;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 * @author jetdario
 */
public class MultipleChoiceHelpViewer extends Window {

    public MultipleChoiceHelpViewer() {
        setWidth("900px");
        setHeight("600px");
        center();
                
        StreamResource.StreamSource source = new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
//                    File f = new File("../VAADIN/files/TestConstRules.pdf");
//                    FileInputStream fis = new FileInputStream(f);
//                    return fis;
                    return this.getClass().getResourceAsStream("/files/TestConstRules.pdf");
                } catch (Exception e) {
                    e.getMessage();
                    return null;
                }
            }
        };
        
        StreamResource resource = new StreamResource(source, "TestConstRules.pdf");
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
