package com.example.projectpickalunch.user_information;

import static com.example.projectpickalunch.Main.MainActivity.confirmChecked;

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

        //????????? ?????? ?????????
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "???????????? ???????????????."),0);
            }
        });

        //????????? ????????? ??????
        userConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //???????????? ???????????? ??????
                if(userConfirmVerifyEdt.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(getApplicationContext(),"???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
                else if(confirmAgreeCheckbox.isChecked()) {
                    addPermission();
                    uploadFile();
                }
                else{
                    Toast.makeText(getApplicationContext(),"?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
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

    //?????? ????????? ?????????
    private void uploadFile(){
        //???????????? ????????? ????????? ??????
        if(filePath != null){
            //ProgressDialog
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("????????????");
            progressDialog.show();

            //Firebase Storage Upload
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //????????? ?????? ????????? ?????? ?????? + ??????????????? ??????????????? ??????
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();

            //???????????? ???????????? ??????????????? ?????? ???????????? ????????? ??????
            String filename = formatter.format(now) + "    Nickname   :   " + userConfirmVerifyEdt.getText();
            //???????????? ????????????
            StorageReference storageRef = storage.getReferenceFromUrl("gs://pickalunch-b0ea3.appspot.com/user_confirm_upload_image/ref_image_png").child("user_confirm_upload_image/" + filename);

            storageRef.putFile(filePath)
                    //????????? ?????? ?????????
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
                            confirmChecked ="1";
                            // database??? ??????
                            finish();
                        }
                    })
                    //????????? ?????? ?????????
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //?????????????????????????????? ?????????
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded" + ((int)progress)+"%...");
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPermission(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            String uid = user.getUid();
            String nickname = userConfirmVerifyEdt.getText().toString();
            UserModel userModel = new UserModel(uid,"1",nickname);
            mDatabase.getReference().child("users").child(uid).child("uid").setValue(userModel);
        }

    }
}


