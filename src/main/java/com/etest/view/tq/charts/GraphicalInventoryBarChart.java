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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.vaadin.addon.JFreeChartWrapper;

/**
 *
 * @author jetdario
 */
public class GraphicalInventoryBarChart extends Window {

    CurriculumService cs = new CurriculumServiceImpl();
    ReportService rs = new ReportServiceImpl();
    
    public GraphicalInventoryBarChart() {
        setModal(true);
        
        VerticalLayout v = new VerticalLayout();
        v.setMargin(true);
        
//        Panel panel = new Panel();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Curriculum c : cs.getAllCurriculum()){
            for(int i=0; i < 2; i++){
                if(i == 0){
                    dataset.addValue(rs.getTotalCasesBySubject(c.getCurriculumId()), 
                            "Cases", c.getSubject());
                } else {
                    dataset.addValue(rs.getTotalItemsBySubject(c.getCurriculumId()), 
                            "Items", c.getSubject());
                }
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "Inventory of Items and Cases", 
                "Subjects", 
                "", 
                dataset, PlotOrientation.VERTICAL, 
                true, 
                true, 
                false);
        
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        Color color;
        
        for (int i = 0; i < dataset.getRowCount(); i++){
            if(i%2 == 0){
                renderer.setSeriesPaint(i, new Color(0, 0, 255)); 
            } else {
                renderer.setSeriesPaint(i, new Color(255, 0, 0)); 
            }
        }        
        
//        JFreeChartWrapper wrapper = new JFreeChartWrapper(chart){
//            
//            @Override
//            public void attach(){
//                super.attach();
//                setResource("src", getSource());
//            }
//            
//        };
        
        v.addComponent(new ReportChartWrapper(chart, null, null));
        v.setWidthUndefined();
        v.setHeightUndefined();
        
        setContent(v);
        getContent().setWidthUndefined();
        getContent().setHeightUndefined();
        center();
    }
    
    double calculatePercentageOfCasesPerSubject(int totalCases, int totalCasesPerSubject){
        return CommonUtilities.roundOffToTwoDecimal(((double)totalCasesPerSubject/totalCases)*100);
    }
}
