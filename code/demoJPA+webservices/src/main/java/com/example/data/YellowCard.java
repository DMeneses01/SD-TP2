package com.example.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class YellowCard extends Event {
    @ManyToOne
    private Player player;

    public YellowCard() {
    }

    public YellowCard(Timestamp time, Match match, Player player) {
        super(time, match);
        this.player = player;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() { return time; }

    public void setTime(Timestamp time) { this.time = time; }

    public Match getMatch() { return match; }

    public void setMatch(Match match) { this.match = match; }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }

    @Override
    public String toString() {
        return "YELLOW CARD - ".concat(this.player.getName()).concat(" [").concat(this.player.getT().getName()).concat("] - ").concat(String.valueOf(this.time));
    }
}
