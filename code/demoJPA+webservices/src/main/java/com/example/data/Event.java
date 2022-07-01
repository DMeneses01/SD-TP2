package com.example.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Entity
@XmlRootElement
@Inheritance(strategy=InheritanceType.JOINED)
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected Timestamp time;

    @ManyToOne
    protected Match match;

    public Event() {
    }

    public Event(Timestamp time, Match match) {
        this.time = time;
        this.match = match;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() { return time; }

    public void setTime(Timestamp time) { this.time = time; }

    public String toString() {
        return String.valueOf(this.time);
    }
}
