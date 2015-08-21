/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.charts;

import com.etest.model.Curriculum;
import com.etest.service.CurriculumService;
import com.etest.service.ReportService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.ReportServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.vaadin.addon.JFreeChartWrapper;

/**
 *
 * @author jetdario
 */
public class GraphicalInventoryChart extends Window {

    CurriculumService cs = new CurriculumServiceImpl();
    ReportService rs = new ReportServiceImpl();
    
    public GraphicalInventoryChart() {
        setModal(true);
        
        Panel panel = new Panel();
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(Curriculum c : cs.getAllCurriculum()){
            dataset.setValue(c.getSubject()+" ("+calculatePercentageOfItemsPerSubject(
                    rs.getTotalItems(), 
                    rs.getTotalItemsBySubject(c.getCurriculumId()))+"%)", 
                    calculatePercentageOfItemsPerSubject(rs.getTotalItems(), 
                            rs.getTotalItemsBySubject(c.getCurriculumId())));
        }
        
//        dataset.setValue("Category 1", 43.2);
//        dataset.setValue("Category 2", 27.9);
//        dataset.setValue("Category 3", 79.5);
        
        JFreeChart chart = ChartFactory.createPieChart("All Subjects", dataset, true, false, false);
                
        JFreeChartWrapper wrapper = new JFreeChartWrapper(chart){
            
            @Override
            public void attach(){
                super.attach();
                setResource("src", getSource());
            }
            
        };
        
        panel.setContent(wrapper);
        panel.getContent().setWidthUndefined();
        panel.setHeightUndefined();
        
        setContent(panel);
        getContent().setWidthUndefined();
        getContent().setHeightUndefined();
        center();
    }
    
    double calculatePercentageOfItemsPerSubject(int totalItems, int totalItemsPerSubject){
        return CommonUtilities.roundOffToTwoDecimal(((double)totalItemsPerSubject/totalItems)*100);
    }
        
}
