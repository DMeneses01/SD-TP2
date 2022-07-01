package com.example.demo;

import com.example.data.Player;
import com.example.data.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer>
{
    @Query("select p from Player p where p.t.id = ?1 or p.t.id = ?2")
    List<Player> findByTeamId(int idA, int idB);

}
