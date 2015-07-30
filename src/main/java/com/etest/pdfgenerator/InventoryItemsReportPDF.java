/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.etest.model.InventoryOfCasesReport;
import com.etest.service.InventoryReportService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.InventoryReportServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class InventoryItemsReportPDF implements StreamSource {

    TQCoverageService tq = new TQCoverageServiceImpl();
    InventoryReportService service = new InventoryReportServiceImpl();
    
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    String[] tableHeader = {"Subject", "Remember", "Understand", "Apply", "Analyze", "Evaluate", "Create", "Total"};
    
    public enum BloomsClass {
        Remember, Understand, Apply, Analyze, Evaluate, Create
    }
    
    public InventoryItemsReportPDF() {
        Document document = null;
        
        try {
            document = new Document(PageSize.LETTER.rotate(), 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            
            Font header = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
            Font content = FontFactory.getFont("Times-Roman", 10);
            
            Paragraph reportTitle = new Paragraph();
            reportTitle.setSpacingAfter(30f);
            reportTitle.setAlignment(Element.ALIGN_CENTER);
            reportTitle.add(new Phrase("Inventory of Items Report"));
            
            document.add(reportTitle);
            
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setSpacingAfter(5f);
                        
            for(int i = 0; i < tableHeader.length; i++){
                PdfPCell cell = new PdfPCell(new Phrase(tableHeader[i], header));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPaddingLeft(10);                
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if(tableHeader[i].equals("Subject")){
                    cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                } else {
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                }                
                table.addCell(cell);
            }
            
            document.add(table);
            
            for(InventoryOfCasesReport report : service.getInventoryOfCases()){
                PdfPTable table2 = new PdfPTable(8);
                table2.setWidthPercentage(100);
                table2.setSpacingBefore(3f);
                table2.setSpacingAfter(3f);
                
                if(!service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId()).isEmpty()){ 
                    if(!service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())).isEmpty()){
                        PdfPCell cell1 = new PdfPCell(new Paragraph(report.getSubject(), content));
                        cell1.setBorder(0);
                        cell1.setPaddingLeft(10);
                        cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell2 = new PdfPCell(new Paragraph(
                                String.valueOf(service.getTotalItemsByBloomsTaxonomy(
                                        service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())), 
                                        tq.getBloomsClassId(BloomsClass.Remember.toString()))), content));
                        cell2.setBorder(0);
                        cell2.setPaddingLeft(10);
                        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell3 = new PdfPCell(new Paragraph(
                                String.valueOf(service.getTotalItemsByBloomsTaxonomy(
                                        service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())), 
                                        tq.getBloomsClassId(BloomsClass.Understand.toString()))), content));
                        cell3.setBorder(0);
                        cell3.setPaddingLeft(10);
                        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell4 = new PdfPCell(new Paragraph(
                                String.valueOf(service.getTotalItemsByBloomsTaxonomy(
                                        service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())), 
                                        tq.getBloomsClassId(BloomsClass.Apply.toString()))), content));
                        cell4.setBorder(0);
                        cell4.setPaddingLeft(10);
                        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell5 = new PdfPCell(new Paragraph(
                                String.valueOf(service.getTotalItemsByBloomsTaxonomy(
                                        service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())), 
                                        tq.getBloomsClassId(BloomsClass.Analyze.toString()))), content));
                        cell5.setBorder(0);
                        cell5.setPaddingLeft(10);
                        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell6 = new PdfPCell(new Paragraph(
                                String.valueOf(service.getTotalItemsByBloomsTaxonomy(
                                        service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())), 
                                        tq.getBloomsClassId(BloomsClass.Evaluate.toString()))), content));
                        cell6.setBorder(0);
                        cell6.setPaddingLeft(10);
                        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell7 = new PdfPCell(new Paragraph(
                                String.valueOf(service.getTotalItemsByBloomsTaxonomy(
                                        service.getListOfCellCaseIdBySyllabusId(service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())), 
                                        tq.getBloomsClassId(BloomsClass.Create.toString()))), content));
                        cell7.setBorder(0);
                        cell7.setPaddingLeft(10);
                        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell8 = new PdfPCell(new Paragraph(
                                String.valueOf(service.getTotalCellItemsByCellCaseId(
                                        service.getListOfCellCaseIdBySyllabusId(
                                                service.getListOfSyllabusIdByCurriculumId(report.getCurriculumId())))), 
                                content));
                        cell8.setBorder(0);
                        cell8.setPaddingLeft(10);
                        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        
                        table2.addCell(cell1);
                        table2.addCell(cell2);
                        table2.addCell(cell3);
                        table2.addCell(cell4);
                        table2.addCell(cell5);
                        table2.addCell(cell6);
                        table2.addCell(cell7);
                        table2.addCell(cell8);                        
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
