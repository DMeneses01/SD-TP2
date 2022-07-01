package com.example.demo;

import com.example.data.Goal;
import com.example.data.Player;
import com.example.data.Resume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends CrudRepository<Goal, Integer>
{
    @Query("select count(n.id) from Goal n where (n.match.id = ?1 and n.player.t.id = ?2)")
    int getResult(int idMatch, int idTeam);

    @Query("select g from Goal g where (g.match.id = ?1) order by g.time")
    Optional<List<Goal>> getMatchGoals(int idMatch);

    @Query("select count(g.player.id) from Goal g group by g.player.id order by count(g.player.id) DESC")
    Optional<List<Integer>> getBestScorer();

    @Query("select g.player.id from Goal g group by g.player.id order by count(g.player.id) DESC")
    Optional<List<Integer>> getScorer(long num_goals);
}