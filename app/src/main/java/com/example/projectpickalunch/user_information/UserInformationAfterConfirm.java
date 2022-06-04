package com.example.projectpickalunch.user_information;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;

public class UserInformationAfterConfirm extends AppCompatActivity {

    ImageButton userInfoReturnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_after_confirm);

        userInfoReturnButton = (ImageButton) findViewById(R.id.userInfoReturnButton);
        userInfoReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
