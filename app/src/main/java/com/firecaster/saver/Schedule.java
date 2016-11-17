package com.firecaster.saver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

    Button resetButton;
    Button saveButton;
    public static final String SCHEDULE_DATA_FILE = "UserScheduleFile";
    ToggleButton mon_mor, mon_eve, mon_nig, tue_mor, tue_eve, tue_nig, wed_mor, wed_eve,
            wed_nig, thur_mor, thur_eve, thur_nig, fri_mor, fri_eve, fri_nig, sat_mor, sat_eve, sat_nig;
    ClassDays monday;
    ClassDays tuesday;
    ClassDays wednesday;
    ClassDays thursday;
    ClassDays friday;
    ClassDays saturday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //inicializa las variables
        iniVar();

        // verifyUserData();
        getUserSchedule();

        resetButton = (Button) findViewById(R.id.reset_schedule);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSchedule();
            }
        });


        saveButton = (Button) findViewById(R.id.save_schedule);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveSchedule();
                saveUserSchedule();
                Toast.makeText(getApplicationContext(), R.string.schedule_saved, Toast.LENGTH_LONG).show();

            }
        });


    }


    public void saveSchedule() {

        monday = new ClassDays("Monday", mon_mor.isChecked(), mon_eve.isChecked(), mon_nig.isChecked());
        tuesday = new ClassDays("Tuesday", tue_mor.isChecked(), tue_eve.isChecked(), tue_nig.isChecked());
        wednesday = new ClassDays("Wednesday", wed_mor.isChecked(), wed_eve.isChecked(), wed_nig.isChecked());
        thursday = new ClassDays("Thursday", thur_mor.isChecked(), thur_eve.isChecked(), thur_nig.isChecked());
        friday = new ClassDays("Friday", fri_mor.isChecked(), fri_eve.isChecked(), fri_nig.isChecked());
        saturday = new ClassDays("Saturday", sat_mor.isChecked(), sat_eve.isChecked(), sat_nig.isChecked());
    }

    //posible metodo a borrar ya que ahora verifica en el main activity y no aqui
    public void verifyUserData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        String getMonday = sharedPreferences.getString("Monday", "No Data saved");
        String getTuesday = sharedPreferences.getString("Tuesday", "No Data saved");
        String getWednesday = sharedPreferences.getString("Wednesday", "No Data saved");
        String getThursday = sharedPreferences.getString("Thursday", "No Data saved");
        String getFriday = sharedPreferences.getString("Friday", "No Data saved");
        String getSaturday = sharedPreferences.getString("Saturday", "No Data saved");
        ClassDays tempMon, tempTue, tempWed, tempThur, tempFri, tempSat;

        if (getMonday.equals("No Data saved")) {
            saveSchedule();
            saveUserSchedule();
            getUserSchedule();
        } else {
            getUserSchedule();
        }
    }


    // para guardar los datos de la cuenta en el almacenamiento del telefono
    public void saveUserSchedule() {
        SharedPreferences sharedPreferences = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Monday", monday.toString());
        editor.putString("Tuesday", tuesday.toString());
        editor.putString("Wednesday", wednesday.toString());
        editor.putString("Thursday", thursday.toString());
        editor.putString("Friday", friday.toString());
        editor.putString("Saturday", saturday.toString());
        editor.commit();
    }


    public void getUserSchedule() {
        SharedPreferences sharedPreferences = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        String getMonday = sharedPreferences.getString("Monday", "No Data saved");
        String getTuesday = sharedPreferences.getString("Tuesday", "No Data saved");
        String getWednesday = sharedPreferences.getString("Wednesday", "No Data saved");
        String getThursday = sharedPreferences.getString("Thursday", "No Data saved");
        String getFriday = sharedPreferences.getString("Friday", "No Data saved");
        String getSaturday = sharedPreferences.getString("Saturday", "No Data saved");
        ClassDays tempMon, tempTue, tempWed, tempThur, tempFri, tempSat;

        String m[] = getMonday.split("-");
        String t[] = getTuesday.split("-");
        String w[] = getWednesday.split("-");
        String th[] = getThursday.split("-");
        String f[] = getFriday.split("-");
        String s[] = getSaturday.split("-");
        tempMon = new ClassDays(m[0], Boolean.parseBoolean(m[1]), Boolean.parseBoolean(m[2]), Boolean.parseBoolean(m[3]));
        tempTue = new ClassDays(t[0], Boolean.parseBoolean(t[1]), Boolean.parseBoolean(t[2]), Boolean.parseBoolean(t[3]));
        tempWed = new ClassDays(w[0], Boolean.parseBoolean(w[1]), Boolean.parseBoolean(w[2]), Boolean.parseBoolean(w[3]));
        tempThur = new ClassDays(th[0], Boolean.parseBoolean(th[1]), Boolean.parseBoolean(th[2]), Boolean.parseBoolean(th[3]));
        tempFri = new ClassDays(f[0], Boolean.parseBoolean(f[1]), Boolean.parseBoolean(f[2]), Boolean.parseBoolean(f[3]));
        tempSat = new ClassDays(s[0], Boolean.parseBoolean(s[1]), Boolean.parseBoolean(s[2]), Boolean.parseBoolean(s[3]));


        mon_mor.setChecked(tempMon.isMorning());
        mon_eve.setChecked(tempMon.isEvening());
        mon_nig.setChecked(tempMon.isNight());
        tue_mor.setChecked(tempTue.isMorning());
        tue_eve.setChecked(tempTue.isEvening());
        tue_nig.setChecked(tempTue.isNight());
        wed_mor.setChecked(tempWed.isMorning());
        wed_eve.setChecked(tempWed.isEvening());
        wed_nig.setChecked(tempWed.isNight());
        thur_mor.setChecked(tempThur.isMorning());
        thur_eve.setChecked(tempThur.isEvening());
        thur_nig.setChecked(tempThur.isNight());
        fri_mor.setChecked(tempFri.isMorning());
        fri_eve.setChecked(tempFri.isEvening());
        fri_nig.setChecked(tempFri.isNight());
        sat_mor.setChecked(tempSat.isMorning());
        sat_eve.setChecked(tempSat.isEvening());
        sat_nig.setChecked(tempSat.isNight());


    }


    public void resetSchedule() {
        mon_mor.setChecked(false);
        mon_eve.setChecked(false);
        mon_nig.setChecked(false);
        tue_mor.setChecked(false);
        tue_eve.setChecked(false);
        tue_nig.setChecked(false);
        wed_mor.setChecked(false);
        wed_eve.setChecked(false);
        wed_nig.setChecked(false);
        thur_mor.setChecked(false);
        thur_eve.setChecked(false);
        thur_nig.setChecked(false);
        fri_mor.setChecked(false);
        fri_eve.setChecked(false);
        fri_nig.setChecked(false);
        sat_mor.setChecked(false);
        sat_eve.setChecked(false);
        sat_nig.setChecked(false);

    }


    public void iniVar() {
        mon_mor = (ToggleButton) findViewById(R.id.mon_mor);
        mon_eve = (ToggleButton) findViewById(R.id.mon_eve);
        mon_nig = (ToggleButton) findViewById(R.id.mon_nig);
        tue_mor = (ToggleButton) findViewById(R.id.tue_mor);
        tue_eve = (ToggleButton) findViewById(R.id.tue_eve);
        tue_nig = (ToggleButton) findViewById(R.id.tue_nig);
        wed_mor = (ToggleButton) findViewById(R.id.wed_mor);
        wed_eve = (ToggleButton) findViewById(R.id.wed_eve);
        wed_nig = (ToggleButton) findViewById(R.id.wed_nig);
        thur_mor = (ToggleButton) findViewById(R.id.thur_mor);
        thur_eve = (ToggleButton) findViewById(R.id.thur_eve);
        thur_nig = (ToggleButton) findViewById(R.id.thur_nig);
        fri_mor = (ToggleButton) findViewById(R.id.fri_mor);
        fri_eve = (ToggleButton) findViewById(R.id.fri_eve);
        fri_nig = (ToggleButton) findViewById(R.id.fri_nig);
        sat_mor = (ToggleButton) findViewById(R.id.sat_mor);
        sat_eve = (ToggleButton) findViewById(R.id.sat_eve);
        sat_nig = (ToggleButton) findViewById(R.id.sat_nig);
    }
}
