/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.vaadin.ui.Grid;

/**
 *
 * @author jetdario
 */
public class TQCoverageDataGrid extends Grid {
    
    public TQCoverageDataGrid() {
        setWidth("100%");
        setSelectionMode(Grid.SelectionMode.SINGLE);
        setImmediate(true);
        setStyleName("wordwrap-grid");
        addStyleName("grid-row-font-size");
        setFooterVisible(true);
        
        addColumn("Topic", String.class);
        addColumn("Hrs Spent", Double.class);
        addColumn("Proportion(%)", Double.class);
        addColumn("Re-U(TB)", Integer.class);
        addColumn("Re-A(TB)", Integer.class);
        addColumn("Re-U(Pick)", Integer.class);
        addColumn("Re-A(Pick)", Integer.class);
        addColumn("Un-U(TB)", Integer.class);
        addColumn("Un-A(TB)", Integer.class);
        addColumn("Un-U(Pick)", Integer.class);
        addColumn("Un-A(Pick)", Integer.class);
        addColumn("Ap-U(TB)", Integer.class);
        addColumn("Ap-A(TB)", Integer.class);
        addColumn("Ap-U(Pick)", Integer.class);
        addColumn("Ap-A(Pick)", Integer.class);
        addColumn("An-U(TB)", Integer.class);
        addColumn("An-A(TB)", Integer.class);
        addColumn("An-U(Pick)", Integer.class);
        addColumn("An-A(Pick)", Integer.class);
        addColumn("Ev-U(TB)", Integer.class);
        addColumn("Ev-A(TB)", Integer.class);
        addColumn("Ev-U(Pick)", Integer.class);
        addColumn("Ev-A(Pick)", Integer.class);
        addColumn("Cr-U(TB)", Integer.class);
        addColumn("Cr-A(TB)", Integer.class);
        addColumn("Cr-U(Pick)", Integer.class);
        addColumn("Cr-A(Pick)", Integer.class);
        addColumn("Running Total", Integer.class);
        addColumn("Max Items", Integer.class);
        addColumn("remove", String.class);
                
        setFrozenColumnCount(1);
        
        getColumn("Topic").setWidth(500);
        getColumn("Hrs Spent").setWidth(100);
        getColumn("Proportion(%)").setWidth(120);                
        
        HeaderRow mainHeader = getDefaultHeaderRow();
        mainHeader.getCell("Re-U(TB)").setText("U");
        mainHeader.getCell("Re-A(TB)").setText("A");
        mainHeader.getCell("Re-U(Pick)").setText("U");
        mainHeader.getCell("Re-A(Pick)").setText("A");
        mainHeader.getCell("Un-U(TB)").setText("U");
        mainHeader.getCell("Un-A(TB)").setText("A");
        mainHeader.getCell("Un-U(Pick)").setText("U");
        mainHeader.getCell("Un-A(Pick)").setText("A");
        mainHeader.getCell("Ap-U(TB)").setText("U");
        mainHeader.getCell("Ap-A(TB)").setText("A");
        mainHeader.getCell("Ap-U(Pick)").setText("U");
        mainHeader.getCell("Ap-A(Pick)").setText("A");
        mainHeader.getCell("An-U(TB)").setText("U");
        mainHeader.getCell("An-A(TB)").setText("A");
        mainHeader.getCell("An-U(Pick)").setText("U");
        mainHeader.getCell("An-A(Pick)").setText("A");
        mainHeader.getCell("Ev-U(TB)").setText("U");
        mainHeader.getCell("Ev-A(TB)").setText("A");
        mainHeader.getCell("Ev-U(Pick)").setText("U");
        mainHeader.getCell("Ev-A(Pick)").setText("A");
        mainHeader.getCell("Cr-U(TB)").setText("U");
        mainHeader.getCell("Cr-A(TB)").setText("A");
        mainHeader.getCell("Cr-U(Pick)").setText("U");
        mainHeader.getCell("Cr-A(Pick)").setText("A");
        
        HeaderRow groupingHeader = prependHeaderRow();
        HeaderCell re_tb = groupingHeader.join(
                groupingHeader.getCell("Re-U(TB)"), 
                groupingHeader.getCell("Re-A(TB)"));
        re_tb.setHtml("in TB");
        HeaderCell re_pick = groupingHeader.join(
                groupingHeader.getCell("Re-U(Pick)"), 
                groupingHeader.getCell("Re-A(Pick)"));
        re_pick.setHtml("Pick");
        
        HeaderCell un_tb = groupingHeader.join(
                groupingHeader.getCell("Un-U(TB)"), 
                groupingHeader.getCell("Un-A(TB)"));
        un_tb.setHtml("in TB");
        HeaderCell un_pick = groupingHeader.join(
                groupingHeader.getCell("Un-U(Pick)"), 
                groupingHeader.getCell("Un-A(Pick)"));
        un_pick.setHtml("Pick");
        
        HeaderCell ap_tb = groupingHeader.join(
                groupingHeader.getCell("Ap-U(TB)"), 
                groupingHeader.getCell("Ap-A(TB)"));
        ap_tb.setHtml("in TB");
        
        HeaderCell ap_pick = groupingHeader.join(
                groupingHeader.getCell("Ap-U(Pick)"), 
                groupingHeader.getCell("Ap-A(Pick)"));
        ap_pick.setHtml("Pick");
        
        HeaderCell an_tb = groupingHeader.join(
                groupingHeader.getCell("An-U(TB)"), 
                groupingHeader.getCell("An-A(TB)"));
        an_tb.setHtml("in TB");        
        HeaderCell an_pick = groupingHeader.join(
                groupingHeader.getCell("An-U(Pick)"), 
                groupingHeader.getCell("An-A(Pick)"));
        an_pick.setHtml("Pick");
        
        HeaderCell ev_tb = groupingHeader.join(
                groupingHeader.getCell("Ev-U(TB)"), 
                groupingHeader.getCell("Ev-A(TB)"));
        ev_tb.setHtml("in TB");
        HeaderCell ev_pick = groupingHeader.join(
                groupingHeader.getCell("Ev-U(Pick)"), 
                groupingHeader.getCell("Ev-A(Pick)"));
        ev_pick.setHtml("Pick");
        
        HeaderCell cr_tb = groupingHeader.join(
                groupingHeader.getCell("Cr-U(TB)"), 
                groupingHeader.getCell("Cr-A(TB)"));
        cr_tb.setHtml("in TB");
        HeaderCell cr_pick = groupingHeader.join(
                groupingHeader.getCell("Cr-U(Pick)"), 
                groupingHeader.getCell("Cr-A(Pick)"));
        cr_pick.setHtml("Pick");
        
        mainHeader.setStyleName("boldheader");
        groupingHeader.setStyleName("boldheader");
        
        setCellStyleGenerator((Grid.CellReference cellReference) -> {
            if(!cellReference.getPropertyId().toString().equals("remove") &&  
                    !cellReference.getPropertyId().toString().equals("Topic")){
                return "align-center";
            } else {
                return null;
            }
//            return null;
        });
    }    
}
