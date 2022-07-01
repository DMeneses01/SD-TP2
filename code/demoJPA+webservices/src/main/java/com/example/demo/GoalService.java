package com.example.demo;

import com.example.data.Goal;
import com.example.data.Player;
import com.example.data.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getAllGoal()
    {
        List<Goal>goalsRecords = new ArrayList<>();
        goalRepository.findAll().forEach(goalsRecords::add);
        System.out.println(goalsRecords);
        return goalsRecords;
    }

    public Optional<Goal> getGoalById(int id)
    {
        return goalRepository.findById(id);
    }

    public Optional<List<Goal>> getGoalsByMatchId(int id)
    {
        return goalRepository.getMatchGoals(id);
    }

    public Optional<List<Integer>> getBestScorer()
    {
        return goalRepository.getBestScorer();
    }

    public Optional<List<Integer>> getScorer(long nGoals)
    {
        return goalRepository.getScorer(nGoals);
    }

    public void addGoal(Goal goal)
    {
        goalRepository.save(goal);
    }

    public String findResult(int idM, int idA, int idB)
    {
        int goalsA = goalRepository.getResult(idM, idA);
        int goalsB = goalRepository.getResult(idM, idB);

        String res = String.valueOf(goalsA).concat(" - ").concat(String.valueOf(goalsB));

        return res;
    }

    public int getNGoalsFromTeam(int idM, int idT)
    {
        int goalsT = goalRepository.getResult(idM, idT);

        return goalsT;
    }

}
