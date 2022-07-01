package com.example.demo;

import com.example.data.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeamsRepository extends CrudRepository<Team, Integer>
{
    @Query("select t from Team t where t.name like ?1")
    List<Team> findByName(String name);

    @Query("select t from Team t order by t.n_matches desc")
    Optional<List<Team>> getTableMatches();

    @Query("select t from Team t order by t.n_wins desc")
    Optional<List<Team>> getTableWins();

    @Query("select t from Team t order by t.n_draws desc")
    Optional<List<Team>> getTableDraws();

    @Query("select t from Team t order by t.n_defeats desc")
    Optional<List<Team>> getTableDefeats();

}