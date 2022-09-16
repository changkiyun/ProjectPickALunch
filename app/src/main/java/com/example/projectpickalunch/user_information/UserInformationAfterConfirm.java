package com.example.projectpickalunch.user_information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.ListItem;
import com.example.projectpickalunch.restorant_info.Review_Rate;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserInformationAfterConfirm extends AppCompatActivity {

    ImageButton userInfoReturnButton;
    TextView userName;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    ArrayList<NickName> nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_after_confirm);

        //사용자 닉네임 받아오기
        nameList = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        databaseReference = mDatabase.getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    NickName nickname = snapshot.getValue(NickName.class);
                    nameList.add(nickname);
//                    String[] name = new String[nameList.size()];
//                    name = nameList.toArray(name);
//                    String nickName = String.valueOf(name[0]);

                }
                for(int i=0; i<nameList.size(); i++){
                    if(nameList.get(i).getUid().equals(uid)){
                        String testname = nameList.get(i).getNickName();
                        userName = (TextView)findViewById(R.id.userName);
                        userName.setText(String.valueOf(nameList.get(i).getNickName()));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력

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

}

