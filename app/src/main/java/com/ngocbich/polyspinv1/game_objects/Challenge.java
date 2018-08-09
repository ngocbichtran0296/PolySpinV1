package com.ngocbich.polyspinv1.game_objects;

/**
 * Created by Ngoc Bich on 7/24/2018.
 */

public class Challenge {
    private int id;
    private int name;
    private String description;
    private int requitment;
    private int pass;

    public Challenge(int id, int name, String description, int requitment, int pass) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requitment = requitment;
        this.pass = pass;
    }

    public Challenge(int name, String description, int requitment, int pass) {
        this.name = name;
        this.description = description;
        this.requitment = requitment;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRequitment() {
        return requitment;
    }

    public void setRequitment(int requitment) {
        this.requitment = requitment;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }
}
