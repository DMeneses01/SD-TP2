package com.example.demo;

import com.example.data.Goal;
import com.example.data.Resume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends CrudRepository<Resume, Integer>
{
    @Query("select r from Resume r where (r.match.id = ?1) order by r.time")
    Optional<List<Resume>> getMatchResumes(int idMatch);
}