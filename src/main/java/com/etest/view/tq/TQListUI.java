/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.model.TQCoverage;
import com.etest.pdfgenerator.TQViewer;
import com.etest.service.CurriculumService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
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
public class TQListUI extends TQListTableProperties{

    TQCoverageService tq = new TQCoverageServiceImpl();
    CurriculumService cs = new CurriculumServiceImpl();
        
    public TQListUI() {
        populateDataTable();
    }
    
    public Table populateDataTable(){
        removeAllItems();
        int i = 0;
        for(TQCoverage t : tq.getAllTQCoverage()){
            VerticalLayout v = new VerticalLayout();
            v.setWidth("100%");
            
            Button view = new Button("view");
            view.setSizeFull();
            view.setData(t.getTqCoverageId());
            view.setIcon(FontAwesome.VIDEO_CAMERA);
            view.addStyleName(ValoTheme.BUTTON_LINK);
            view.addStyleName(ValoTheme.BUTTON_TINY);
            view.addStyleName(ValoTheme.BUTTON_QUIET);
            view.addStyleName("button-container");
            view.addClickListener(remarksBtnClickListener);
            v.addComponent(view);
            v.setComponentAlignment(view, Alignment.MIDDLE_LEFT);
            
            Button approve = new Button("status");
            approve.setSizeFull();
            approve.setData(t.getTqCoverageId());            
            approve.addStyleName(ValoTheme.BUTTON_LINK);
            approve.addStyleName(ValoTheme.BUTTON_TINY);
            approve.addStyleName(ValoTheme.BUTTON_QUIET);
            approve.addStyleName("button-container");
            v.addComponent(approve);
            v.setComponentAlignment(approve, Alignment.MIDDLE_LEFT);
            
            Button print = new Button("print");
            print.setSizeFull();
            print.setData(t.getTqCoverageId());  
            print.setIcon(FontAwesome.PRINT);
            print.addStyleName(ValoTheme.BUTTON_LINK);
            print.addStyleName(ValoTheme.BUTTON_TINY);
            print.addStyleName(ValoTheme.BUTTON_QUIET);
            print.addStyleName("button-container");
            print.addClickListener(remarksBtnClickListener);
            v.addComponent(print);
            v.setComponentAlignment(print, Alignment.MIDDLE_LEFT);
            
            if(t.getStatus() == 0){ 
                approve.setIcon(FontAwesome.THUMBS_DOWN); 
                print.setVisible(false);
            }
            else { 
                approve.setIcon(FontAwesome.THUMBS_UP); 
                print.setVisible(true);
            } 
            
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
    
    Button.ClickListener remarksBtnClickListener = (Button.ClickEvent event) -> {
        if(event.getButton().getCaption().equals("view")){
            Window sub = new TQCoverageWindow((int) event.getButton().getData());
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
            sub.addCloseListener((Window.CloseEvent e) -> {
                populateDataTable();
            });
        } else {
            Window pdf = new TQViewer((int) event.getButton().getData());
            if(pdf.getParent() == null){
                UI.getCurrent().addWindow(pdf);
            }
        }
    };
}
