package com.example.mitulmalani.emergencyservice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity {

    EditText e1, e2;
    Button btn;
    String email_address, passwd;
    FirebaseAuth mAuth;
    ImageView imageView;
    Intent intent1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();

        imageView = (ImageView) findViewById(R.id.menu);
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                email_address = e1.getText().toString();
                passwd = e2.getText().toString();
                boolean ans = validateData(email_address, passwd);

                if (!email_address.isEmpty() && !passwd.isEmpty()) {

                    final ProgressDialog progressDialog = new ProgressDialog(UserLoginActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Logging in");
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email_address, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Login Successfull...", 1).show();
                                finish();
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1 = getIntent();
                                String uid = intent1.getStringExtra("uid");
                                intent.putExtra("uid", uid);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), 1).show();
                            }
                        }
                    });
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(UserLoginActivity.this, imageView);
                popupMenu.getMenuInflater().inflate(R.menu.except_signout_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(UserLoginActivity.this, menuItem.getTitle(), 0).show();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    /*   @Override
       public void onBackPressed() {
           //super.onBackPressed();
       }
   */
    private boolean validateData(String email_address, String passwd) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email_address).matches()) {
            e1.setError("Please Enter Correct Email Address");
            e1.requestFocus();
            return false;
        }
        if (passwd.length() < 6) {
            e2.setError("Password length should not be less than 6");
            e2.requestFocus();
            return false;
        }
        return true;
    }
}
