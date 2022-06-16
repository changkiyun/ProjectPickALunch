package com.example.projectpickalunch.user_information;

import static com.example.projectpickalunch.Main.MainActivity.confirmCheck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.loginpage.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInformationConfirm extends AppCompatActivity {
    ImageButton userInfoReturnButton;
    Button userConfirmButton;
    ImageView confrimImage;
    Button gallery;
    CheckBox confirmAgreeCheckbox;
    EditText userConfirmVerifyEdt;
    private FirebaseDatabase mDatabase;

    //Firebase
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_confirm);
        mDatabase = FirebaseDatabase.getInstance();
        confrimImage = findViewById(R.id.confirm_image);
        gallery = findViewById(R.id.confirm_gallery);
        userConfirmButton = findViewById(R.id.userConfirmButton);
        confirmAgreeCheckbox = findViewById(R.id.confirmAgreeCheckbox);
        userConfirmVerifyEdt = findViewById(R.id.userConfirmVerifyEdt);

        //이미지 선택 리스너
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."),0);
            }
        });

        //이미지 업로드 버튼
        userConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //인증동의 체크여부 확인
                if(userConfirmVerifyEdt.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(confirmAgreeCheckbox.isChecked()) {
                    addPermission();
                    uploadFile();
                }
                else{
                    Toast.makeText(getApplicationContext(),"인증 동의에 체크해주세요.", Toast.LENGTH_SHORT).show();
                }
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

        if(requestCode == 0  && resultCode == RESULT_OK){
            filePath = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                confrimImage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //파일 업로드 메소드
    private void uploadFile(){
        //업로드할 파일이 있으면 수행
        if(filePath != null){
            //ProgressDialog
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중");
            progressDialog.show();

            //Firebase Storage Upload
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //파일명 중복 방지를 위해 날짜 + 현재시간을 파일명으로 설정
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();

            //업로드한 사용자의 정보확인을 위해 파일명에 닉네임 입력
            String filename = formatter.format(now) + "    Nickname   :   " + userConfirmVerifyEdt.getText();
            //스토리지 경로설정
            StorageReference storageRef = storage.getReferenceFromUrl("gs://pickalunch-b0ea3.appspot.com/user_confirm_upload_image/ref_image_png").child("user_confirm_upload_image/" + filename);

            storageRef.putFile(filePath)
                    //업로드 성공 리스너
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            //여기에 데베에 닉네임과 권한 보내는 코드 uid받아와서 권한과 닉네임 데베에 저장해야함
                            confirmCheck =true;
                            // database에 저장
                            finish();
                        }
                    })
                    //업로드 실패 리스너
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //프로그래스다이어로그 리스너
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded" + ((int)progress)+"%...");
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPermission(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            String uid = user.getUid();
            String nickname = userConfirmVerifyEdt.getText().toString();
            UserModel userModel = new UserModel(uid,"0",nickname);
            mDatabase.getReference().child("users").child(uid).setValue(userModel);
        }

    }
}


