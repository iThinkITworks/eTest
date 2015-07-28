/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class InventoryItemsReportPDF implements StreamSource {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    public InventoryItemsReportPDF() {
        Document document = null;
        
        try {
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            
            document.add(new Paragraph("Inventory Items"));
        } catch (DocumentException ex) {
            Logger.getLogger(InventoryItemsReportPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            document.close();
        }        
    }

    @Override
    public InputStream getStream() {
        //return pdf as byte-array
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
}
