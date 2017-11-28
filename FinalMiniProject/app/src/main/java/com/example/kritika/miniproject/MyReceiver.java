package com.example.kritika.miniproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");



        Intent intentObject = new Intent(context.getApplicationContext(), FakeCall.class);
        intentObject.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentObject.putExtra("myfakename", "DAD");
        intentObject.putExtra("myfakenumber", "8765434567");
        context.startActivity(intentObject);
    }
}
