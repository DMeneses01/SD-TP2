package com.example.demo;

import com.example.data.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Integer>
{
    @Query("select e from Event e where (e.match.id = ?1) order by e.id")
    Optional<List<Event>> getMatchEvents(int idMatch);
}