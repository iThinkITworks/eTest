/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.itemanalysis;

import com.etest.model.TQCoverage;
import com.etest.service.CurriculumService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.view.tq.itemanalysis.FileUploadWindow;
import com.etest.view.tq.itemanalysis.ItemAnalysisDataTableProperties;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class TQItemAnalysisUI extends ItemAnalysisDataTableProperties {

    TQCoverageService tq = new TQCoverageServiceImpl();
    CurriculumService cs = new CurriculumServiceImpl();
    
    private int tqCoverageId = 0;
    
    public TQItemAnalysisUI() {        
        populateDataTable();
    }
        
    public Table populateDataTable(){
        removeAllItems();
        int i = 0;
        for(TQCoverage t : tq.getAllTQCoverage()){   
            VerticalLayout v = new VerticalLayout();
            v.setWidth("100%");
            
            Button analyze = new Button();
            analyze.setWidthUndefined();
            analyze.setData(t.getTqCoverageId());
            analyze.setCaption((t.getAnalyzed() == 0) ? "Unanalyze" : "Analyzed");
            analyze.setIcon(FontAwesome.BULLSEYE);
            analyze.addStyleName(ValoTheme.BUTTON_LINK);
            analyze.addStyleName(ValoTheme.BUTTON_TINY);
            analyze.addStyleName(ValoTheme.BUTTON_QUIET);
            analyze.addStyleName("button-container");      
            analyze.setEnabled(t.getAnalyzed() == 0);
            analyze.addClickListener(buttonClickListener);
            v.addComponent(analyze);
            v.setComponentAlignment(analyze, Alignment.MIDDLE_LEFT);
            
            Button view = new Button("View");
            view.setWidthUndefined();
            view.setData(t.getTqCoverageId());
            view.setIcon(FontAwesome.VIDEO_CAMERA);
            view.addStyleName(ValoTheme.BUTTON_LINK);
            view.addStyleName(ValoTheme.BUTTON_TINY);
            view.addStyleName(ValoTheme.BUTTON_QUIET);
            view.addStyleName("button-container");
            view.setVisible((t.getAnalyzed() != 0));
            view.addClickListener(buttonClickListener);
            v.addComponent(view);
            v.setComponentAlignment(view, Alignment.MIDDLE_LEFT);
            
            addItem(new Object[]{
                t.getExamTitle(), 
                cs.getCurriculumById(t.getCurriculumId()).getSubject(), 
                t.getDateCreated(), 
                t.getTotalHoursCoverage(), 
                t.getTotalItems(), 
                v
            }, i);
            i++;
        }
        setPageLength(size()); 
        
        return this;
    }
    
    int getTqCoverageId(){
        return tqCoverageId;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(event.getButton().getCaption().equals("View")){
            Window sub = new ItemAnalysisViewResultWindow((int) event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        } else {
            Window sub = new FileUploadWindow((int) event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
            sub.addCloseListener((Window.CloseEvent e) -> {
                populateDataTable();
            });
        }
    };
}
