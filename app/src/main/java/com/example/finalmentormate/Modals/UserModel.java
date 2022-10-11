package com.example.finalmentormate.Modals;

public class UserModel {
    String userid;
    String useremail;

    public UserModel(String userid, String useremail) {
        this.userid = userid;
        this.useremail = useremail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

}