/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.etest.model.CellItem;
import com.etest.service.CellItemService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class ItemAnalysisViewResultWindow extends Window {

    CellItemService cs = new CellItemServiceImpl();
    Grid grid = new Grid();
    
    private int tqCoverageId;
    
    public ItemAnalysisViewResultWindow(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
        
        setCaption("ITEM ANALYSIS");
        setWidth("700px");
        setHeight("100%");
        setModal(true);
        center();
        
        grid.setWidth("100%");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setImmediate(true);
        grid.setFooterVisible(true);
        
        grid.addColumn("itemNo", Integer.class);
        grid.addColumn("difficultIndex", Double.class);
        grid.addColumn("difficultIndexInterpretation", String.class);
        grid.addColumn("discriminationIndex", Double.class);
        grid.addColumn("discriminationIndexInterpretation", String.class);
        grid.addColumn("cellItemId", Integer.class);
        
        HeaderRow mainHeader = grid.getDefaultHeaderRow();
        mainHeader.getCell("difficultIndex").setText("Difficulty");
        mainHeader.getCell("difficultIndexInterpretation").setText("Interpretation");
        mainHeader.getCell("discriminationIndex").setText("Discrimination");
        mainHeader.getCell("discriminationIndexInterpretation").setText("Interpretation");
        mainHeader.getCell("cellItemId").setText("Item ID");
          
        int itemNo = 1;
        for(CellItem ci : cs.getItemAnalysisResult(tqCoverageId)){
            grid.addRow(
                    itemNo, 
                    ci.getDifficultIndex(), 
                    ItemAnalysisInterpretation.getDifficultyInterpretation(ci.getDifficultIndex()), 
                    ci.getDiscriminationIndex(), 
                    ItemAnalysisInterpretation.getDiscriminationInterpretation(ci.getDiscriminationIndex()), 
                    ci.getCellItemId()
            );
            itemNo++;
        }
        
        grid.setHeight("100%");
        grid.removeColumn("cellItemId");
        
        setContent(grid);
    }
    
}
