package com.example.projectpickalunch.login_api;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class kakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "66003ba03f17d26728f7de955b64634a");

    }
}
