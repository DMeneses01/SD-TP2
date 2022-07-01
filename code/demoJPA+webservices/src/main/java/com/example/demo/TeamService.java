package com.example.demo;

import java.util.List;


import java.util.ArrayList;
import java.util.Optional;

import com.example.data.Match;
import com.example.data.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class TeamService {

    @Autowired
    private TeamsRepository teamsRepository;

    public List<Team> getAllTeams()
    {
        List<Team>teamRecords = new ArrayList<>();
        teamsRepository.findAll().forEach(teamRecords::add);
        System.out.println(teamRecords);
        return teamRecords;
    }

    public Optional<Team> getTeamById(int id)
    {
        return teamsRepository.findById(id);
    }

    public Optional<Team> getTeamByName(String name)
    {
        return Optional.ofNullable(teamsRepository.findByName(name).get(0));
    }

    public void addTeam(Team team)
    {
        teamsRepository.save(team);
    }

    public Optional<List<Team>> getTableMatches()  { return teamsRepository.getTableMatches(); }

    public Optional<List<Team>> getTableWins()  { return teamsRepository.getTableWins(); }

    public Optional<List<Team>> getTableDraws()  { return teamsRepository.getTableDraws(); }

    public Optional<List<Team>> getTableDefeats()  { return teamsRepository.getTableDefeats(); }

    public void deleteTeam(int id)
    {
        teamsRepository.deleteById(id);
    }
}
