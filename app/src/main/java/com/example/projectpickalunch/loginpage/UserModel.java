package com.example.projectpickalunch.loginpage;

public class UserModel {
    public String uid;
    public String confirmCheck;
    public String nickname;

    UserModel(){}
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getConfirmCheck() {
        return confirmCheck;
    }

    public void setConfirmCheck(String confirmCheck) {
        this.confirmCheck = confirmCheck;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserModel(String uid, String confirmCheck, String nickname){
        this.uid = uid;
        this.confirmCheck = confirmCheck;
        this.nickname = nickname;
    }
}
