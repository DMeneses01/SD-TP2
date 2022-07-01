package com.example.demo;

import com.example.data.Goal;
import com.example.data.Interrupt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class InterruptService {

    @Autowired
    private InterruptRepository interruptRepository;

    public List<Interrupt> getAllInterrupts()
    {
        List<Interrupt>interruptsRecords = new ArrayList<>();
        interruptRepository.findAll().forEach(interruptsRecords::add);
        System.out.println(interruptsRecords);
        return interruptsRecords;
    }

    public Optional<Interrupt> getInterruptById(int id)
    {
        return interruptRepository.findById(id);
    }

    public Optional<List<Interrupt>> getInterruptsByMatchId(int id)
    {
        return interruptRepository.getMatchInterrupts(id);
    }

    public void addInterrupt(Interrupt interrupt)
    {
        interruptRepository.save(interrupt);
    }


}
