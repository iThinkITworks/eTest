/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.common.BloomsClassTaxonomy;
import com.etest.common.BloomsClassTaxonomy.BloomsClass;
import com.etest.dao.TQCoverageDAO;
import com.etest.model.CellCase;
import com.etest.model.CellItem;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.service.TQCoverageService;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class TQCoverageServiceImpl implements TQCoverageService {

    @Override
    public double calculateTotalHourSpent(Grid grid) {
        double avg = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            avg = avg + CommonUtilities.convertStringToDouble(item.getItemProperty("Hrs Spent").getValue().toString());
        }
                
        return avg;
    }

    @Override
    public void calculateProportion(Grid grid) {
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            item.getItemProperty("Proportion(%)").setValue(
                    CommonUtilities.roundOffToWholeNumber((CommonUtilities.convertStringToDouble(
                            item.getItemProperty("Hrs Spent").getValue().toString()) / calculateTotalHourSpent(grid))*100)                    
            );
        }  
    }

    @Override
    public void calculateMaxItems(Grid grid, TextField totalItems) {
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            item.getItemProperty("Max Items").setValue(
                    CommonUtilities.roundOffToWholeNumber((CommonUtilities.convertStringToDouble(
                            item.getItemProperty("Proportion(%)").getValue().toString()) / 100) * 
                            CommonUtilities.convertStringToDouble(totalItems.getValue().trim())
                    )                    
            );
        }
    }

    @Override
    public double calculateTotalProportion(Grid grid) {
        double proportion = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            proportion = proportion + CommonUtilities.convertStringToDouble(item.getItemProperty("Proportion(%)").getValue().toString());
        }
                
        return (proportion > 100 ? 100 : proportion);
    }

    @Override
    public double calculateTotalMaxItems(Grid grid) {
        double maxItems = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            maxItems = maxItems + CommonUtilities.convertStringToDouble(item.getItemProperty("Max Items").getValue().toString());
        }
                
        return maxItems;
    }

    @Override
    public int getBloomsClassId(String bloomsClass) {
        return TQCoverageDAO.getBloomsClassId(bloomsClass);
    }    

    @Override
    public int getTotalForBloomsClassColumn(Grid grid, String propertyId) {
        int total = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            total = total + CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
        }
                
        return total;
    }
}
