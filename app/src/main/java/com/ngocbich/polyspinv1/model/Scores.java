package com.ngocbich.polyspinv1.model;

/**
 * Created by Ngoc Bich on 7/17/2018.
 */

public class Scores {
    private String Id;
    private String Score;
    private String IdAccount;

    public Scores(String id, String score, String idAccount) {
        Id = id;
        Score = score;
        IdAccount = idAccount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getIdAccount() {
        return IdAccount;
    }

    public void setIdAccount(String idAccount) {
        IdAccount = idAccount;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "Id=" + Id +
                ", Score=" + Score +
                ", IdAccount=" + IdAccount +
                '}';
    }
}
