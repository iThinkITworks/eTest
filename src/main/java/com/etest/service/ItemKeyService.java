/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.ItemKeys;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface ItemKeyService {
    
    public List<String> getAllItemKey(int cellItemId);
    
    public boolean addItemKey(int cellItemId, 
            String itemKey, 
            String answer);
    
    public String getItemKey(int cellItemId, 
            String answer);
    
    public boolean isKeyExist(int cellItemId, 
            String answer);
    
    public int getItemKeyId(int cellItemId, 
            String answer);
    
    public boolean modifyItemOption(int cellItemId, 
            String optionColumn, 
            String optionValue, 
            boolean isOptionAKeyExist, 
            int itemKeyId);
    
    public boolean modifyItemKey(int itemKeyId, 
            int cellItemId, 
            String keyValue, 
            String answer, 
            boolean isOptionKeyExist, 
            String actionDone, 
            String remarks);
    
    public boolean removeItemKey(int itemKeyId);
    
    public boolean isAnswerCorrect(int cellItemId, 
            String key, 
            String answer);
    
    public List<ItemKeys> getItemKeysByCellItemId(int cellItemId);
    
    public List<Integer> getItemKeyIdsByCellItemId(int cellItemId);
    
    public String getAnswerByItemKeyId(int itemKeyId);
}
