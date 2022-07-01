package com.example.demo;

import com.example.data.Match;
import com.example.data.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends CrudRepository<Match, Integer>
{
    @Query("select m from Match m where m.start = true and m.end_ = false")
    List<Match> findCurrentMatches();

    @Query("select m from Match m where m.end_ = false")
    List<Match> findCurrentAndFutureMatches();
}
