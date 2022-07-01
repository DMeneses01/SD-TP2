package com.example.demo;

import com.example.data.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllGoal()
    {
        List<Event>eventsRecords = new ArrayList<>();
        eventRepository.findAll().forEach(eventsRecords::add);
        System.out.println(eventsRecords);
        return eventsRecords;
    }

    public Optional<Event> getEventById(int id)
    {
        return eventRepository.findById(id);
    }

    public Optional<List<Event>> getEventsByMatchId(int id)
    {
        return eventRepository.getMatchEvents(id);
    }

}
