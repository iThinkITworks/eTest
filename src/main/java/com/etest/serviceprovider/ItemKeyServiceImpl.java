/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.ItemKeyDAO;
import com.etest.model.ItemKeys;
import com.etest.service.ItemKeyService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class ItemKeyServiceImpl implements ItemKeyService {

    @Override
    public List<String> getAllItemKey(int cellItemId) {
        return ItemKeyDAO.getAllItemKey(cellItemId);
    }

    @Override
    public boolean addItemKey(int cellItemId, 
            String itemKey, 
            String answer) {
        return ItemKeyDAO.addItemKey(cellItemId, 
                itemKey, 
                answer);
    }

    @Override
    public String getItemKey(int cellItemId, 
            String answer) {
        return ItemKeyDAO.getItemKey(cellItemId, 
                answer);
    }

    @Override
    public boolean isKeyExist(int cellItemId, 
            String answer) {
        return ItemKeyDAO.isKeyExist(cellItemId, 
                answer);
    }

    @Override
    public int getItemKeyId(int cellItemId, 
            String answer) {
        return ItemKeyDAO.getItemKeyId(cellItemId, 
                answer);
    }

    @Override
    public boolean modifyItemOption(int cellItemId, 
            String optionColumn, 
            String optionValue, 
            boolean isOptionAKeyExist, 
            int itemKeyId) {
        return ItemKeyDAO.modifyItemOption(cellItemId, 
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
            boolean isOptionKeyExist, 
            String actionDone, 
            String remarks) {
        return ItemKeyDAO.modifyItemKey(itemKeyId, 
                cellItemId, 
                keyValue, 
                answer, 
                isOptionKeyExist, 
                actionDone, 
                remarks);
    }

    @Override
    public boolean removeItemKey(int itemKeyId) {
        return ItemKeyDAO.removeItemKey(itemKeyId);
    }

    @Override
    public boolean isAnswerCorrect(int cellItemId, 
            String key, 
            String answer) {
        return ItemKeyDAO.isAnswerCorrect(cellItemId, 
                key, 
                answer);
    }

    @Override
    public List<ItemKeys> getItemKeysByCellItemId(int cellItemId) {
        return ItemKeyDAO.getItemKeysByCellItemId(cellItemId);
    }

    @Override
    public List<Integer> getItemKeyIdsByCellItemId(int cellItemId) {
        return ItemKeyDAO.getItemKeyIdsByCellItemId(cellItemId);
    }

    @Override
    public String getAnswerByItemKeyId(int itemKeyId) {
        return ItemKeyDAO.getAnswerByItemKeyId(itemKeyId);
    }
    
}
