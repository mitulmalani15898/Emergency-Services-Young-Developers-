package com.example.mitulmalani.emergencyservice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity {

    Firebase mref;
    FirebaseAuth mAuth;
    EditText e1, e2, e3, e4, e5;
    Button btn;
    RadioGroup rg;
    RadioButton r;
    String name;
    String uid;
    String email_address;
    String mobile_no;
    String gender;
    String aadhar_number;
    ImageView imageView;
    String passwd;

    /* String id = "1";
     String dob = "15/08/1998";
     public String TABLE_NAME = "user";
     public static final String db_url = "jdbc:mysql://localhost:3306/emergencyservices";
     public static final String user = "admin";
     public static final String password = "admin";
 */
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firebase.setAndroidContext(this);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        mAuth = FirebaseAuth.getInstance();

        mref = new Firebase("https://emergencyservice-5b9e8.firebaseio.com/user");

        imageView = (ImageView) findViewById(R.id.menu);
        btn = (Button) findViewById(R.id.btn);
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        rg = (RadioGroup) findViewById(R.id.rg);
        e3 = (EditText) findViewById(R.id.e3);
        e4 = (EditText) findViewById(R.id.e4);
        e5 = (EditText) findViewById(R.id.e5);

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ShowToast", "WrongConstant"})
            @Override
            public void onClick(View view) {
                name = e1.getText().toString();
                email_address = e2.getText().toString();
                mobile_no = e3.getText().toString();
                int id = rg.getCheckedRadioButtonId();
                if (id != -1) {
                    r = (RadioButton) findViewById(id);
                    gender = r.getText().toString();
                }
                aadhar_number = e4.getText().toString();
                passwd = e5.getText().toString();

                boolean ans=validateData(name, email_address, mobile_no, id, aadhar_number, passwd);
                //  Submit submit = new Submit();
                // submit.execute(name, email_address, mobile_no, gender, aadhar_number, passwd);
                if (ans && !name.isEmpty() && !email_address.isEmpty() && !mobile_no.isEmpty() && id != -1 && !aadhar_number.isEmpty() && !passwd.isEmpty()) {

                    final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Registering");
                    progressDialog.show();
                    uid = name + " " + aadhar_number;

                    sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("uid", uid);
                    editor.commit();

                    Firebase mainchildref = mref.child(name + " " + aadhar_number);

                    Firebase childrefname = mainchildref.child("Name");
                    Firebase childrefmono = mainchildref.child("Mobile No");
                    Firebase childrefemailid = mainchildref.child("Email Id");
                    Firebase childrefpassword = mainchildref.child("Password");
                    Firebase childrefaadharno = mainchildref.child("Aadhar No");
                    Firebase childrefgender = mainchildref.child("Gender");
                    Firebase childstatus = mainchildref.child("Status");

                    childrefname.setValue(name);
                    childrefgender.setValue(gender);
                    childrefemailid.setValue(email_address);
                    childrefpassword.setValue(passwd);
                    childrefmono.setValue(mobile_no);
                    childrefaadharno.setValue(aadhar_number);
                    childstatus.setValue("No");

                    //   Toast.makeText(getApplicationContext(), "Successful...", 1).show();
                    //  Toast.makeText(getApplicationContext(), "Name : " + name + "\nMobile No : " + mobile_no + "\nEmail id : " + email_address + "\nPassword : " + passwd + "\nAadhar  No: " + aadhar_number + "\nGender : " + gender, Toast.LENGTH_LONG).show();

                   mAuth.createUserWithEmailAndPassword(email_address, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Registered Successfully...", 1).show();
                                finish();
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("uid", uid);
                                startActivity(intent);
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getApplicationContext(), "Email is already Registered...", 1).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), 1).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(RegisterActivity.this, imageView);
                popupMenu.getMenuInflater().inflate(R.menu.except_signout_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(RegisterActivity.this, menuItem.getTitle(), 0).show();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

  /*  @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }*/

    @SuppressLint("WrongConstant")
    private boolean validateData(String name, String email_address, String mobile_no, int id, String aadhar_number, String passwd) {
        if (name.isEmpty()) {
            e1.setError("Please Enter Name");
            e1.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_address).matches()) {
            e2.setError("Please Enter Correct Email Address");
            e2.requestFocus();
            return false;
        }
        if (mobile_no.isEmpty()) {
            e3.setError("Please Enter Mobile No.");
            e3.requestFocus();
            return false;
        }
        if (id == -1) {
            Toast.makeText(getApplicationContext(), "Please Select any Radio Button", 0).show();
            return false;
        }
        if (aadhar_number.isEmpty()) {
            e4.setError("Please Enter Aadhar No.");
            e4.requestFocus();
            return false;
        }
        if (passwd.length() < 6) {
            e5.setError("Password length should not be less than 6");
            e5.requestFocus();
            return false;
        }
        return true;
    }

    /*private class Submit {

        void execute(String name,String email_address,String mobile_no,String gender,String aadhar_number,String passwd){

            String msg="";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(db_url, user, password);
                if (con == null) {
                    msg="Connection goes wrong";
                    Toast.makeText(getApplicationContext(), "Connection goes wrong", Toast.LENGTH_LONG).show();
                }
                else {
                    String query = "INSERT INTO " + TABLE_NAME + " (user_id,full_name,email_id,mobile_no,gender,aadhar_no,dob,password) VALUES(`" + id + "`,`" + name + "`,`" + email_address + "`,`" + mobile_no + "`,`" + gender + "`,`" + aadhar_number + "`,`" + dob + "`,`" + passwd + "`)";
                    Statement stmt = con.createStatement();
                    ResultSet rs=stmt.executeQuery(query);
                    msg="Successfully...";
                    Toast.makeText(getApplicationContext(),"Successfully...", Toast.LENGTH_LONG).show();

                }
                //Toast.makeText(getApplicationContext(),"Successfully...", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                msg="Connection goes wrong";
                Toast.makeText(getApplicationContext(), "From Catch -->Connection goes wrong", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }



        }
    }*/
}
