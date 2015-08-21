/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.charts;

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

    public GraphicalInventoryChart() {
        setModal(true);
        
        Panel panel = new Panel();
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Category 1", 43.2);
        dataset.setValue("Category 2", 27.9);
        dataset.setValue("Category 3", 79.5);
        
        JFreeChart chart = ChartFactory.createPieChart("Sample Pie Chart", dataset, true, true, false);
        
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
    
}
