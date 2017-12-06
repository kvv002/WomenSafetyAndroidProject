package com.example.kritika.miniproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import static com.example.kritika.miniproject.MainNavigation.Option;

public class SmsOption extends Fragment {

    RadioGroup radioGroup;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view=inflater.inflate(R.layout.activity_sms_option, container, false);
        radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);
        if(Option.equals("Option2"))
            radioGroup.check(R.id.Option2);
        else if(Option.equals("Option3"))
            radioGroup.check(R.id.Option3);
        else radioGroup.check(R.id.Option1);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                RadioButton checked = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                Option=getResources().getResourceName(checked.getId());
                Option=Option.substring(Option.lastIndexOf("/") + 1);




              pref = getActivity().getSharedPreferences("myPreferences", 0); // 0 - for private mode

               editor = pref.edit();
                editor.putString("Option",Option);
                editor.commit();

                if (null != checked && checkedId > -1) {
                    Toast.makeText(getActivity(), Option, Toast.LENGTH_SHORT).show();
                }

            }



        });
        return view;
    }
}
