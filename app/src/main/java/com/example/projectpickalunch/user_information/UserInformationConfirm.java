package com.example.projectpickalunch.user_information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;

import java.net.URI;

public class UserInformationConfirm extends AppCompatActivity {
    ImageButton userInfoReturnButton;

    ImageView confrimImage;
    Button gallery;
    Button camere;
    private static final int REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_confirm);

        confrimImage = findViewById(R.id.confirm_image);
        gallery = findViewById(R.id.confirm_gallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        userInfoReturnButton = (ImageButton) findViewById(R.id.userInfoReturnButton);
        userInfoReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    Uri uri = data.getData();
                    Glide.with(getApplicationContext()).load(uri).into(confrimImage);
                }catch (Exception e){

                }
            }else if(resultCode == RESULT_CANCELED){
            }
        }
    }
}
