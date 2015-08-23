/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.charts;

import org.jfree.chart.JFreeChart;
import org.vaadin.addon.JFreeChartWrapper;

/**
 *
 * @author jetdario
 */
public class ReportChartWrapper extends JFreeChartWrapper {

    public ReportChartWrapper(JFreeChart chart, String width, String height) {
        super(chart);
        setResource("src", getSource());
        
        if(width != null || height != null){
            setWidth(width);
            setHeight(height);
        }        
    }    
    
}
