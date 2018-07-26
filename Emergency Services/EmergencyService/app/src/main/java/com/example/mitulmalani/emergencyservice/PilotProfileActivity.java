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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PilotProfileActivity extends AppCompatActivity {

    public static double lo, la;
    Button btn1;
    TextView latitude, longitude, displaynametv;
    GPSTracker gps;
    Intent intent;
    boolean doubleBackToExitPressedOnce = false;
    // FirebaseAuth mAuth;
    //Firebase mref;
    ImageView imageView;
    private String uid;
    public static int status = 0;
    private SharedPreferences sharedPreferences123;
    private SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilot_profile);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", null);


        sharedPreferences123 = getSharedPreferences("Mypref123", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences123.edit();
        editor.putString("status123", "true");
        editor.commit();


     /*   Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        mref = new Firebase("https://emergencypilot-eef66.firebaseio.com/");
*/
        btn1 = (Button) findViewById(R.id.btn1);
        imageView = (ImageView) findViewById(R.id.menu);
        latitude = (TextView) findViewById(R.id.latitudetv);
        longitude = (TextView) findViewById(R.id.longitudetv);
        displaynametv = (TextView) findViewById(R.id.displaynametv);


       /* mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
*/
    /*    FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (user.getDisplayName() != null) {
                displaynametv.setText(user.getDisplayName());
            }
        }
*/

        displaynametv.setText("Hello, " + uid);

        gps = new GPSTracker(PilotProfileActivity.this);
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

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PilotProfileActivity.this, MapsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(PilotProfileActivity.this, imageView);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.logout:
//                                FirebaseAuth.getInstance().signOut();
//                                Toast.makeText(getApplicationContext(), "Sign out Successfull...", 1).show();
//                                finish();
//                                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                                sharedPreferences = getSharedPreferences("Mypref12", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                editor1.clear();
                                editor1.commit();


                                sharedPreferences = getSharedPreferences("Mypref123", MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                                editor2.clear();
                                editor2.commit();

                                finish();
                                startActivity(new Intent(PilotProfileActivity.this, MainActivity.class));
                                break;
                        }
                        Toast.makeText(PilotProfileActivity.this, menuItem.getTitle(), 0).show();
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
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
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
