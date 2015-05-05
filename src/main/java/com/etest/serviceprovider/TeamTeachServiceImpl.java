/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.TeamTeachDAO;
import com.etest.model.TeamTeach;
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
    public List<String> getAllMembersFromTeam(int teamTeachId) {
        return TeamTeachDAO.getAllMembersFromTeam(teamTeachId);
    }

    @Override
    public boolean insertNewTeamTeach(TeamTeach tt) {
        return TeamTeachDAO.insertNewTeamTeachLeader(tt);
    }

    @Override
    public boolean updateTeamTeach(TeamTeach tt) {
        return TeamTeachDAO.updateTeamTeachLeader(tt);
    }

    @Override
    public boolean removeTeamTeach(int teamTeachId) {
        return TeamTeachDAO.removeTeamTeachLeader(teamTeachId);
    }
    
}
