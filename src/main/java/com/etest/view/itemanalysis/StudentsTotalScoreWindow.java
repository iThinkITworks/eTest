/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.etest.utilities.CommonUtilities;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Window;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author jetdario
 */
public class StudentsTotalScoreWindow extends Window {

    Grid grid = new Grid();
    Map<String, Integer> studentNoAndTotalScore;
    
    public StudentsTotalScoreWindow(Map<String, Integer> studentNoAndTotalScore) {
        this.studentNoAndTotalScore = studentNoAndTotalScore;
        
        setCaption("STUDENT's TOTAL SCORES");
        setHeight("100%");
        setModal(true);
        center();
        
        grid.addColumn("Student No.", String.class);
        grid.addColumn("Total Score", Integer.class);
        
        Stream<Map.Entry<String, Integer>> sorted = studentNoAndTotalScore.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        
        Iterator iterator = sorted.iterator();
        while(iterator.hasNext()){
            String[] s = iterator.next().toString().split("=");
            grid.addRow(!s[0].contains(".") ? s[0] : s[0].replaceAll("0*$", "").replaceAll("\\.$", ""), CommonUtilities.convertStringToInt(s[1]));
        }
                
        grid.setCellStyleGenerator((Grid.CellReference cellReference) -> {
            return "align-center-only";
        });
        
        grid.setHeight("100%");
        setContent(grid);
        getContent().setWidthUndefined();
    }
    
    
}
