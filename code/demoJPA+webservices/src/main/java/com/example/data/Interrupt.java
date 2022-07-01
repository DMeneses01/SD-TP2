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
public class Interrupt extends Event {

    public Interrupt() {
    }

    public Interrupt(Timestamp time, Match match) {
        super(time, match);
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


    @Override
    public String toString() {
        return "INTERRUPTION - ".concat(String.valueOf(this.time));
    }
}
