package com.example.kritika.miniproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.example.kritika.miniproject.MainNavigation.UserList;


/**
 * Created by kritika on 25-11-2017.
 */

public class FragmentContact extends Fragment {
    private static final int REQUEST_CODE = 1;
    Button chooseContact;
    static ListView ContactList;
    SharedPreferences sharedpreferences;
      FragmentHome fragment ;
     Bundle bundle = new Bundle();
    DBHelper mydb;

    String key;
    Set<String> Phoneset;
   SharedPreferences PhoneListShared ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view=inflater.inflate(R.layout.fragment_contact, container, false);

        chooseContact=(Button)view.findViewById(R.id.btnContacts);

        ContactList=(ListView)view.findViewById(R.id.ContactList);


        mydb = new DBHelper(getActivity());

        sharedpreferences = this.getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);


        ContactList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, UserList));

        chooseContact.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        int n=UserList.size();
                        if(n<5) {

                            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                            startActivityForResult(contactPickerIntent, REQUEST_CODE);
                            loadContacts();

                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?")
                        .setTitle("Delete Contact");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked yes button

                       Object  temp= ContactList.getAdapter().getItem(i);
                      String[] name = temp.toString().trim().split(" ");
                       String phone = name[name.length - 1].trim();
                        mydb.deleteAContact(phone);
                        UserList=mydb.getAllCotacts();
                        ContactList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, UserList));



                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                loadContacts();
                return true;
            }
        });

        return view;
    }

    private void loadContacts() {

    }

    @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //you can set the title for your toolbar here for different fragments different titles
            getActivity().setTitle("Emergency Contacts");
        }




    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {

                case REQUEST_CODE:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null;
                        String name = null;
                        Uri uri = data.getData();

                        cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int NameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        name = cursor.getString(NameColumnIndex);
                        phoneNo = cursor.getString(phoneIndex);

                        mydb.insertContact(name,phoneNo);
                          UserList=mydb.getAllCotacts();


                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                       ContactList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, UserList));
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }


}
