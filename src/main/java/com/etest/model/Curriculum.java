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
public class Curriculum extends Users {
    
    private int curriculumId;
    private int yearLevel;
    private String subject;
    private String descriptiveTitle;
    private int normCourseOffering;

    public int getCurriculumId() {
        return curriculumId;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescriptiveTitle() {
        return descriptiveTitle;
    }

    public int getNormCourseOffering() {
        return normCourseOffering;
    }

    public void setCurriculumId(int curriculumId) {
        this.curriculumId = curriculumId;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescriptiveTitle(String descriptiveTitle) {
        this.descriptiveTitle = descriptiveTitle;
    }

    public void setNormCourseOffering(int normCourseOffering) {
        this.normCourseOffering = normCourseOffering;
    }
    
}
