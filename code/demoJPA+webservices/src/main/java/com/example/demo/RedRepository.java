package com.example.demo;

import com.example.data.Player;
import com.example.data.RedCard;
import com.example.data.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RedRepository extends CrudRepository<RedCard, Integer>
{
    @Query("select count(n.id) from RedCard n where (n.player.id = ?1 and n.match.id = ?2)")
    int redPlayerPerMatch(int idPlayer, int idMatch);

    @Query("select n.id from RedCard n where n.match.id = ?1")
    List<Integer> redIdPerMatch(int idMatch);

    @Query("select r from RedCard r where r.match.id = ?1 order by r.time")
    Optional<List<RedCard>> redPerMatch(int idMatch);
}