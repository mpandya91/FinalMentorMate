package com.example.finalmentormate.Modals;

public class DoubtReplyModel {

    String id, uid, reply, username;

    public DoubtReplyModel(String id, String uid, String reply, String username) {
        this.id = id;
        this.uid = uid;
        this.reply = reply;
        this.username = username;
    }

    public DoubtReplyModel() {
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
