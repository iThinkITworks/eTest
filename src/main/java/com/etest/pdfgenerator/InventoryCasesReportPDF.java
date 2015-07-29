/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.etest.model.InventoryOfCasesReport;
import com.etest.service.InventoryOfCasesReportService;
import com.etest.serviceprovider.InventoryOfCasesReportServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class InventoryCasesReportPDF implements StreamSource {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    InventoryOfCasesReportService service = new InventoryOfCasesReportServiceImpl();
    
    public InventoryCasesReportPDF() {
        Document document = null;
        
        try {
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);
            document.open();            
            
            document.add(new Paragraph("Inventory of Cases Report"));
            
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);            
            
            table.addCell("Subject");
            table.addCell("Descriptive Title");
            table.addCell("No. of Cases");
            table.addCell("No. of Items"); 
            
            document.add(table);
                        
            for(InventoryOfCasesReport report : service.getInventoryOfCases()){
                PdfPTable table2 = new PdfPTable(4);
                table2.setWidthPercentage(100);
                table2.setSpacingBefore(10f);
                table2.setSpacingAfter(10f); 
                                             
                if(!service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId()).isEmpty()){                   
                    if(!service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())).isEmpty()){
                        PdfPCell cell1 = new PdfPCell(new Paragraph(report.getSubject()));
                        cell1.setBorder(0);
                        cell1.setPaddingLeft(10);
                        cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell2 = new PdfPCell(new Paragraph(report.getDescriptiveTitle()));
                        cell2.setBorder(0);
                        cell2.setPaddingLeft(10);
                        cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell3 = new PdfPCell(new Paragraph(String.valueOf(service.getTotalCellCasesBySyllabus(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())))));
                        cell3.setBorder(0);
                        cell3.setPaddingLeft(10);
                        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell4 = new PdfPCell(new Paragraph(String.valueOf(service.getTotalCellItemsByCellCaseId(service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId()))))));
                        cell4.setBorder(0);
                        cell4.setPaddingLeft(10);
                        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        
                        table2.addCell(cell1);
                        table2.addCell(cell2);
                        table2.addCell(cell3);
                        table2.addCell(cell4);
                        document.add(table2);
                    }                       
                }                 
                
//                document.add(table);
            }
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
