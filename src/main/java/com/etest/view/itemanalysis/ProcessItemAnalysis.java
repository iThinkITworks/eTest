/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.etest.model.ItemAnalysis;
import com.etest.model.TQAnswerKey;
import com.etest.service.CellItemService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class ProcessItemAnalysis {

    TQCoverageService tq = new TQCoverageServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    private final int tqCoverageId;
    
    public ProcessItemAnalysis(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
    }
    
    int getTQCoverageId(){
        return tqCoverageId;
    }
    
    public int getTotalScoresOfStudent(int tqCoverageId, List<Character> answer){
        int totalScore = 0;
        List<TQAnswerKey> tqAnswerKey = tq.getTQCoverageAnswerKey(getTQCoverageId());
        int i = 0;
        for(TQAnswerKey t : tqAnswerKey){
            char c = cis.getOptionAnswer(t.getCellItemId()).get(t.getAnswer());            
//            System.out.println("itemId: "+t.getCellItemId()+" itemNo: "+i+" answers: "+c+" "+answer.get(i)+" "+(answer.get(i).equals(c) ? 1 : 0));
            totalScore = totalScore + (answer.get(i).equals(c) ? 1 : 0);
            i++;
        }
        return totalScore;
    }
}
