package com.example.demo;

import com.example.data.Goal;
import com.example.data.Interrupt;
import com.example.data.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InterruptRepository extends CrudRepository<Interrupt, Integer>
{
    @Query("select i from Interrupt i where (i.match.id = ?1) order by i.time")
    Optional<List<Interrupt>> getMatchInterrupts(int idMatch);
}