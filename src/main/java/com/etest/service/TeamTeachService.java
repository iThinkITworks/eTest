/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.TeamTeach;
import com.etest.model.Users;
import java.util.List;

/**
 *
 * @author jetdario
 */
public interface TeamTeachService {
    
    public List<TeamTeach> getAllSemestralTeamTeach();
    
    public List<TeamTeach> getAllMembersFromTeam(int teamTeachId);
    
    public boolean insertNewTeamTeach(TeamTeach tt);
    
    public boolean updateTeamTeach(TeamTeach tt);
    
    public boolean removeTeamTeach(int teamTeachId);
    
    public int countTeamMembers(int teamTeachId);
    
    public List<Users> getAllFacultyExceptTeamLeader(int facultyId);
    
    public int getFacultyIdByTeamTeachId(int teamTeachId);
    
    public boolean addTeamMember(int teamTeachId, 
            int facultyId);
    
    public boolean removeTeamMember(int teamTeachId, 
            int facultyId);
    
    public boolean isFacultyTeamLeader(int teamTeachId, 
            int facultyId);
}
