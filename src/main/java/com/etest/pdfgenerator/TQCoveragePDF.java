/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.etest.global.ShowErrorNotification;
import com.etest.model.TQAnswerKey;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.service.ItemKeyService;
import com.etest.service.SyllabusService;
import com.etest.service.TQCoverageService;
import com.etest.service.TeamTeachService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.ItemKeyServiceImpl;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

/**
 *
 * @author jetdario
 */
public class TQCoveragePDF implements StreamSource {

    SyllabusService ss = new SyllabusServiceImpl();
    TeamTeachService tts = new TeamTeachServiceImpl();
    TQCoverageService tq = new TQCoverageServiceImpl();
    CellCaseService ccs = new CellCaseServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    ItemKeyService k = new ItemKeyServiceImpl();
    
    Grid grid = new Grid();
    
    private int curriculumId;
    private int syllabusId;
    private int teamTeachId;
    private int totalTestItems;
    List<Integer> CellItemIdList = new ArrayList<>();
    
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private int tqCoverageId;
    
    public TQCoveragePDF(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
        
        Document document = null;         
        
        try {
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            
            int itemNo = 1;
            Map<Integer, Map<Integer, Integer>> tqCoverage = tq.getTQCoverage(getTQCoverageId());
            for (Map.Entry<Integer, Map<Integer, Integer>> tqCases : tqCoverage.entrySet()) {
                Integer tqCaseId = tqCases.getKey();

                Label caseTopic = new Label();
                caseTopic.setValue(ccs.getCellCaseById(tqCaseId).getCaseTopic());
                caseTopic.setContentMode(ContentMode.HTML); 
                document.add(new Paragraph(caseTopic.getValue().replaceAll("(?i)<p.*?>.*?</p>", "")));

                Map<Integer, Integer> value = tqCases.getValue();
                for (Map.Entry<Integer, Integer> itemIds : value.entrySet()) {
                    Integer itemId = itemIds.getKey();
                    Integer itemKeyId = itemIds.getValue();

                    List<String> keyList = k.getAllItemKey(itemId);
                    if(keyList.isEmpty()){
                        ShowErrorNotification.error("No Item Key was found for STEM: \n"
                                +cis.getCellItemById(itemId).getItem());
                        return;
                    }

                    Label stem = new Label();            
                    stem.setValue(itemNo+". "+cis.getCellItemById(itemId).getItem().replace("{key}", keyList.get(0)));
                    stem.setContentMode(ContentMode.HTML);
                    document.add(new Paragraph(stem.getValue()));

                    PdfPTable table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);

                    //Set Column widths
                    float[] columnWidths = {1f, 1f};
                    table.setWidths(columnWidths);

                    PdfPCell cell1 = new PdfPCell(new Paragraph("A) "+cis.getCellItemById(itemId).getOptionA()));
    //                cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setBorder(0);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell cell2 = new PdfPCell(new Paragraph("C) "+cis.getCellItemById(itemId).getOptionC()));
    //                cell2.setBorderColor(BaseColor.GREEN);
                    cell2.setBorder(0);
                    cell2.setPaddingLeft(10);
                    cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell cell3 = new PdfPCell(new Paragraph("B) "+cis.getCellItemById(itemId).getOptionB()));
    //                cell3.setBorderColor(BaseColor.RED);
                    cell3.setBorder(0);
                    cell3.setPaddingLeft(10);
                    cell3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell cell4 = new PdfPCell(new Paragraph("D) "+ cis.getCellItemById(itemId).getOptionD()));
    //                cell4.setBorderColor(BaseColor.RED);
                    cell4.setBorder(0);
                    cell4.setPaddingLeft(10);
                    cell4.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    table.addCell(cell1);
                    table.addCell(cell2);
                    table.addCell(cell3);
                    table.addCell(cell4);

                    document.add(table);
                                        
                    itemNo++;
                }
            }         
            
            document.newPage();
            document.add(new Paragraph("Answer Key: "));
                        
            itemNo = 1;
            List<TQAnswerKey> answerKey = tq.getTQCoverageAnswerKey(getTQCoverageId());
            for (TQAnswerKey t : answerKey) {
                document.add(new Paragraph(t.getItemNo()+": "+cis.getOptionAnswer(t.getCellItemId()).get(t.getAnswer())));
            }         
            
        } catch (DocumentException ex) {
            Logger.getLogger(TQCoveragePDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(document != null){
                document.close();
            }
        }
    }

    int getTQCoverageId(){
        return tqCoverageId;
    }
    
    Grid getTQCoverageGrid(){
        return grid;
    }
    
    int getCurriculumId(){
        return curriculumId;
    }
    
    int getSyllabusId(){
        return syllabusId;
    }
    
    int getTeamTeachId(){
        return teamTeachId;
    }
    
    int getTotalTestItems(){
        return totalTestItems;
    }
    
    @Override
    public InputStream getStream() {
        //return pdf as byte-array
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    String extractText(String html){
    final ArrayList<String> list = new ArrayList<String>();

    ParserDelegator parserDelegator = new ParserDelegator();
    ParserCallback parserCallback;
        parserCallback = new ParserCallback() {
            @Override
            public void handleText(final char[] data, final int pos) {
                list.add(new String(data));
            }
            @Override
            public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) { }
            @Override
            public void handleEndTag(Tag t, final int pos) {  }
            @Override
            public void handleSimpleTag(Tag t, MutableAttributeSet a, final int pos) { }
            @Override
            public void handleComment(final char[] data, final int pos) { }
            @Override
            public void handleError(final java.lang.String errMsg, final int pos) { }
        };
        try {
            parserDelegator.parse(new StringReader(html), parserCallback, true);
        } catch (IOException ex) {
            Logger.getLogger(TQCoveragePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    String text = "";

    text = list.stream().map((s) -> " " + s).reduce(text, String::concat);

    return text;
}
}
