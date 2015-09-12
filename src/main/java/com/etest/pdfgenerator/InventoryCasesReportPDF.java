/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.etest.model.InventoryOfCasesReport;
import com.etest.service.InventoryReportService;
import com.etest.serviceprovider.InventoryReportServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class InventoryCasesReportPDF implements StreamSource {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    InventoryReportService service = new InventoryReportServiceImpl();
    
    public InventoryCasesReportPDF() {
        Document document = null;
        Date date = new Date();
        
        try {
            document = new Document(PageSize.LETTER, 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);
            document.open();            
            
            Font header = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
            Font content = FontFactory.getFont("Times-Roman", 10);
            Font dateFont = FontFactory.getFont("Times-Roman", 8);
            
            Paragraph reportTitle = new Paragraph();
            reportTitle.setAlignment(Element.ALIGN_CENTER);
            reportTitle.add(new Phrase("Inventory of Cases Report", header));            
            document.add(reportTitle);
            
            Paragraph datePrinted = new Paragraph();
            datePrinted.setSpacingAfter(20f);
            datePrinted.setAlignment(Element.ALIGN_CENTER);
            datePrinted.add(new Phrase("Date printed: "+new SimpleDateFormat("dd MMMM yyyy").format(date), dateFont));            
            document.add(datePrinted);
            
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{100, 300, 100, 100});
            table.setSpacingAfter(5f);             
            
            PdfPCell cellOne = new PdfPCell(new Phrase("Subject"));
            cellOne.setBorder(Rectangle.NO_BORDER);
            cellOne.setPaddingLeft(10);
            cellOne.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellTwo = new PdfPCell(new Phrase("Descriptive Title"));
            cellTwo.setBorder(Rectangle.NO_BORDER);
            cellTwo.setPaddingLeft(10);
            cellTwo.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellTwo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellThree = new PdfPCell(new Phrase("No. of Cases"));
            cellThree.setBorder(Rectangle.NO_BORDER);
            cellThree.setPaddingLeft(10);
            cellThree.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellThree.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellFour = new PdfPCell(new Phrase("No. of Items"));
            cellFour.setBorder(Rectangle.NO_BORDER);
            cellFour.setPaddingLeft(10);
            cellFour.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellFour.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            table.addCell(cellOne);
            table.addCell(cellTwo);
            table.addCell(cellThree);
            table.addCell(cellFour); 
            
            table.getDefaultCell().setBorderWidth(0f);
            document.add(table);
                        
            for(InventoryOfCasesReport report : service.getInventoryOfCases()){
                PdfPTable table2 = new PdfPTable(4);
                table2.setWidthPercentage(100);
                table2.setWidths(new int[]{100, 300, 100, 100});
                table2.setSpacingBefore(3f);
                table2.setSpacingAfter(3f);
                                             
                if(!service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId()).isEmpty()){                   
                    if(!service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())).isEmpty()){
                        PdfPCell cell1 = new PdfPCell(new Paragraph(report.getSubject(), content));
                        cell1.setBorder(0);
                        cell1.setPaddingLeft(10);
                        cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell2 = new PdfPCell(new Paragraph(report.getDescriptiveTitle(), content));
                        cell2.setBorder(0);
                        cell2.setPaddingLeft(10);
                        cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell3 = new PdfPCell(new Paragraph(String.valueOf(service.getTotalCellCasesBySyllabus(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId()))), content));
                        cell3.setBorder(0);
                        cell3.setPaddingLeft(10);
                        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell4 = new PdfPCell(new Paragraph(String.valueOf(service.getTotalCellItemsByCellCaseId(service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())))), content));
                        cell4.setBorder(0);
                        cell4.setPaddingLeft(10);
                        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        
                        table2.addCell(cell1);
                        table2.addCell(cell2);
                        table2.addCell(cell3);
                        table2.addCell(cell4);
                        document.add(table2);
                    }                       
                }                    
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
