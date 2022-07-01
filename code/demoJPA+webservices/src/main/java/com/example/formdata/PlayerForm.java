package com.example.formdata;

import com.example.data.Team;

public class PlayerForm {
    private int id;
    private Team t;
    private String birth;
    private String position;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getT() {
        return t;
    }

    public void setT(Team t) {
        this.t = t;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
