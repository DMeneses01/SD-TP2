package com.example.demo;

import com.example.data.Goal;
import com.example.data.YellowCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface YellowRepository extends CrudRepository<YellowCard, Integer>
{
    @Query("select count(n.id) from YellowCard n where (n.player.id = ?1 and n.match.id = ?2)")
    int yellowPlayerPerMatch(int idPlayer, int idMatch);

    @Query("select y from YellowCard y where (y.match.id = ?1) order by y.time")
    Optional<List<YellowCard>> getMatchYellows(int idMatch);
}

