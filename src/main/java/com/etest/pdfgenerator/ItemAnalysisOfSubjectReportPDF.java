/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.etest.service.ReportService;
import com.etest.serviceprovider.ReportServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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
public class ItemAnalysisOfSubjectReportPDF implements StreamSource {
    
    ReportService rs = new ReportServiceImpl();
    
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private int tqCoverageId;
    
    public ItemAnalysisOfSubjectReportPDF(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
        
        Document document = null;
        
        try {
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            
            Font header = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
            Font content = FontFactory.getFont("Times-Roman", 10);
            
            Paragraph reportTitle = new Paragraph();
            reportTitle.setSpacingAfter(30f);
            reportTitle.setAlignment(Element.ALIGN_CENTER);
            reportTitle.add(new Phrase("Interactive Querying", header));
            
            document.add(reportTitle);
            
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(75);            
            
            PdfPCell cellOne = new PdfPCell(new Phrase("Difficulty"));
            cellOne.setBorder(0);
            cellOne.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellTwo = new PdfPCell(new Phrase("Discrimination"));
            cellTwo.setBorder(0);
            cellTwo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTwo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellThree = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DifficultIndex", 0.00, 0.19))+" Very difficult item(s)", 
                            content));
            cellThree.setBorder(0);
            cellThree.setPaddingLeft(50);
            cellThree.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellThree.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellFour = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DiscriminationIndex", 0.00, 0.19))+" Poor items(s)", 
                            content));
            cellFour.setBorder(0);
            cellFour.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellFour.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellFive = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DifficultIndex", 0.20, 0.39))+" difficult item(s)", 
                            content));
            cellFive.setBorder(0);
            cellFive.setPaddingLeft(50);
            cellFive.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellFive.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellSix = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DiscriminationIndex", 0.20, 0.29))+" Marginal items(s)", 
                            content));
            cellSix.setBorder(0);
            cellSix.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSix.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellSeven = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DifficultIndex", 0.40, 0.60))+" Average item(s)", 
                            content));
            cellSeven.setBorder(0);
            cellSeven.setPaddingLeft(50);
            cellSeven.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellSeven.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellEight = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DiscriminationIndex", 0.30, 0.39))+" Reasonably Good items(s)", 
                            content));
            cellEight.setBorder(0);
            cellEight.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellEight.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellNine = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DifficultIndex", 0.61, 0.80))+" Easy item(s)", 
                            content));
            cellNine.setBorder(0);
            cellNine.setPaddingLeft(50);
            cellNine.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellNine.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellTen = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DiscriminationIndex", 0.41, 1))+" Very good items(s)", 
                            content));
            cellTen.setBorder(0);
            cellTen.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTen.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellEleven = new PdfPCell(
                    new Phrase(
                            String.valueOf(rs.getTQCriticalIndexValue(getTQCoverageId(), "DifficultIndex", 0.81, 1))+" Very Easy item(s)", 
                            content));
            cellEleven.setBorder(0);
            cellEleven.setPaddingLeft(50);
            cellEleven.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellEleven.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellTwelve = new PdfPCell(new Phrase(""));
            cellTwelve.setBorder(0);
            cellTwelve.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTwelve.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            table.addCell(cellOne);
            table.addCell(cellTwo);
            table.addCell(cellThree);
            table.addCell(cellFour);
            table.addCell(cellFive);
            table.addCell(cellSix);
            table.addCell(cellSeven);
            table.addCell(cellEight);
            table.addCell(cellNine);
            table.addCell(cellTen);
            table.addCell(cellEleven);
            table.addCell(cellTwelve);
            
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(ItemAnalysisOfSubjectReportPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(document != null){
                document.close();
            }
        }
    }

    @Override
    public InputStream getStream() {
        //return pdf as byte-array
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    int getTQCoverageId(){
        return tqCoverageId;
    }
    
}
