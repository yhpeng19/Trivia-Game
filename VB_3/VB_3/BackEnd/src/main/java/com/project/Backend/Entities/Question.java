package com.project.Backend.Entities;

//import jdk.jfr.Category;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.List;

/**
 * Entity to keep records of questions in the database
 */
@Entity
//@Table(name = "questions")
public class Question {
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
    private String category;        //Maybe change this to multiple categories later
    @OneToMany (mappedBy = "question")
    private List<GameQuestions> gameQuestions;

    public Question(){}

    public Question( String question, String correct_answer, String wrong_answer1,
                    String wrong_answer2, String wrong_answer3, String category) {
        //this.setId(id);
        this.setQuestion(question);
        this.setCorrectAnswer(correct_answer);
        this.setWrongAnswer1(wrong_answer1);
        this.setWrongAnswer2(wrong_answer2);
        this.setWrongAnswer3(wrong_answer3);
        this.setCategory(category);

    }

    //Getters and Setters
    //id
    public int getId() {return id;}
    public void setId(int id){this.id = id;}
    //Question
    public String getQuestion(){
        return question;
    }
    public void setQuestion(String question){
        this.question=question;
    }

    //Correct Answer
    public String getCorrectAnswer(){
        return correct_answer;
    }
    public void setCorrectAnswer(String correct_answer){
        this.correct_answer=correct_answer;
    }

    //Wrong Answer 1
    public String getWrongAnswer1(){
        return wrong_answer1;
    }
    public void setWrongAnswer1(String wrong_answer1){
        this.wrong_answer1=wrong_answer1;
    }

    //Wrong Answer 2
    public String getWrongAnswer2(){
        return wrong_answer2;
    }
    public void setWrongAnswer2(String wrong_answer2){
        this.wrong_answer2=wrong_answer2;
    }

    //Wrong Answer 3
    public String getWrongAnswer3(){
        return wrong_answer3;
    }
    public void setWrongAnswer3(String wrong_answer3){
        this.wrong_answer3=wrong_answer3;
    }

    //Category
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category=category;
    }

   /* public void addInGameQuestion(inGameQuestion q){
        this.inGameQ.add(q);
    }*/
    /*public Iterable<Question> printQuestionList() {}*/

}
