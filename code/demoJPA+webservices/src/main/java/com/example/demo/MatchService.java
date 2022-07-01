package com.example.demo;

import com.example.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> getAllMatches()
    {
        List<Match>matchesRecords = new ArrayList<>();
        matchRepository.findAll().forEach(matchesRecords::add);
        return matchesRecords;
    }

    public Optional<Match> getMatchById(int id)
    {
        return matchRepository.findById(id);
    }

    public void addMatch(Match match)
    {
        matchRepository.save(match);
    }

    @Transactional
    public void changeStart(int id, Timestamp time) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (!m.get().isStart()) {
                m.get().setStart_time(time);
                m.get().setStart(true);
            }
        }
    }

    @Transactional
    public void changeEnd(int id, int GoalsA, int GoalsB, Timestamp time) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) {
                m.get().setEnd_time(time);
                m.get().setEnd_(true);
            }
        }

    }

    @Transactional
    public void changeInterrupt(int id, boolean value) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) m.get().setInterrupted(value);
        }
    }

    @Transactional
    public void addInterrupt(int id, Interrupt interrupt) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) m.get().addInterruption(interrupt);
        }
    }

    @Transactional
    public void addResume(int id, Resume resume) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) m.get().addResume(resume);
        }
    }

    @Transactional
    public void changeLastEvent(int id, String last_event) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) m.get().setLast_event(last_event);
        }
    }

    @Transactional
    public void addGoal(int id, Goal goal) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) m.get().addGoal(goal);
        }
    }

    @Transactional
    public void addYellow(int id, YellowCard yellow) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) m.get().addYellow(yellow);
        }
    }

    @Transactional
    public void addRed(int id, RedCard red) {
        Optional<Match> m = matchRepository.findById(id);

        if(m.isPresent()) {
            if (m.get().isStart()) m.get().addRed(red);
        }
    }

    public List<Match> findCurrentMatches()
    {
        return matchRepository.findCurrentMatches();
    }

    public List<Match> findCurrentAndFutureMatches()
    {
        return matchRepository.findCurrentAndFutureMatches();
    }

    public void deleteMatch(int id)
    {
        matchRepository.deleteById(id);
    }

}

