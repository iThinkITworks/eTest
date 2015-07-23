/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.etest.global.ShowErrorNotification;
import com.etest.model.ItemAnalysis;
import com.etest.service.CurriculumService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.manager.PluploadManager;

/**
 *
 * @author jetdario
 */
public class FileUploadWindow extends Window {

    VerticalLayout v = new VerticalLayout();
    TQCoverageService tq = new TQCoverageServiceImpl();
    CurriculumService cs = new CurriculumServiceImpl();
    
    private int tqCoverageId = 0;
    
    List<String> upperGroupStudentNo = new ArrayList<>();
    List<String> lowerGroupStudentNo = new ArrayList<>();
    List<Integer> itemIds;
    Map<String, List<Character>> studentNoAndAnswer;
    Map<String, Integer> studentNoAndTotalScore;
    private double groupTotalForProportion = 0;
    File excelFile;    
    int totalItems;
    
    Grid grid;
    Button view;
    Label analyze = new Label();
    private double current;
    private double totalData;
        
    public FileUploadWindow(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
        
        setCaption("UPLOAD FILE");
        setWidth("700px");
        setHeight("100%");
        setModal(true);
        center();
                
        v.setWidth("100%");
        v.setMargin(true);
        v.setSpacing(true);
        
        v.addComponent(new Label("Subject: <b>"+tq.getTQCoverageById(getTqCoverageId()).getSubject()+"</b>", ContentMode.HTML));
        v.addComponent(new Label("Exam Tile: <b>"+tq.getTQCoverageById(getTqCoverageId()).getExamTitle()+"</b>", ContentMode.HTML));
        v.addComponent(new Label("Date Created: <b>"+tq.getTQCoverageById(getTqCoverageId()).getDateCreated()+"</b>", ContentMode.HTML));
                
        PluploadManager manager = new PluploadManager();
        manager.getUploader().setMaxFileSize("5mb");
        manager.getUploader().addFileUploadedListener((PluploadFile file) -> {            
            excelFile = new File(file.getUploadedFile().toString());
            
            String extension = "";
            int i = excelFile.getName().lastIndexOf('.');
            if(i >= 0){
                extension = excelFile.getName().substring(i+1);
            }
            
            if(extension.equals("xlsx")){
                Notification.show("Convert Excel File from .xlsx to .xls",Notification.Type.ERROR_MESSAGE);
                return;
            }
                                 
            current = 1;
            readContentFromExcelFile(excelFile);
        });
        
        manager.getUploader().addErrorListener((PluploadError error) -> {
            Notification.show("There was an error: "
                 + error.getMessage() + " (" + error.getType() + ")",
                 Notification.Type.ERROR_MESSAGE);
        }); 
            
        manager.getUploader().addUploadStartListener(new Plupload.UploadStartListener() {

            @Override
            public void onUploadStart() {
                analyze.setValue("Process Item Analysis... 0%");
                v.addComponent(analyze);
            }
        });
        
        manager.getUploader().addUploadStopListener(new Plupload.UploadStopListener() {

            @Override
            public void onUploadStop() {
                //TODO
            }
        });
        
//        manager.getUploader().addUploadCompleteListener(new Plupload.UploadCompleteListener() {
//
//            @Override
//            public void onUploadComplete() {
//                v.removeComponent(status);
//                
//                HorizontalLayout h = new HorizontalLayout();
//                h.setWidth("100%");
//
//                h.addComponent(viewTableProportion());
//                h.addComponent(viewStudentsTotalScore());
//                h.addComponent(approveItemAnalysis());
//                v.addComponent(h);
//
//                v.addComponent(itemAnalysisGridPanel());
//            }
//        });
        
        v.addComponent(manager);   
        
        setContent(v);
    }
    
    Panel itemAnalysisGridPanel(){
        grid = new ItemAnalysisDataGridProperties(
                getTqCoverageId(), 
                getUpperGroupStudentNo(), 
                getLowerGroupStudentNo(), 
                tq.getCellItemIdByTQCoverageId(getTqCoverageId()), 
                getStudentNoAndAnswer(), 
                getGroupTotalForProportion());
        
        Panel panel = new Panel("ITEM ANALYSIS");
        panel.setWidth("100%");
        panel.setContent(grid);
        
        return panel;
    }
    
    Grid getItemAnalysisGrid(){
        return grid;
    }
    
    void readContentFromExcelFile(File excelFile){ 
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelFile));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(wb.getActiveSheetIndex());
            HSSFRow row;
            HSSFCell cell;
                        
            boolean stop = false;
            boolean nonBlankRowFound;
            int s;
            HSSFRow lastRow = null;
            
            while (stop == false) {
                nonBlankRowFound = false;
                lastRow = sheet.getRow(sheet.getLastRowNum());
                for (s = lastRow.getFirstCellNum(); s <= lastRow.getLastCellNum(); s++) {
                    cell = lastRow.getCell(s);
                    if (cell != null && lastRow.getCell(s).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
                        nonBlankRowFound = true;
                    }
                }
                if (nonBlankRowFound == true) {
                    stop = true;
                } else {
                    sheet.removeRow(lastRow);
                }
            }
            
            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;
            
            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            List<ItemAnalysis> itemAnalysisList = new ArrayList<>();
            List<Character> answer;
            ItemAnalysis itemAnalysis = null;
             
            for(int c = 0; c < cols; c++){
                itemAnalysis = new ItemAnalysis();
                answer = new ArrayList<>();
               for(int r = 0; r < rows; r++){
                   row = sheet.getRow(r);
                   if(row == null || row.toString().isEmpty()){       
                       ShowErrorNotification.error("Remove all blank/empty rows after the last Item!");
                       return;
                   } else {
//                   if(row != null){
                       cell = row.getCell(c);
                       if(cell == null || cell.toString().isEmpty()){
                           ShowErrorNotification.error("Remove all blank/empty columns after the last student!");
                           return;
                       } else {
//                       if(cell != null){                           
                           if(c != 0){         
                               if(r == 0){                                   
                                   itemAnalysis.setStudentNumber(cell.toString().trim());
                               } else {
                                   answer.add(cell.toString().trim().charAt(0));
                               }
                           } else {
                               if(r != 0){
                                   totalItems++;
                               }
                           }                            
                       }                       
                   }                    
               }     
               if(c != 0){                   
                   itemAnalysis.setAnswer(answer);
                   itemAnalysisList.add(itemAnalysis);
               }                     
            }
                     
            if(tq.getCellItemIdByTQCoverageId(getTqCoverageId()).size() != totalItems){
                ShowErrorNotification.error("Total Items do not MATCH!");
                totalItems = 0;
                return;
            }
                        
            studentNoAndTotalScore = new HashMap<>();
            studentNoAndAnswer = new HashMap<>();
            totalItems = 1;
            new Thread(){
                
                @Override
                public void run(){
                    totalData = itemAnalysisList.size();
                    for(ItemAnalysis i : itemAnalysisList){
                        try {
                            Thread.sleep(50);
                            studentNoAndTotalScore.put(i.getStudentNumber(), ItemAnalysisInterpretation.getTotalScoresOfAllStudent(tqCoverageId, i.getAnswer()));
                            studentNoAndAnswer.put(i.getStudentNumber(), i.getAnswer());
                            
                            getUI().access(new Runnable(){

                                @Override
                                public void run() {
                                    if(totalItems < itemAnalysisList.size()){
                                        analyze.setValue("Analyzing data.. "+CommonUtilities.roundOffToTwoDecimal((current/totalData)*100)+"%");
                                        current++;
                                    } else {
                                        analyze.setValue("Data analyzed... 100%");
                                        getLowerAndUpperGroupStudent(studentNoAndTotalScore);
            
                                        HorizontalLayout h = new HorizontalLayout();
                                        h.setWidth("100%");

                                        h.addComponent(viewTableProportion());
                                        h.addComponent(viewStudentsTotalScore());
                                        h.addComponent(approveItemAnalysis());
                                        v.addComponent(h);

                                        v.addComponent(itemAnalysisGridPanel());
                                    }
                                }
                            
                            });
                            
                            totalItems++;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FileUploadWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            }.start();
            UI.getCurrent().setPollInterval(500);
//            for(ItemAnalysis i : itemAnalysisList){
//                studentNoAndTotalScore.put(i.getStudentNumber(), ItemAnalysisInterpretation.getTotalScoresOfAllStudent(tqCoverageId, i.getAnswer()));
//                studentNoAndAnswer.put(i.getStudentNumber(), i.getAnswer());
//                totalItems++;
//            }
            
//            getLowerAndUpperGroupStudent(studentNoAndTotalScore);
//            
//            HorizontalLayout h = new HorizontalLayout();
//            h.setWidth("100%");
//
//            h.addComponent(viewTableProportion());
//            h.addComponent(viewStudentsTotalScore());
//            h.addComponent(approveItemAnalysis());
//            v.addComponent(h);
//
//            v.addComponent(itemAnalysisGridPanel());
        } catch (IOException ex) {
            Logger.getLogger(TQItemAnalysisUI.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    public void getLowerAndUpperGroupStudent(Map<String, Integer> studentNoAndTotalScore){
//        Stream<Map.Entry<String, Integer>> sorted = studentNoAndTotalScore.entrySet().stream()
//                    .sorted(Map.Entry.comparingByValue());
        System.out.println("test this");
        Stream<Map.Entry<String, Integer>> sorted = studentNoAndTotalScore.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        
        double upperGroup = 0;   
        double proportion;
        if(studentNoAndTotalScore.size() < 30){
            upperGroup = CommonUtilities.roundingDownToWholeNumber(studentNoAndTotalScore.size() * .5);
            proportion = CommonUtilities.roundOffToTwoDecimal(studentNoAndTotalScore.size() * .5);
            int i = 1;
            Iterator iterator = sorted.iterator();
            if((studentNoAndTotalScore.size() % 2) != 0){
                while(iterator.hasNext()){
                    String[] s = iterator.next().toString().split("=");                    
                    if(i == (studentNoAndTotalScore.size()+1)/2){ //Elimate the median for the list of Students
                    } else {
                        if((i) <= upperGroup){
                            upperGroupStudentNo.add(s[0]); //add all students with high score to upper group
                        } else {
                            lowerGroupStudentNo.add(s[0]); //add all students with low score to lower group
                        }
                        
                    }
                    i++;
                }
            } else {
                while(iterator.hasNext()){
                    String[] s = iterator.next().toString().split("=");
                    if(i <= upperGroup){
                        upperGroupStudentNo.add(s[0]);
                    } else {
                        lowerGroupStudentNo.add(s[0]);
                    }
                    i++;
                }
            }
        } else {
            upperGroup = CommonUtilities.roundingDownToWholeNumber(studentNoAndTotalScore.size() * .27);
            proportion = CommonUtilities.roundOffToTwoDecimal(studentNoAndTotalScore.size() * .27);
            double lowerGroup = CommonUtilities.roundingDownToWholeNumber(studentNoAndTotalScore.size() * .73);
            int i = 0;
            Iterator iterator = sorted.iterator();
            while(iterator.hasNext()){
                String[] s = iterator.next().toString().split("=");
                if((i) < upperGroup){
                    upperGroupStudentNo.add(s[0]);
                } 
                
                if((i) > (lowerGroup)){
                    lowerGroupStudentNo.add(s[0]);
                }
                i++;
            }
        }    
        
        groupTotalForProportion = proportion;
    }
    
    int getTqCoverageId(){
        return tqCoverageId;
    }
    
    List<String> getUpperGroupStudentNo(){
        return upperGroupStudentNo;
    }
    
    List<String> getLowerGroupStudentNo(){
        return lowerGroupStudentNo;
    }
    
    Map<String, List<Character>> getStudentNoAndAnswer(){
        return studentNoAndAnswer;
    }
    
    double getGroupTotalForProportion(){
        return groupTotalForProportion;
    }
    
    Map<String, Integer> getStudentNoAndTotalScore(){
        return studentNoAndTotalScore;
    }
    
    Button viewTableProportion(){
        Button button = new Button("View Proportion Table");
        button.setWidthUndefined();
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener((Button.ClickEvent event) -> {
            Window sub = new ProportionDataTable(getStudentNoAndAnswer(), 
                    getUpperGroupStudentNo(), 
                    getLowerGroupStudentNo(), 
                    tq.getCellItemIdByTQCoverageId(getTqCoverageId()), 
                    getTqCoverageId(), 
                    getGroupTotalForProportion());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        });
        
        return button;
    }
    
    Button viewStudentsTotalScore(){
        Button button = new Button("View Students Total Scores");
        button.setWidthUndefined();
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener((Button.ClickEvent event) -> {
            Window sub = new StudentsTotalScoreWindow(getStudentNoAndTotalScore());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        });
        return button;
    }
    
    Button approveItemAnalysis(){
        Button button = new Button("Approve Item Analysis");
        button.setWidthUndefined();
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener((Button.ClickEvent event) -> {  
            boolean result = tq.saveItemAnalysis(getItemAnalysisGrid(), 
                    getTqCoverageId());
            if(result){
                close();
            }            
        });
        
        return button;
    }
}
