package com.example.projectpickalunch.loginpage;

public class UserModel {
    public String uid;
    public boolean confirmCheck;
    public String nickname;
    public UserModel(){}
    public String getUid() {
        return uid;
    }

    public boolean isConfirmCheck() {
        return confirmCheck;
    }

    public void setConfirmCheck(boolean confirmCheck) {
        this.confirmCheck = confirmCheck;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public UserModel(String uid, boolean confirmCheck, String nickname){
        this.uid = uid;
        this.confirmCheck = confirmCheck;
        this.nickname = nickname;
    }
}
