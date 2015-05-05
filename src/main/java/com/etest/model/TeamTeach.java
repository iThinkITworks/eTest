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
public class TeamTeach extends Curriculum {
    
    private int teamTeachId;
    private String schoolYear;   
    private String teamLeader;

    public int getTeamTeachId() {
        return teamTeachId;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamTeachId(int teamTeachId) {
        this.teamTeachId = teamTeachId;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }
        
}
