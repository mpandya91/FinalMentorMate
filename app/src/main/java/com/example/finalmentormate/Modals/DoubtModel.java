package com.example.finalmentormate.Modals;

public class DoubtModel {

    String id, uid, question, username;

    public DoubtModel() {
    }

    public DoubtModel(String id, String uid, String question, String username) {
        this.id = id;
        this.uid = uid;
        this.question = question;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
