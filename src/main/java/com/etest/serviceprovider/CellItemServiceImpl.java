/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.CellItemDAO;
import com.etest.model.CellItem;
import com.etest.service.CellItemService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class CellItemServiceImpl implements CellItemService {

    @Override
    public List<CellItem> getAllCellItem() {
        return CellItemDAO.getAllCellItem();
    }

    @Override
    public List<CellItem> getCellItemByCase(int cellCaseId) {
        return CellItemDAO.getCellItemByCase(cellCaseId);
    }

    @Override
    public CellItem getCellItemById(int cellItemId) {
        return CellItemDAO.getCellItemById(cellItemId);
    }

    @Override
    public boolean insertNewCellItem(CellItem ci) {
        return CellItemDAO.insertNewCellItem(ci);
    }

    @Override
    public boolean modifyCellItem(CellItem ci) {
        return CellItemDAO.modifyCellItem(ci);
    }

    @Override
    public boolean approveCellItem(int cellItemId) {
        return CellItemDAO.approveCellItem(cellItemId);
    }

    @Override
    public boolean removeCellItem(int cellItemId) {
        return CellItemDAO.removeCellItem(cellItemId);
    }

    @Override
    public boolean addItemKey(int cellItemId, 
            String itemKey, 
            String answer) {
        return CellItemDAO.addItemKey(cellItemId, 
                itemKey, 
                answer);
    }

    @Override
    public String getItemKey(int cellItemId, 
            String answer) {
        return CellItemDAO.getItemKey(cellItemId, 
                answer);
    }

    @Override
    public boolean isKeyExist(int cellItemId, String answer) {
        return CellItemDAO.isKeyExist(cellItemId, 
                answer);
    }

    @Override
    public int getItemKeyId(int cellItemId, 
            String answer) {
        return CellItemDAO.getItemKeyId(cellItemId, 
                answer);
    }

    @Override
    public boolean modifyItemOption(int cellItemId, 
            String optionColumn, 
            String optionValue, 
            boolean isOptionAKeyExist, 
            int itemKeyId) {
        return CellItemDAO.modifyItemOption(cellItemId, 
                optionColumn, 
                optionValue, 
                isOptionAKeyExist, 
                itemKeyId);
    }

    @Override
    public boolean modifyItemKey(int itemKeyId, 
            int cellItemId, 
            String keyValue, 
            String answer, 
            boolean isOptionAKeyExist) {
        return CellItemDAO.modifyItemKey(itemKeyId, 
            cellItemId, 
            keyValue, 
            answer, 
            isOptionAKeyExist);
    }

    @Override
    public boolean removeItemKey(int itemKeyId) {
        return CellItemDAO.removeItemKey(itemKeyId);
    }
    
}
