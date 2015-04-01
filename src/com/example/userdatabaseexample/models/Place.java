package com.example.userdatabaseexample.models;

import com.example.userdatabaseexample.mapper.Column;

public class Place {

    @Column("_id")
    private int id;
    private String description;
    private int score;

    public Place() {
    }

    public Place(String description, int score) {
        this.description = description;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                '}';
    }
}
