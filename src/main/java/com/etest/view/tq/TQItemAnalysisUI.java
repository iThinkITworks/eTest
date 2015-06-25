/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.model.ItemAnalysis;
import com.etest.view.itemanalysis.ItemAnalysisInterpretation;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.FileInputStream;
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
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.manager.PluploadManager;

/**
 *
 * @author jetdario
 */
public class TQItemAnalysisUI extends VerticalLayout {

    private final int tqCoverageId = 28;
    File excelFile;
    
    public TQItemAnalysisUI() {
        setWidth("100%");        
        
        PluploadManager manager = new PluploadManager();
        manager.getUploader().setMaxFileSize("5mb");
        manager.getUploader().addFileUploadedListener((PluploadFile file) -> {
            Notification.show("I've just uploaded file: "
                    + file.getName());
            excelFile = new File(file.getUploadedFile().toString());
//            System.out.println("file: "+excelFile.getAbsolutePath());
            readContentFromExcelFile(excelFile);
        });
        
        manager.getUploader().addErrorListener((PluploadError error) -> {
            Notification.show("There was an error: "
                 + error.getMessage() + " (" + error.getType() + ")",
                 Notification.Type.ERROR_MESSAGE);
        });        
        
        addComponent(manager);
    }
    
    void readContentFromExcelFile(File excelFile){        
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelFile));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(wb.getActiveSheetIndex());
            HSSFRow row;
            HSSFCell cell;
                        
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
                   if(row != null){
                       cell = row.getCell(c);
                       if(cell != null){                           
                           if(c != 0){         
                               if(r == 0){
//                                   System.out.println(cell.toString()+" ");
                                   itemAnalysis.setStudentNumber(cell.toString());
                               } else {
                                   answer.add(cell.toString().charAt(0));
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
                     
//            ItemAnalysisInterpretation p = new ItemAnalysisInterpretation(tqCoverageId);
            int totalScore = 0;
            Map<String, Integer> studentNoAndTotalScore = new HashMap<>();
            Map<String, List<Character>> studentNoAndAnswer = new HashMap<>();
            for(ItemAnalysis i : itemAnalysisList){
                studentNoAndTotalScore.put(i.getStudentNumber(), ItemAnalysisInterpretation.getTotalScoresOfAllStudent(tqCoverageId, i.getAnswer()));
                studentNoAndAnswer.put(i.getStudentNumber(), i.getAnswer());
            }
            
            List<String> upperGroupStudentNo = getUpperGroupStudent(studentNoAndTotalScore);
            List<String> lowerGroupStudentNo = getLowerGroupStudent(studentNoAndTotalScore);
            
            for(String s : upperGroupStudentNo){
                ItemAnalysisInterpretation.getTotalScoresForUpperAndLower(tqCoverageId, studentNoAndAnswer.get(s));
                System.out.println("");
//                System.out.println("upper: "+studentNoAndAnswer.get(s));
            }
            
            for(String s : lowerGroupStudentNo){
                ItemAnalysisInterpretation.getTotalScoresForUpperAndLower(tqCoverageId, studentNoAndAnswer.get(s));
                System.out.println("");
//                System.out.println("lower: "+studentNoAndAnswer.get(s));
            }
           
        } catch (IOException ex) {
            Logger.getLogger(TQItemAnalysisUI.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public List<String> getLowerGroupStudent(Map<String, Integer> studentNoAndTotalScore){
        Stream<Map.Entry<String, Integer>> sorted = studentNoAndTotalScore.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue());
        
        List<String> lowerGroupStudentNo = new ArrayList<>();
        
            double lowerGroupTotal = 0;        
            if(studentNoAndTotalScore.size() < 30){
                lowerGroupTotal = studentNoAndTotalScore.size() * .5;
            }        
        
            int i = 1;
            Iterator iterator = sorted.iterator();
            while(iterator.hasNext()){
                if(i > lowerGroupTotal){
                    break;
                }
                String[] s = iterator.next().toString().split("=");
                lowerGroupStudentNo.add(s[0]);
//                System.out.println("studentNo: "+s[0]+" totalScore: "+s[1]);
                i++;
            } 
        
            return lowerGroupStudentNo;
    }
    
    public List<String> getUpperGroupStudent(Map<String, Integer> studentNoAndTotalScore){
        Stream<Map.Entry<String, Integer>> sorted = studentNoAndTotalScore.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
            
        List<String> upperGroupStudentNo = new ArrayList<>();
        
            double upperGroupTotal = 0;        
            if(studentNoAndTotalScore.size() < 30){
                upperGroupTotal = studentNoAndTotalScore.size() * .5;
            }
        
            int i = 1;
            Iterator iterator = sorted.iterator();
            while(iterator.hasNext()){
                if(i > upperGroupTotal){
                    break;
                }
                String[] s = iterator.next().toString().split("=");
                upperGroupStudentNo.add(s[0]);
//                System.out.println("studentNo: "+s[0]+" totalScore: "+s[1]);
                i++;
            }
            
        return upperGroupStudentNo;    
    }
}
