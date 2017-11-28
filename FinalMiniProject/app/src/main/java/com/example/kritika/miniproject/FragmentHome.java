package com.example.kritika.miniproject;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.example.kritika.miniproject.MainNavigation.UserList;

/**
 * Created by kritika on 25-11-2017.
 */

public class FragmentHome extends Fragment {

    static ArrayList<String> ContactList = new ArrayList<String>();
    ImageButton callPolice, location, fakecall,buzzer;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 9;
    Set<String> Phoneset;
    private static final int FC = 2;
    SharedPreferences sharedpreferences;
    static boolean musicOn=false;

    MediaPlayer mediaPlayer;

    int resID;

    static String myLastLocation;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.activity_main, container, false);
        callPolice = (ImageButton) view.findViewById(R.id.btnPolice);

        location = (ImageButton) view.findViewById(R.id.btnLocation);


        fakecall = (ImageButton) view.findViewById(R.id.btnFakeCall);

        buzzer=(ImageButton)view.findViewById(R.id.btnBuzzer);


        sharedpreferences = this.getActivity().getSharedPreferences("PhonePreferences", Context.MODE_PRIVATE);

        Phoneset = sharedpreferences.getAll().keySet();
        for (String key : Phoneset) {
            Log.d(TAG, key);

        }
        //id for music file
        resID = getResources().getIdentifier("alarm", "raw", getActivity().getPackageName());

        buzzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    if(musicOn==false){
                       // mediaPlayer.prepare();
                        mediaPlayer = MediaPlayer.create(getActivity(), resID);
                         mediaPlayer.start();
                        musicOn=true;
                    }
                    else if (musicOn==true) {
                        mediaPlayer.stop();
                        musicOn=false;
                    }

                }catch(Exception e){e.printStackTrace();}
            }
        });


        fakecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 10);
                long selectedTimeInMilliseconds = calendar.getTimeInMillis();

                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getActivity(), MyReceiver.class);

                PendingIntent fakePendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, selectedTimeInMilliseconds, fakePendingIntent);
                Toast.makeText(getActivity(), "DAD will call you soon..", Toast.LENGTH_SHORT).show();

                Intent intents = new Intent(getActivity(), MainNavigation.class);
                startActivity(intents);
            }
        });

        callPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* to get nearest police station
               /* Intent intentMap=new Intent(MainActivity.this,MapsActivity.class);
                startActivityForResult(intentMap,MAP_CODE);*/

                String phoneNumber = "tel:" + "+919067712003";  //replace with 100
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(phoneNumber));
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //callIntent.putExtra("com.android.phone.extra.slot", 0);
                startActivity(callIntent);

            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locInt = new Intent(getActivity(), myLocation.class);

                startActivityForResult(locInt, 99);

            }


        });


        return view;
    }


    //sending sms

    public void sendSMSMessage() {
        // TODO Auto-generated method stub

        Log.i("Send SMS", "");
        try {
            SmsManager smsManager = SmsManager.getDefault();

            for (int i = 0; i < UserList.size(); i++) {
                String[] name = UserList.get(i).trim().split(" ");
                String phone = name[name.length - 1].trim();
                Log.e("name", name[0]);
                Log.e("ph", phone);

                smsManager.sendTextMessage(phone, null, "Hey " + name[0] + ", This is an emergency...My Last Tracked Location: "+myLastLocation, null, null);

            }

            Toast.makeText(getActivity(), "SMS Sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(),
                    "SMS faild, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 10
        if (requestCode == 99 && data!=null) {
            String getLocation=data.getStringExtra("mylocation");

                myLastLocation=getLocation;



        }



        if(!UserList.isEmpty()) {
            String[] name = UserList.get(0).trim().split(" ");
            String phone = name[name.length - 1].trim();

            //sending whatsapp sms to top list contact
            PackageManager packageManager = getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);

            try {
                String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode("Hey, this is an emergencyMy Last Tracked Location:" + myLastLocation, "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    getActivity().startActivity(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            sendSMSMessage();
        }
        else

            Toast.makeText(getActivity(), "Emergency contact List is empty",
                    Toast.LENGTH_LONG).show();

    }
}
