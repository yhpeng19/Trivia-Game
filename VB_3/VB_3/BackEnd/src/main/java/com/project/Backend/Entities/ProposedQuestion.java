package com.project.Backend.Entities;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Entity to keep record of the questions proposed by players
 */
@Entity
public class ProposedQuestion {
    @Id
    //@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;
    //@Column (name = "question")
    @NotFound(action = NotFoundAction.IGNORE)
    private String question;
    //@Column (name = "correct_answer")
    @NotFound(action = NotFoundAction.IGNORE)
    private String correct_answer;
    // @Column (name = "wrong_answer1")
    @NotFound(action = NotFoundAction.IGNORE)
    private String wrong_answer1;
    //@Column (name = "wrong_answer2")
    @NotFound(action = NotFoundAction.IGNORE)
    private String wrong_answer2;
    //@Column (name = "wrong_answer3")
    @NotFound(action = NotFoundAction.IGNORE)
    private String wrong_answer3;
    //@Column (name = "category")
    @NotFound(action = NotFoundAction.IGNORE)
    private String category;
    @NotFound(action = NotFoundAction.IGNORE)
    private ZonedDateTime dateProposed;
    @ManyToOne
    private User user;

    public ProposedQuestion() {
    }
    public ProposedQuestion(String question, String correct_answer, String wrong_answer1, String wrong_answer2, String wrong_answer3, String category, User user) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.wrong_answer1 = wrong_answer1;
        this.wrong_answer2 = wrong_answer2;
        this.wrong_answer3 = wrong_answer3;
        this.category = category;
        this.user = user;
        Instant nowUtc = Instant.now();
        ZoneId us = ZoneId.of("America/Chicago");
        ZonedDateTime nowUS = ZonedDateTime.ofInstant(nowUtc, us);
        this.dateProposed = nowUS;
    }
    public ProposedQuestion(int id, String question, String correct_answer, String wrong_answer1, String wrong_answer2, String wrong_answer3, String category, User user) {
        this.id = id;
        this.question = question;
        this.correct_answer = correct_answer;
        this.wrong_answer1 = wrong_answer1;
        this.wrong_answer2 = wrong_answer2;
        this.wrong_answer3 = wrong_answer3;
        this.category = category;
        this.user = user;
        Instant nowUtc = Instant.now();
        ZoneId us = ZoneId.of("America/Chicago");
        ZonedDateTime nowUS = ZonedDateTime.ofInstant(nowUtc, us);
        this.dateProposed = nowUS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getWrong_answer1() {
        return wrong_answer1;
    }

    public void setWrong_answer1(String wrong_answer1) {
        this.wrong_answer1 = wrong_answer1;
    }

    public String getWrong_answer2() {
        return wrong_answer2;
    }

    public void setWrong_answer2(String wrong_answer2) {
        this.wrong_answer2 = wrong_answer2;
    }

    public String getWrong_answer3() {
        return wrong_answer3;
    }

    public void setWrong_answer3(String wrong_answer3) {
        this.wrong_answer3 = wrong_answer3;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZonedDateTime getDateProposed() {
        return dateProposed;
    }

    public void setDateProposed(ZonedDateTime dateProposed) {
        this.dateProposed = dateProposed;
    }
}
