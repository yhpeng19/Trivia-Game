package com.project.Backend.Entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Entity to keep records of what questions were used in what game
 */
@Entity
public class GameQuestions {
    @Id
    //@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;
    @ManyToOne
    private Game game;
    @ManyToOne
    private Question question;

    public GameQuestions() {
    }

    public GameQuestions(Game game, Question question) {
        this.game = game;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
