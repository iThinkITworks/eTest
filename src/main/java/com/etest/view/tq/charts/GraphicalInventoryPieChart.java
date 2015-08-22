/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.charts;

import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.service.ReportService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.ReportServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.vaadin.addon.JFreeChartWrapper;

/**
 *
 * @author jetdario
 */
public class GraphicalInventoryPieChart extends Window {

    CurriculumService cs = new CurriculumServiceImpl();
    ReportService rs = new ReportServiceImpl();
    TQCoverageService tq = new TQCoverageServiceImpl();
    
    public enum BloomsClass {
        Remember, Understand, Apply, Analyze, Evaluate, Create
    }
    
    private int curriculumId;
    
    public GraphicalInventoryPieChart(int curriculumId) {
        this.curriculumId = curriculumId;
        setModal(true);
        
        int percentage = 0;
        VerticalLayout v = new VerticalLayout();        
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        for(BloomsClass b : BloomsClass.values()){
            percentage = (int)calculatePercentageOfItemsPerBloomsClass(
                    rs.getTotalItemsBySubject(getCurriculumId()), 
                    rs.getTotalItemsByBloomsCass(
                            getCurriculumId(), 
                            tq.getBloomsClassId(b.toString())));
            
            dataset.setValue(b+" "+percentage+"%", percentage);
        }
                
        JFreeChart chart = ChartFactory.createPieChart(
                "Inventory of Items for "+cs.getCurriculumById(getCurriculumId()).getSubject(), 
                dataset, 
                true, 
                true, 
                false);
                
        JFreeChartWrapper wrapper = new JFreeChartWrapper(chart){
            
            @Override
            public void attach(){
                super.attach();
                setResource("src", getSource());
            }
            
        };
        
        v.addComponent(wrapper);
        v.setWidthUndefined();
        v.setHeightUndefined();
        
        setContent(v);
        getContent().setWidthUndefined();
        getContent().setHeightUndefined();
        center();
    }
    
    double calculatePercentageOfItemsPerBloomsClass(int totalItems, int totalItemsPerSubject){
        return CommonUtilities.roundOffToWholeNumber(((double)totalItemsPerSubject/totalItems)*100);
    }
        
    int getCurriculumId(){
        return curriculumId;
    }
}
