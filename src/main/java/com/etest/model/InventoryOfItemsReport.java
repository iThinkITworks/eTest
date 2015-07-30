/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

/**
 *
 * @author jetdario
 */
public class InventoryOfItemsReport extends Syllabus {
    
    private int remember;
    private int understand;
    private int apply;
    private int analyze;
    private int evaluate;
    private int create;
    private int total;

    public int getRemember() {
        return remember;
    }

    public int getUnderstand() {
        return understand;
    }

    public int getApply() {
        return apply;
    }

    public int getAnalyze() {
        return analyze;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public int getCreate() {
        return create;
    }

    public int getTotal() {
        return total;
    }

    public void setRemember(int remember) {
        this.remember = remember;
    }

    public void setUnderstand(int understand) {
        this.understand = understand;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }

    public void setAnalyze(int analyze) {
        this.analyze = analyze;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public void setCreate(int create) {
        this.create = create;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}
