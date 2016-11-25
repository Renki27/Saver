package com.firecaster.saver;


import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class TimePickers extends AppCompatActivity {

    Button datePicker, timePicker;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pickers);



        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        datePicker = (Button) findViewById(R.id.bt_date_picker);
        timePicker = (Button) findViewById(R.id.bt_time_picker);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("ID");


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });

    }






}
