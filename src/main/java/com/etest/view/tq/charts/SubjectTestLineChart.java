/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.charts;

import com.etest.dao.CellItemDAO;
import com.etest.model.CellItem;
import com.etest.service.CellItemService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author jetdario
 */
public class SubjectTestLineChart{
    
    TQCoverageService tq = new TQCoverageServiceImpl();
    CellItemService cs = new CellItemServiceImpl();
    
    public static JFreeChart discriminationIndex(int tqCoverageId){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                
        for(CellItem ci : CellItemDAO.getItemAnalysisResult(tqCoverageId)){
            dataset.setValue((int)(ci.getDiscriminationIndex()*100), "Discrimination Index", String.valueOf(ci.getItemNo()));
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
                "Item Analysis Report", 
                "Item No.", 
                "Discrimination Index (%)", 
                dataset, 
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                false);
                
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        
        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        // customise the renderer...
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setFillPaint(Color.white);
        
        return chart;
    }    
    
    public static JFreeChart difficultIndex(int tqCoverageId){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                
        for(CellItem ci : CellItemDAO.getItemAnalysisResult(tqCoverageId)){
            dataset.setValue((int)(ci.getDifficultIndex()*100), "Difficult Index", String.valueOf(ci.getItemNo()));
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
                "Item Analysis Report", 
                "Item No.", 
                "Difficult Index (%)", 
                dataset, 
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                false);
                
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        
        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        // customise the renderer...
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setFillPaint(Color.white);
        
        return chart;
    }
}
