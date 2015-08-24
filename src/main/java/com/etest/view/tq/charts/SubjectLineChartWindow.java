/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.charts;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author jetdario
 */
public class SubjectLineChartWindow extends Window {

    private int tqCoverageId;
    
    public SubjectLineChartWindow(int tqCoverageId, String lineChartType) {
        this.tqCoverageId = tqCoverageId;
        setModal(true);
//        setHeight("100%");
        
        VerticalLayout v = new VerticalLayout();
        v.setMargin(true);
        v.setSpacing(true);
        
        if(lineChartType.equals("discrimination")){
            v.addComponent(new ReportChartWrapper(SubjectTestLineChart.discriminationIndex(getTqCoverageId()), null, null));
        } else {
            v.addComponent(new ReportChartWrapper(SubjectTestLineChart.difficultIndex(getTqCoverageId()), null, null));
        }        
        v.setWidthUndefined();
        
        setContent(v);
        getContent().setWidthUndefined();
        getContent().setHeightUndefined();
        center();
    }
    
    int getTqCoverageId(){
        return tqCoverageId;
    }
}
