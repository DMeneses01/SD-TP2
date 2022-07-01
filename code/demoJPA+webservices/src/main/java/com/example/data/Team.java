package com.example.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String image;
    private int n_wins, n_defeats, n_draws, n_matches;
    @OneToMany(mappedBy="t")
    List<Player> players;

    public Team() {
    }

    public Team(String name, String image, int n_wins, int n_defeats, int n_draws, int n_matches) {
        this.name = name;
        this.image = image;
        this.n_wins = n_wins;
        this.n_defeats = n_defeats;
        this.n_draws = n_draws;
        this.players = new ArrayList<>();
        this.n_matches = n_matches;
    }

    public int getN_matches() { return n_matches; }

    public void setN_matches(int n_matches) {
        this.n_matches = n_matches;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public int getN_wins() { return n_wins; }

    public void setN_wins(int n_wins) { this.n_wins = n_wins; }

    public int getN_defeats() { return n_defeats; }

    public void setN_defeats(int n_defeats) { this.n_defeats = n_defeats; }

    public int getN_draws() { return n_draws; }

    public void setN_draws(int n_draws) { this.n_draws = n_draws; }

    public List<Player> getPlayers() { return players; }

    public void setPlayers(List<Player> players) { this.players = players; }

    public void addPlayer(Player player) { this.players.add(player); }

    public String toString() {
        return this.name;
    }
}
