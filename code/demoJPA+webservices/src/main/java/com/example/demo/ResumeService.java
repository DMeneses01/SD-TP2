package com.example.demo;

import com.example.data.Goal;
import com.example.data.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public List<Resume> getAllInterrupts()
    {
        List<Resume>resumesRecords = new ArrayList<>();
        resumeRepository.findAll().forEach(resumesRecords::add);
        System.out.println(resumesRecords);
        return resumesRecords;
    }

    public Optional<Resume> getResumeById(int id)
    {
        return resumeRepository.findById(id);
    }

    public Optional<List<Resume>> getResumesByMatchId(int id)
    {
        return resumeRepository.getMatchResumes(id);
    }

    public void addResume(Resume resume)
    {
        resumeRepository.save(resume);
    }


}
