package com.example.demo;

import com.example.data.Goal;
import com.example.data.YellowCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class YellowService {

    @Autowired
    private YellowRepository yellowRepository;

    public List<YellowCard> getAllYellow()
    {
        List<YellowCard>yellowsRecords = new ArrayList<>();
        yellowRepository.findAll().forEach(yellowsRecords::add);
        System.out.println(yellowsRecords);
        return yellowsRecords;
    }

    public Optional<YellowCard> getYellowById(int id)
    {
        return yellowRepository.findById(id);
    }

    public Optional<List<YellowCard>> getYellowsByMatchId(int id)
    {
        return yellowRepository.getMatchYellows(id);
    }

    public void addYellow(YellowCard yellow)
    {
        yellowRepository.save(yellow);
    }

    public int getPlayerYellowsPerMatch(int idPlayer, int idMatch)
    {
        return yellowRepository.yellowPlayerPerMatch(idPlayer, idMatch);
    }

}
