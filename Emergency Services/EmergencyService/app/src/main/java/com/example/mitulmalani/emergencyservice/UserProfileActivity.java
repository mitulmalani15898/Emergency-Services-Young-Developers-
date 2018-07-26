package com.example.mitulmalani.emergencyservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    Firebase mref;
    //FirebaseAuth mAuth;
    public static double lo, la;
    TextView latitude, longitude, displaynametv;
    GPSTracker gps;
    Intent intent, intent1;
    Button btn1, btn2, btn3;
    boolean doubleBackToExitPressedOnce = false;
    ImageView imageView;
    private SharedPreferences sharedPreferences;
    private String uid;
    public static int status = 0;
    private SharedPreferences sharedPreferences12;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", null);

        sharedPreferences12 = getSharedPreferences("Mypref12", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences12.edit();
        editor.putString("status12", "true");
        editor.commit();


        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        Firebase.setAndroidContext(this);
        // mAuth = FirebaseAuth.getInstance();
        mref = new Firebase("https://emergencyservice-5b9e8.firebaseio.com/user");
        imageView = (ImageView) findViewById(R.id.menu);
        latitude = (TextView) findViewById(R.id.latitudetv);
        longitude = (TextView) findViewById(R.id.longitudetv);
        displaynametv = (TextView) findViewById(R.id.displaynametv);

//        intent1 = getIntent();
//        final String uid = intent1.getStringExtra("uid");

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        gps = new GPSTracker(UserProfileActivity.this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            la = gps.getLatitude();
            lo = gps.getLongitude();
            // \n is for new line
            // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            longitude.setText("Longitude : " + lo);
            latitude.setText("Latitude : " + la);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        displaynametv.setText("Hello, "+uid);
       /* final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            displaynametv.setText("Hello, " + user.getEmail());
            //  displaynametv.setText(user.getDisplayName());
        }*/

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mainchildref = mref.child(uid);

                Firebase childreflo = mainchildref.child("Longitude");
                Firebase childrefla = mainchildref.child("Latitude");
                Firebase childrefservice = mainchildref.child("Service");
                Firebase childstatus = mainchildref.child("Status");

                childrefla.setValue(MainActivity.la);
                childreflo.setValue(MainActivity.lo);
                childrefservice.setValue("Fire Brigade");
                childstatus.setValue("Yes");

                Toast.makeText(getApplicationContext(), "Your Request is being Proceeds for Fire Brigade", Toast.LENGTH_SHORT).show();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mainchildref = mref.child(uid);

                Firebase childreflo = mainchildref.child("Longitude");
                Firebase childrefla = mainchildref.child("Latitude");
                Firebase childrefservice = mainchildref.child("Service");
                Firebase childstatus = mainchildref.child("Status");


                childrefla.setValue(MainActivity.la);
                childreflo.setValue(MainActivity.lo);
                childrefservice.setValue("108 Service");
                childstatus.setValue("Yes");


                Toast.makeText(getApplicationContext(), "Your Request is being Proceeds for 108 Service", Toast.LENGTH_SHORT).show();

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mainchildref = mref.child(uid);

                Firebase childreflo = mainchildref.child("Longitude");
                Firebase childrefla = mainchildref.child("Latitude");
                Firebase childrefservice = mainchildref.child("Service");
                Firebase childstatus = mainchildref.child("Status");


                childrefla.setValue(MainActivity.la);
                childreflo.setValue(MainActivity.lo);
                childrefservice.setValue("Both Service");
                childstatus.setValue("Yes");


                Toast.makeText(getApplicationContext(), "Your Request is being Proceeds for Fire Brigade and 108 Service", Toast.LENGTH_SHORT).show();

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(UserProfileActivity.this, imageView);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.logout:

                                sharedPreferences = getSharedPreferences("Mypref12", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                editor1.clear();
                                editor1.commit();


                                sharedPreferences = getSharedPreferences("Mypref123", MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                                editor1.clear();
                                editor2.commit();

                                finish();
                                startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
//                                FirebaseAuth.getInstance().signOut();
                                // Toast.makeText(getApplicationContext(), "Sign out Successfull...", 1).show();
//                                finish();
//                                startActivity(new Intent(UserProfileActivity.this, MainActivity.class));

                                break;
                        }
                        Toast.makeText(UserProfileActivity.this, menuItem.getTitle(), 0).show();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
     /*   if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);*/
    }


/*    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }*/
}
