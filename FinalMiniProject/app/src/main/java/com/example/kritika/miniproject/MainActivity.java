//package com.example.kritika.miniproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/* not used***
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static final int MAP_CODE = 2;
    Button chooseContact,callPolice;
    TextView Contacts;
    ListView ContactList;
    ArrayList<String> UserList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseContact=(Button)findViewById(R.id.btnContacts);
        callPolice=(Button)findViewById(R.id.btnPolice);
        ContactList=(ListView)findViewById(R.id.ContactList);


        chooseContact.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        int n=UserList.size();
                        if(n<5) {

                            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                            startActivityForResult(contactPickerIntent, REQUEST_CODE);
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Cannot Add more Contacts");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }

                    }
                });

        ContactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.v("long clicked","pos: " + i);
                //UserList.remove(i);
              //  ContactList.removeViewAt(i);


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure?")
                        .setTitle("Delete Contact");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked yes button

                        UserList.remove(i);
                        ContactList.invalidateViews();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });



        callPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* to get nearest police station
               /* Intent intentMap=new Intent(MainActivity.this,MapsActivity.class);
                startActivityForResult(intentMap,MAP_CODE);*/

          /*      String phoneNumber="tel:"+"+919067712003";  //replace with 100
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(phoneNumber));
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {

                case REQUEST_CODE:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null ;
                        String name = null;
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int NameColumnIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        name=cursor.getString(NameColumnIndex);
                        phoneNo = cursor.getString(phoneIndex);

                        //Contacts.setText(name+" "+phoneNo);
                        UserList.add(name+" "+phoneNo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ContactList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, UserList));
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }




}
*/