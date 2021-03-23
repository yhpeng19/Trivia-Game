package com.project.Backend.Entities;

import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity to keep records of games played
 */
@Entity
@Table (name = "game")
public class Game {
    //@Column (name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //@Column (name = "score")
    //private int score;

    private ZonedDateTime date;
    private String status;

    @OneToMany (mappedBy = "game")
    private List<GamePlayers> gamesList;
    @OneToMany (mappedBy = "game")
    private List<GameQuestions> qList;

    public Game(){
        Instant nowUtc = Instant.now();
        ZoneId us = ZoneId.of("America/Chicago");
        ZonedDateTime nowUS = ZonedDateTime.ofInstant(nowUtc, us);
        this.date = nowUS;
        this.status = "open";
    };

    //get rid of question list later probably

    public Game(ZonedDateTime date, List<GamePlayers> gamesList, String status) {
        this.date = date;
        this.gamesList = gamesList;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public List<GamePlayers> getGamesList() {
        return gamesList;
    }

    public void setGamesList(List<GamePlayers> gamesList) {
        this.gamesList = gamesList;
    }

    public List<GameQuestions> getQuestionList(){return qList;}

    public void setqList(List<GameQuestions> qList){this.qList = qList;}
public String getIdString(){
        return ""+id;
}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GameQuestions> getqList() {
        return qList;
    }
}
