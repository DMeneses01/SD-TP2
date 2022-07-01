package com.example.data;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name, position;
    private Date birth;
    @ManyToOne
    private Team t;
    @OneToMany(mappedBy="player")
    private List<Goal> goals;
    @OneToMany(mappedBy="player")
    private List<YellowCard> yellows;
    @OneToMany(mappedBy="player")
    private List<RedCard> reds;

    public Player() {
    }

    public Player(String name, String position, Date birth, Team t) {
        this.name = name;
        this.position = position;
        this.birth = birth;
        this.t = t;
        this.goals = new ArrayList<>();
        this.yellows = new ArrayList<>();
        this.reds = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public Date getBirth() { return birth; }

    public void setBirth(Date birth) { this.birth = birth; }

    public Team getT() { return t; }

    public void setT(Team t) { this.t = t; }

    public List<Goal> getGoals() { return goals; }

    public void setGoals(List<Goal> goals) { this.goals = goals; }

    public void addGoal(Goal goal) { this.goals.add(goal); }

    public List<YellowCard> getYellows() { return yellows; }

    public void setYellows(List<YellowCard> yellows) { this.yellows = yellows; }

    public void addYellow(YellowCard yellow) { this.yellows.add(yellow); }

    public List<RedCard> getReds() { return reds; }

    public void setReds(List<RedCard> reds) { this.reds = reds; }

    public void addRed(RedCard red) { this.reds.add(red); }

    public String toString() {
        return this.name;
    }
}
