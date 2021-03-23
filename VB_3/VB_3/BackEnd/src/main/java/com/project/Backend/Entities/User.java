package com.project.Backend.Entities;

import javax.persistence.*;
import java.util.List;

/**
 * Entity to keep records of the app users
 */
@Entity
//@Table (name = "user")
public class User {
    //@Column (name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //@Column (name = "username")
    private String username;
    //@Column (name = "email")
    private String email;
    //@Column (name = "password")
    private String password;
    private String role;
    private int userScore;
   @OneToMany(mappedBy = "player")
    private List<GamePlayers> games;
   @OneToMany (mappedBy = "user")
   private List<LoginSession> loginSessions;
   @OneToMany (mappedBy = "user")
   private List<ProposedQuestion> proposedQuestions;
   @OneToMany
   @JoinColumn (name = "friends")
   private List<User> friends;
   @OneToMany (mappedBy = "sender")
   private List<FriendRequest> sent;
    @OneToMany (mappedBy = "receiver")
    private List<FriendRequest> received;


    public User(){}

    public User( String username, String email, String pass, List<User> friends, int userScore){
        //this.id = id;
        this.username = username;
        this.email = email;
        this.password = pass;
        this.role = "player";
        this.friends = friends;
this.userScore = userScore;
    }

    public User(String username, String email, String password, List<GamePlayers> games, List<User> friends, int userScore) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.games = games;
        this.role = "player";
        this.friends = friends;
        this.userScore = userScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GamePlayers> getGames() {
        return games;
    }

    public void setGames(List<GamePlayers> games) {
        this.games = games;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<LoginSession> getLoginSessions() {
        return loginSessions;
    }

    public void setLoginSessions(List<LoginSession> loginSessions) {
        this.loginSessions = loginSessions;
    }

    public List<ProposedQuestion> getProposedQuestions() {
        return proposedQuestions;
    }

    public void setProposedQuestions(List<ProposedQuestion> proposedQuestions) {
        this.proposedQuestions = proposedQuestions;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void addFriend(User u){
        this.friends.add(u);
    }

    public void deleteFriend(User u){
        if (this.friends.contains(u)) this.friends.remove(u);
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public List<FriendRequest> getSent() {
        return sent;
    }

    public void setSent(List<FriendRequest> sent) {
        this.sent = sent;
    }

    public List<FriendRequest> getReceived() {
        return received;
    }

    public void setReceived(List<FriendRequest> received) {
        this.received = received;
    }

}
