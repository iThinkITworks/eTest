/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq.charts;

import com.etest.service.CurriculumService;
import com.etest.service.ReportService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.ReportServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
public class ItemAnalysisGraphicalViewAll extends Window {

    CurriculumService cs = new CurriculumServiceImpl();
    ReportService rs = new ReportServiceImpl();
    
    private int curriculumId;
    int percentage = 0;
    
    String[] difficulty = {"Very Difficult", "Difficult", "Average", "Easy", "Very Easy"};
    String[] discrimination = {"Poor Items", "Marginal Items", "Reasonably Good Item", "Very Good Item"};
    
    public ItemAnalysisGraphicalViewAll(int curriculumId) {
        this.curriculumId = curriculumId;
        
        setModal(true);
        setHeight("100%");
        
        VerticalLayout v = new VerticalLayout();
        v.setMargin(true);
        v.setSpacing(true);
        
        Label title = new Label();
        title.setCaption("Item Analysis of "+cs.getCurriculumById(getCurriculumId()).getSubject());
        title.setWidthUndefined();
        v.addComponent(title);
        v.setComponentAlignment(title, Alignment.TOP_CENTER);        
        
        HorizontalLayout h = new HorizontalLayout();
        h.setSpacing(true);
                
        h.addComponent(getDiscriminationIndexChart());                
        h.addComponent(getDifficultIndexChart());
                
        h.setWidthUndefined();
        h.setHeightUndefined();
        
        v.addComponent(h);
        v.setWidthUndefined();
        
        setContent(v);
        getContent().setWidthUndefined();
//        getContent().setHeightUndefined();
        center();
    }
    
    JFreeChartWrapper getDiscriminationIndexChart(){
        DefaultPieDataset discriminationDataSet = new DefaultPieDataset();        
        for(String s : discrimination){
            switch(s){
                case "Poor Items":{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDiscriminationIndex(getCurriculumId(), 0, 0.19));
                    discriminationDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }                
                case "Marginal Items":{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDiscriminationIndex(getCurriculumId(), 0.20, 0.29));
                    discriminationDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }                
                case "Reasonably Good Item":{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDiscriminationIndex(getCurriculumId(), 0.30, 0.39));
                    discriminationDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }  
                default:{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDiscriminationIndex(getCurriculumId(), 0.40, 1));
                    discriminationDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }
            }
            
        }
        
        JFreeChart discriminationIndexChart = ChartFactory.createPieChart(
                "Discrimination Index", 
                discriminationDataSet, 
                true, 
                true, 
                false);
                
        
        return new ReportChartWrapper(discriminationIndexChart, "550px", "550px");
    }
    
    JFreeChartWrapper getDifficultIndexChart(){
        DefaultPieDataset difficultyDataSet = new DefaultPieDataset();        
        for(String s : difficulty){
            switch(s){
                case "Very Difficult":{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDifficultyIndex(getCurriculumId(), 0, 0.19));
                    difficultyDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }                
                case "Difficult":{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDifficultyIndex(getCurriculumId(), 0.20, 0.39));
                    difficultyDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }                
                case "Average":{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDifficultyIndex(getCurriculumId(), 0.40, 0.60));
                    difficultyDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }                
                case "Easy":{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDifficultyIndex(getCurriculumId(), 0.61, 0.80));
                    difficultyDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }
                default:{
                    percentage = (int)calculatePercentageOfAnalyzedItems(
                            rs.getTotalAnalyzedItemsBySubject(getCurriculumId()), 
                            rs.getTotalItemByDifficultyIndex(getCurriculumId(), 0.81, 1));
                    difficultyDataSet.setValue(s+" "+percentage+"%", percentage);
                    break;
                }
            }
            
        }
        
        JFreeChart difficultIndexChart = ChartFactory.createPieChart(
                "Difficult Index", 
                difficultyDataSet, 
                true, 
                true, 
                false);
                        
        return new ReportChartWrapper(difficultIndexChart, "550px", "550px");
    }
    
    double calculatePercentageOfAnalyzedItems(int totalAnalyzedItems, int totalItemsPerIndex){
        return CommonUtilities.roundOffToWholeNumber(((double)totalItemsPerIndex/totalAnalyzedItems)*100);
    }
        
    int getCurriculumId(){
        return curriculumId;
    }
}
