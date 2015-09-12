/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.etest.model.CellItem;
import com.etest.service.CellItemService;
import com.etest.service.CurriculumService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.view.tq.itemanalysis.ItemAnalysisInterpretation;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class ItemAnalysisReportPDF implements StreamSource {

    CurriculumService cs = new CurriculumServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    TQCoverageService tq = new TQCoverageServiceImpl();
    
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int tqCoverageId;
    
    public ItemAnalysisReportPDF(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
        
        Document document = null;        
        Date date = new Date();
        
        try {
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            
            Font header = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
            Font content = FontFactory.getFont("Times-Roman", 10);
            Font dateFont = FontFactory.getFont("Times-Roman", 8);
            
            Paragraph reportTitle = new Paragraph();
            reportTitle.setAlignment(Element.ALIGN_CENTER);
            reportTitle.add(new Phrase("Item Analysis Report", header));            
            document.add(reportTitle);
            
            Paragraph datePrinted = new Paragraph();
            datePrinted.setSpacingAfter(20f);
            datePrinted.setAlignment(Element.ALIGN_CENTER);
            datePrinted.add(new Phrase("Date printed: "+new SimpleDateFormat("dd MMMM yyyy").format(date), dateFont));            
            document.add(datePrinted);
            
            Paragraph subject = new Paragraph();
            subject.setAlignment(Element.ALIGN_LEFT);
            subject.add(new Phrase("Subject: "+cs.getCurriculumById(
                    tq.getTQCoverageById(
                            getTqCoverageId()).getCurriculumId()).getSubject().toUpperCase(), 
                    content));
            document.add(subject);
            
            Paragraph term = new Paragraph();
            term.setAlignment(Element.ALIGN_LEFT);
            term.add(new Phrase("SY and Semester Administered: 2015-16 2nd Semester", content));
            document.add(term);
            
            Paragraph type = new Paragraph();
            type.setSpacingAfter(20f);
            type.setAlignment(Element.ALIGN_LEFT);
            type.add(new Phrase("Type of Test: "+tq.getTQCoverageById(getTqCoverageId()).getExamTitle(), content));
            document.add(type);
            
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{130, 300, 300, 300, 300});
//            table.setSpacingAfter(5f);             
            
            PdfPCell cellOne = new PdfPCell(new Phrase("Item No."));
            cellOne.setBorderWidthTop(1);
            cellOne.setBorderWidthLeft(1);
            cellOne.setBorderWidthRight(1);
            cellOne.setBorderWidthBottom(1);
            cellOne.setPaddingLeft(10);
            cellOne.setPaddingRight(10);
            cellOne.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellTwo = new PdfPCell(new Phrase("Difficulty"));
            cellTwo.setBorderWidthTop(1);
            cellTwo.setBorderWidthLeft(1);
            cellTwo.setBorderWidthRight(1);
            cellTwo.setBorderWidthBottom(1);
            cellTwo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTwo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellThree = new PdfPCell(new Phrase("Interpretation"));
            cellThree.setBorderWidthTop(1);
            cellThree.setBorderWidthLeft(1);
            cellThree.setBorderWidthRight(1);
            cellThree.setBorderWidthBottom(1);
            cellThree.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellThree.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellFour = new PdfPCell(new Phrase("Discrimination"));
            cellFour.setBorderWidthTop(1);
            cellFour.setBorderWidthLeft(1);
            cellFour.setBorderWidthRight(1);
            cellFour.setBorderWidthBottom(1);
            cellFour.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellFour.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell cellFive = new PdfPCell(new Phrase("Interpretation"));
            cellFive.setBorderWidthTop(1);
            cellFive.setBorderWidthLeft(1);
            cellFive.setBorderWidthRight(1);
            cellFive.setBorderWidthBottom(1);
            cellFive.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellFive.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            table.addCell(cellOne);
            table.addCell(cellTwo);
            table.addCell(cellThree);
            table.addCell(cellFour); 
            table.addCell(cellFive);
            
            table.getDefaultCell().setBorderWidth(0f);
            document.add(table);
            
            PdfPTable table2 = new PdfPTable(5);
            table2.setWidthPercentage(100);
            table2.setWidths(new int[]{130, 300, 300, 300, 300});
            
            int itemNo = 1;
            for(CellItem ci : cis.getItemAnalysisResult(tqCoverageId)){
                PdfPCell cell1 = new PdfPCell(new Paragraph(String.valueOf(itemNo), content));
                cell1.setBorderWidthTop(1);
                cell1.setBorderWidthLeft(1);
                cell1.setBorderWidthRight(1);
                cell1.setBorderWidthBottom(1);
                cell1.setPaddingLeft(10);
                cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell2 = new PdfPCell(new Paragraph(String.valueOf(ci.getDifficultIndex()), content));
                cell2.setBorderWidthTop(1);
                cell2.setBorderWidthLeft(1);
                cell2.setBorderWidthRight(1);
                cell2.setBorderWidthBottom(1);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell3 = new PdfPCell(new Paragraph(ItemAnalysisInterpretation.getDifficultyInterpretation(ci.getDifficultIndex()), content));
                cell3.setBorderWidthTop(1);
                cell3.setBorderWidthLeft(1);
                cell3.setBorderWidthRight(1);
                cell3.setBorderWidthBottom(1);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell4 = new PdfPCell(new Paragraph(String.valueOf(ci.getDiscriminationIndex()), content));
                cell4.setBorderWidthTop(1);
                cell4.setBorderWidthLeft(1);
                cell4.setBorderWidthRight(1);
                cell4.setBorderWidthBottom(1);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                
                PdfPCell cell5 = new PdfPCell(new Paragraph(ItemAnalysisInterpretation.getDiscriminationInterpretation(ci.getDiscriminationIndex()), content));
                cell5.setBorderWidthTop(1);
                cell5.setBorderWidthLeft(1);
                cell5.setBorderWidthRight(1);
                cell5.setBorderWidthBottom(1);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        
                table2.addCell(cell1);
                table2.addCell(cell2);
                table2.addCell(cell3);
                table2.addCell(cell4);
                table2.addCell(cell5);
                
                itemNo++;
            }            
            table.getDefaultCell().setBorderWidth(0f);
            document.add(table2);
            
        } catch (DocumentException ex) {
            Logger.getLogger(ItemAnalysisReportPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            document.close();
        }
    }

    @Override
    public InputStream getStream() {
        //return pdf as byte-array
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    int getTqCoverageId(){
        return tqCoverageId;
    }
}
