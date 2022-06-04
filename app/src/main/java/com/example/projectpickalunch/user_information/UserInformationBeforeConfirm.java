package com.example.projectpickalunch.user_information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;

public class UserInformationBeforeConfirm extends AppCompatActivity {
    ImageButton userInfoReturnButton;
    Button userConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_before_confirm);

        //정보인증 화면
        userConfirmButton = (Button) findViewById(R.id.userConfirmButton);
        userConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_information_confirm = new Intent(getApplicationContext(), UserInformationConfirm.class);
                startActivity(user_information_confirm);
            }
        });

        //메인화면으로 돌아가기
        userInfoReturnButton = (ImageButton) findViewById(R.id.userInfoReturnButton);
        userInfoReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
