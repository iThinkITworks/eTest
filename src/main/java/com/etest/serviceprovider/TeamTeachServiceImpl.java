/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.TeamTeachDAO;
import com.etest.model.TeamTeach;
import com.etest.model.Users;
import com.etest.service.TeamTeachService;
import java.util.List;

/**
 *
 * @author jetdario
 */
public class TeamTeachServiceImpl implements TeamTeachService {

    @Override
    public List<TeamTeach> getAllSemestralTeamTeach() {
        return TeamTeachDAO.getAllSemestralTeamTeach();
    }

    @Override
    public List<TeamTeach> getAllMembersFromTeam(int teamTeachId) {
        return TeamTeachDAO.getAllMembersFromTeam(teamTeachId);
    }

    @Override
    public int getTeamTeachIdByUserId(int userId){
        return TeamTeachDAO.getTeamTeachIdByUserId(userId);
    }
    
    @Override
    public boolean insertNewTeamTeach(TeamTeach tt) {
        return TeamTeachDAO.insertNewTeamTeachLeader(tt);
    }

    @Override
    public boolean updateTeamTeach(int teamTeachId, int userId) {
        return TeamTeachDAO.updateTeamTeachLeader(teamTeachId, userId);
    }

    @Override
    public boolean removeSemestralTeam(int teamTeachId) {
        return TeamTeachDAO.removeSemestralTeam(teamTeachId);
    }

    @Override
    public int countTeamMembers(int teamTeachId) {
        return TeamTeachDAO.countTeamMembers(teamTeachId);
    }

    @Override
    public List<Users> getAllFacultyExceptTeamLeader(int facultyId) {
        return TeamTeachDAO.getAllFacultyExceptTeamLeader(facultyId);
    }

    @Override
    public int getFacultyIdByTeamTeachId(int teamTeachId) {
        return TeamTeachDAO.getFacultyIdByTeamTeachId(teamTeachId);
    }

    @Override
    public boolean addTeamMember(int teamTeachId, 
            int facultyId) {
        return TeamTeachDAO.addTeamMember(teamTeachId, 
                facultyId);
    }

    @Override
    public boolean removeTeamMember(int teamTeachId, 
            int facultyId) {
        return TeamTeachDAO.removeTeamMember(teamTeachId, 
                facultyId);
    }

    @Override
    public boolean isFacultyTeamLeader(int teamTeachId, 
            int facultyId) {
        return TeamTeachDAO.isFacultyTeamLeader(teamTeachId, 
                facultyId);
    }

    @Override
    public boolean isTeamMemberAlreadyExist(int teamTeachId, 
            int facultyId) {
        return TeamTeachDAO.isTeamMemberAlreadyExist(teamTeachId, 
                facultyId);
    }    
}
