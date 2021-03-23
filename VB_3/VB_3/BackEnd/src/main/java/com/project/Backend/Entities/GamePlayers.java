package com.project.Backend.Entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Entity to keep records of who played what game and the score in that game
 */
@Entity
@Table (name = "gamePlayers")
public class GamePlayers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;
    @Column (name = "totalScore")
    private int totalScore;
    @ManyToOne
    private User player;
    @ManyToOne
    private Game game;

    public GamePlayers() {
    }

    public GamePlayers(int totalScore, User player, Game game) {
        this.totalScore = totalScore;
        this.player = player;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
