package com.example.demo;

import com.example.data.Goal;
import com.example.data.Player;
import com.example.data.RedCard;
import com.example.data.YellowCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RedService {

    @Autowired
    private RedRepository redRepository;

    public List<RedCard> getAllRed()
    {
        List<RedCard>redsRecords = new ArrayList<>();
        redRepository.findAll().forEach(redsRecords::add);
        System.out.println(redsRecords);
        return redsRecords;
    }

    public Optional<RedCard> getRedById(int id)
    {
        return redRepository.findById(id);
    }

    public Optional<List<RedCard>> getRedsByMatch(int id)
    {
        return redRepository.redPerMatch(id);
    }

    public void addRed(RedCard red)
    {
        redRepository.save(red);
    }

    public int getPlayerRedsPerMatch(int idPlayer, int idMatch)
    {
        return redRepository.redPlayerPerMatch(idPlayer, idMatch);
    }


}
