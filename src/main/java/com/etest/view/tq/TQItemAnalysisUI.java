/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.model.TQCoverage;
import com.etest.service.CurriculumService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.view.itemanalysis.FileUploadWindow;
import com.etest.view.itemanalysis.ItemAnalysisDataTable;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pl.exsio.plupload.manager.PluploadManager;

/**
 *
 * @author jetdario
 */
public class TQItemAnalysisUI extends VerticalLayout {

    TQCoverageService tq = new TQCoverageServiceImpl();
    CurriculumService cs = new CurriculumServiceImpl();
    
    private int tqCoverageId = 0;
    List<String> upperGroupStudentNo = new ArrayList<>();
    List<String> lowerGroupStudentNo = new ArrayList<>();
    List<Integer> itemIds;
    Map<String, List<Character>> studentNoAndAnswer;
    private double groupTotalForProportion = 0;
    File excelFile;
    
    Table table = new ItemAnalysisDataTable();
    PluploadManager manager;
    
    public TQItemAnalysisUI() {
        setWidth("100%");        
        
        addComponent(populateDataTable());
    }
        
    public Table populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(TQCoverage t : tq.getAllTQCoverage()){   
            VerticalLayout v = new VerticalLayout();
            v.setWidth("100%");
            
            Button analyze = new Button();
            analyze.setSizeFull();
            analyze.setData(t.getTqCoverageId());
            analyze.setCaption((t.getAnalyzed() == 0) ? "Unanalyze" : "Analyzed");
            analyze.setIcon(FontAwesome.BULLSEYE);
            analyze.addStyleName(ValoTheme.BUTTON_LINK);
            analyze.addStyleName(ValoTheme.BUTTON_TINY);
            analyze.addStyleName(ValoTheme.BUTTON_QUIET);
            analyze.addStyleName("button-container");
            analyze.addClickListener((Button.ClickEvent event) -> {                
                tqCoverageId = (int) event.getButton().getData();
                Window sub = new FileUploadWindow(tqCoverageId, event.getButton());
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
            });           
            v.addComponent(analyze);
            v.setComponentAlignment(analyze, Alignment.MIDDLE_LEFT);
            
            Button view = new Button("View");
            view.setSizeFull();
            view.setData(t.getTqCoverageId());
            view.setIcon(FontAwesome.VIDEO_CAMERA);
            view.addStyleName(ValoTheme.BUTTON_LINK);
            view.addStyleName(ValoTheme.BUTTON_TINY);
            view.addStyleName(ValoTheme.BUTTON_QUIET);
            view.addStyleName("button-container");
            view.setVisible((t.getAnalyzed() == 0));
            v.addComponent(view);
            v.setComponentAlignment(view, Alignment.MIDDLE_LEFT);
            
            table.addItem(new Object[]{
                t.getExamTitle(), 
                cs.getCurriculumById(t.getCurriculumId()).getSubject(), 
                t.getDateCreated(), 
                t.getTotalHoursCoverage(), 
                t.getTotalItems(), 
                v
            }, i);
            i++;
        }
        table.setPageLength(table.size()); 
        table.setImmediate(true);
        
        return table;
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
}
