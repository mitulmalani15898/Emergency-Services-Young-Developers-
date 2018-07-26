package com.example.mitulmalani.emergencyservice;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Date;


public class CallReceiver extends BroadcastReceiver {

    Firebase mref;
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.w("intent " , intent.getAction().toString());

        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            onCallStateChanged(context, state, number);
        }
    }


    @SuppressLint("WrongConstant")
    public void onCallStateChanged(Context context, int state, String number) {
        if (lastState == state) {
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;

                Toast.makeText(context, "Incoming Call Ringing", Toast.LENGTH_SHORT).show();
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    callStartTime = new Date();
                    Toast.makeText(context, "Outgoing Call Started", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,MainActivity.mobile_no,0).show();

                    Firebase.setAndroidContext(context);
                    mref = new Firebase("https://emergencyservice-5b9e8.firebaseio.com/mobile_service");

                    Firebase mainchildref = mref.child(MainActivity.mobile_no);

                    Firebase childreflo = mainchildref.child("Longitude");
                    Firebase childrefla = mainchildref.child("Latitude");
                    Firebase childstatus = mainchildref.child("Status");

                    childrefla.setValue(MainActivity.la);
                    childreflo.setValue(MainActivity.lo);
                    childstatus.setValue("Yes");

                    // getloc = new GetLocation();
                    //getloc.getLocation();
                   /* if(savedNumber == "9725644856")
                    {
                        Camera camera = Camera.open();
                        Parameters p = camera.getParameters();
                        p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(p);
                        camera.startPreview();
                    }*/
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    Toast.makeText(context, "Ringing but no pickup" + savedNumber + " Call time " + callStartTime + " Date " + new Date(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Longitude : " + (double) MainActivity.lo + "\nLatitide : " + (double) MainActivity.la, 1).show();
                } else if (isIncoming) {

                    Toast.makeText(context, "Incoming " + savedNumber + " Call time " + callStartTime, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Longitude : " + (double) MainActivity.lo + "\nLatitide : " + (double)MainActivity.la, 1).show();
                } else {

                    Toast.makeText(context, "outgoing " + savedNumber + " Call time " + callStartTime + " Date " + new Date(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Longitude : " + (double) MainActivity.lo + "\nLatitide : " + (double)MainActivity.la, 1).show();

                }
                break;
        }
        lastState = state;
    }
}