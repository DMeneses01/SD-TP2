package com.example.data;

import com.example.demo.TeamService;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Match {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "team_a_id")
    private Team teamA;
    @ManyToOne
    @JoinColumn(name = "team_b_id")
    private Team teamB;
    private String location, last_event;
    private Timestamp date, start_time, end_time;
    private boolean start, end_, interrupted;
    @OneToMany(mappedBy="match")
    private List<YellowCard> yellows;
    @OneToMany(mappedBy="match")
    private List<RedCard> reds;
    @OneToMany(mappedBy="match")
    private List<Goal> goals;
    @OneToMany(mappedBy="match")
    private List<Resume> resumes;
    @OneToMany(mappedBy="match")
    private List<Interrupt> interruptions = new ArrayList<Interrupt>();

    public Match() {
    }

    public Match(Team teamA, Team teamB, String location, Timestamp date, boolean start, boolean end_, boolean interrupted, String last_event, Timestamp start_time, Timestamp end_time) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end_ = end_;
        this.interrupted = interrupted;
        this.yellows = new ArrayList<>();
        this.reds = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.resumes = new ArrayList<>();
        this.interruptions = new ArrayList<>();
        this.last_event = last_event;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeamA() { return teamA; }

    public void setTeamA(Team teamA) { this.teamA = teamA; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }

    public boolean isStart() { return start; }

    public void setStart(boolean start) { this.start = start; }

    public boolean isEnd_() { return end_; }

    public void setEnd_(boolean end_) { this.end_ = end_; }

    public boolean isInterrupted() { return interrupted; }

    public void setInterrupted(boolean interrupted) { this.interrupted = interrupted; }

    public Team getTeamB() { return teamB; }

    public void setTeamB(Team teamB) { this.teamB = teamB; }

    public List<YellowCard> getYellows() { return yellows; }

    public void setYellows(List<YellowCard> yellows) { this.yellows = yellows; }

    public void addYellow(YellowCard yellow) {
        this.yellows.add(yellow);
    }

    public List<RedCard> getReds() { return reds; }

    public void setReds(List<RedCard> reds) { this.reds = reds; }

    public void addRed(RedCard red) { this.reds.add(red); }

    public List<Goal> getGoals() { return goals; }

    public void setGoals(List<Goal> goals) { this.goals = goals; }

    public void addGoal(Goal goal) {
        this.goals.add(goal);
    }

    public List<Resume> getResumes() { return resumes; }

    public void setResumes(List<Resume> resumes) { this.resumes = resumes; }

    public void addResume(Resume resume) {
        this.resumes.add(resume);
    }

    public List<Interrupt> getInterruptions() { return interruptions; }

    public void setInterruptions(List<Interrupt> interruptions) { this.interruptions = interruptions; }

    public void addInterruption(Interrupt interruption) {
        this.interruptions.add(interruption);
    }

    public String getLast_event() { return last_event; }

    public void setLast_event(String last_event) { this.last_event = last_event; }

    public String toString() {
        return this.teamA + " vs " + this.teamB;
    }
}
