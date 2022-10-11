package com.example.projectpickalunch.user_information;


//Todo: 필요 없는 클래스 오류 확인 후 삭제 (파이어베이스에서 유저 닉네임 가져오는 클래스)
public class NickName {
    private String nickName;
    private String uid;

    public NickName(){}

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
