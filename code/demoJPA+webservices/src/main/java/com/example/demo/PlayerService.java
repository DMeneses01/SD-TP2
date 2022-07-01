package com.example.demo;

import java.util.List;


import java.util.ArrayList;
import java.util.Optional;

import com.example.data.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.data.Player;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playersRepository;

    public List<Player> getAllPlayers()
    {
        List<Player>playersRecords = new ArrayList<>();
        playersRepository.findAll().forEach(playersRecords::add);
        return playersRecords;
    }

    public void addPlayer(Player player)
    {
        System.out.println("ENTREI");
        playersRepository.save(player);
        System.out.println("SAI");
    }

    public Optional<List<Player>> getPlayerfromMatchByTeamId(int idA, int idB)
    {
        return Optional.ofNullable(playersRepository.findByTeamId(idA, idB));
    }

    public Optional<Player> getPlayerById(int id)
    {
        return playersRepository.findById(id);
    }


}

