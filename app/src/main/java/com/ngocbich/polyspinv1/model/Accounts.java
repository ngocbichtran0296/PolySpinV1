package com.ngocbich.polyspinv1.model;

/**
 * Created by Ngoc Bich on 7/1/2018.
 */

public class Accounts {
    private String AccountId;
    private String Name;
    private String Password;

    public Accounts(String accountName, String accountPassword) {
        Name = accountName;
        Password = accountPassword;
    }

    public Accounts() {
    }

    public Accounts(String accountId, String accountName, String accountPassword) {
        AccountId = accountId;
        Name = accountName;
        Password = accountPassword;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "AccountId=" + AccountId +
                ", AccountName='" + Name + '\'' +
                ", AccountPassword='" + Password + '\'' +
                '}';
    }
}
