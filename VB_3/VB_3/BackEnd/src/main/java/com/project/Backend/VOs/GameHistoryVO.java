package com.project.Backend.VOs;

/**
 * Helper class to pass the game results to display on the frontEnd
 */
public class GameHistoryVO {
    int numPlayers;
    String currentUser;
    int currentUserScore;
    String player1;
    int score1;
    String player2;
    int score2;
    String player3;
    int score3;

    public GameHistoryVO() {
    }
    public GameHistoryVO(int numPlayers, String currentUser, int currentUserScore, String player1, int score1, String player2, int score2, String player3, int score3) {
        this.numPlayers = numPlayers;
        this.currentUser = currentUser;
        this.currentUserScore = currentUserScore;
        this.player1 = player1;
        this.score1 = score1;
        this.player2 = player2;
        this.score2 = score2;
        this.player3 = player3;
        this.score3 = score3;
    }
    public GameHistoryVO(String currentUser, int currentUserScore) {
        this.currentUser = currentUser;
        this.currentUserScore = currentUserScore;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public int getCurrentUserScore() {
        return currentUserScore;
    }

    public void setCurrentUserScore(int currentUserScore) {
        this.currentUserScore = currentUserScore;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public int getScore3() {
        return score3;
    }

    public void setScore3(int score3) {
        this.score3 = score3;
    }
}
