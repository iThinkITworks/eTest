/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.itemanalysis;

import com.etest.dao.CellItemDAO;
import com.etest.dao.TQCoverageDAO;
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
public class ItemAnalysisInterpretation {

    TQCoverageService tq = new TQCoverageServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
        
    public static int getTotalScoresOfAllStudent(int tqCoverageId, List<Character> answer){
        int totalScore = 0;
        List<TQAnswerKey> tqAnswerKey = TQCoverageDAO.getTQCoverageAnswerKey(tqCoverageId);
        int i = 0;
        for(TQAnswerKey t : tqAnswerKey){
            char c = CellItemDAO.getOptionAnswer(t.getCellItemId()).get(t.getAnswer());            
            totalScore = totalScore + (answer.get(i).equals(c) ? 1 : 0);
            i++;
        }
        return totalScore;
    }
    
    public static void getTotalScoresForUpperAndLower(int tqCoverageId, List<Character> answer){
        int totalScore = 0;
        List<TQAnswerKey> tqAnswerKey = TQCoverageDAO.getTQCoverageAnswerKey(tqCoverageId);
        int i = 0;
        for(TQAnswerKey t : tqAnswerKey){
            char c = CellItemDAO.getOptionAnswer(t.getCellItemId()).get(t.getAnswer()); 
            System.out.print((answer.get(i).equals(c) ? 1 : 0)+" ");
            totalScore = totalScore + (answer.get(i).equals(c) ? 1 : 0);
            i++;
        }
//        return totalScore;
    }
    
    public static String getInterpretation(double value){
        String difficultyIndex;
        
        if(value == 0.00 || value < 0.20){
            difficultyIndex = "Very Difficult";
        } else if(value == 0.20 || value < 0.40){
            difficultyIndex = "Difficult";
        } else if(value == 0.40 || value < 0.61){
            difficultyIndex = "Average";
        } else if(value == 0.61 || value < 0.81){
            difficultyIndex = "Easy";
        } else {
            difficultyIndex = "Very Easy";
        }
        
        return difficultyIndex;
    }
}
