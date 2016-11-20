package com.firecaster.saver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private long mLastPress = 0;    // Cuándo se pulsó atrás por última vez
    private long mTimeLimit = 2000; // Límite de tiempo entre pulsaciones, en ms
    private TextView userName;
    private TextView userEmail;
    private ImageView userPictureURL;
    private TextView Prev_val_1, Prev_val_2, Prev_val_3, Prev_val_4, Prev_val_5, Prev_val_6, Prev_val_7, Prev_val_8, Prev_val_9, Prev_val_10;
    private TextView real_val1, real_val2, real_val3, real_val4, real_val5, real_val6, real_val7, real_val8, real_val9, real_val10;

    String n;
    String e;
    String p;

    public static final String USER_DATA_FILE = "UserGoogleDataFile";
    public static final String CHECKBOX_STATES = "checkboxStates";
    public static final String SCHEDULE_DATA_FILE = "UserScheduleFile";
    private static final String SPENT_FILE = "UserSpent";
    private static final String VALUED_FILE = "UserValued";
    public static final String VALUES = "values";


    private ClassDays monday;
    private ClassDays tuesday;
    private ClassDays wednesday;
    private ClassDays thursday;
    private ClassDays friday;
    private ClassDays saturday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Para poder accesar a los datos del navigation view, si no no sirve**
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        //para settear el nombre  correo de usuario google
        userName = (TextView) header.findViewById(R.id.userGoogleName);
        userEmail = (TextView) header.findViewById(R.id.userGoogleEmail);
        userPictureURL = (ImageView) header.findViewById(R.id.userGooglePic);

        getData();

        verifyWeekNotifications();

        setTextView();

        defTextView();

        addSpent();
    }

    //setea valores previstos para los gastos
    public void defTextView() {
        SharedPreferences sp = getSharedPreferences(VALUED_FILE, 0);

        int breakfast = sp.getInt("breakfast", 0);
        int launch = sp.getInt("launch", 0);
        int dinner = sp.getInt("dinner", 0);
        int transportation = sp.getInt("transportation", 0);

        SharedPreferences spp = getSharedPreferences(VALUES, 0);

        int internet = spp.getInt("internet", 0);
        int water = spp.getInt("water", 0);
        int electricity = spp.getInt("electricity", 0);
        int renting = spp.getInt("renting", 0);
        int extras = 0;


        Prev_val_1.setText(Integer.toString(breakfast));
        Prev_val_2.setText(Integer.toString(launch));
        Prev_val_3.setText(Integer.toString(dinner));
        Prev_val_4.setText(Integer.toString(transportation));
        Prev_val_5.setText(Integer.toString(internet));
        Prev_val_6.setText(Integer.toString(water));
        Prev_val_7.setText(Integer.toString(electricity));
        Prev_val_8.setText(Integer.toString(renting));
        Prev_val_9.setText(Integer.toString(extras));

        int totalV = breakfast + launch + dinner + transportation + internet + water + electricity + renting;

        Prev_val_10.setText(Integer.toString(totalV));


    }

    //agregar valores gastados hasta la fecha
    public void addSpent() {
        SharedPreferences sp = getSharedPreferences(SPENT_FILE, 0);

        int extras = sp.getInt("extras", 0);
        int breakfast = sp.getInt("breakfast", 0);
        int launch = sp.getInt("launch", 0);
        int dinner = sp.getInt("dinner", 0);
        int trans = sp.getInt("transportation", 0);
        int inter = sp.getInt("internet", 0);
        int water = sp.getInt("water", 0);
        int elect = sp.getInt("electricity", 0);
        int rent = sp.getInt("renting", 0);

        real_val1.setText(Integer.toString(breakfast));
        real_val2.setText(Integer.toString(launch));
        real_val3.setText(Integer.toString(dinner));
        real_val4.setText(Integer.toString(trans));
        real_val5.setText(Integer.toString(inter));
        real_val6.setText(Integer.toString(water));
        real_val7.setText(Integer.toString(elect));
        real_val8.setText(Integer.toString(rent));
        real_val9.setText(Integer.toString(extras));

        int total = extras + breakfast + launch + dinner + trans + inter + water + elect + rent;

        real_val10.setText(Integer.toString(total));

    }

    //valores de establecidos anteriormente
    public int values(int i) {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);

        switch (i) {
            case 1:
                int breakfast = sp.getInt("breakfast", 0);
                return breakfast;
            case 2:
                int launch = sp.getInt("launch", 0);
                return launch;
            case 3:
                int dinner = sp.getInt("dinner", 0);
                return dinner;
            case 4:
                int transportation = sp.getInt("transportation", 0);
                return transportation;
            case 5:
                int internet = sp.getInt("internet", 0);
                return internet;
            case 6:
                int water = sp.getInt("water", 0);
                return water;
            case 7:
                int electricity = sp.getInt("electricity", 0);
                return electricity;
            case 8:
                int renting = sp.getInt("renting", 0);
                return renting;
        }

        return 0;
    }

    //evalua que dia de la semana es
    public void valuedValues() {
        SharedPreferences ch_SPreferences = getSharedPreferences(CHECKBOX_STATES, 0);
        SharedPreferences schedule_SPreferences = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        int cb_breakfast = ch_SPreferences.getInt("Breakfast", 0);
        int cb_launch = ch_SPreferences.getInt("Launch", 0);
        int cb_dinner = ch_SPreferences.getInt("Dinner", 0);
        int cb_trans = ch_SPreferences.getInt("Transportation", 0);

        String getMonday = schedule_SPreferences.getString("Monday", "No Data saved");
        String getTuesday = schedule_SPreferences.getString("Tuesday", "No Data saved");
        String getWednesday = schedule_SPreferences.getString("Wednesday", "No Data saved");
        String getThursday = schedule_SPreferences.getString("Thursday", "No Data saved");
        String getFriday = schedule_SPreferences.getString("Friday", "No Data saved");
        String getSaturday = schedule_SPreferences.getString("Saturday", "No Data saved");
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

        Calendar c = Calendar.getInstance();
        int last = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < last; i++) {
            c.set(Calendar.DAY_OF_MONTH, i);

            switch (c.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    writeData(tempMon, cb_breakfast, cb_launch, cb_dinner, cb_trans);
                    break;

                case Calendar.TUESDAY:
                    writeData(tempTue, cb_breakfast, cb_launch, cb_dinner, cb_trans);
                    break;

                case Calendar.WEDNESDAY:
                    writeData(tempWed, cb_breakfast, cb_launch, cb_dinner, cb_trans);
                    break;

                case Calendar.THURSDAY:
                    writeData(tempThur, cb_breakfast, cb_launch, cb_dinner, cb_trans);
                    break;

                case Calendar.FRIDAY:
                    writeData(tempFri, cb_breakfast, cb_launch, cb_dinner, cb_trans);
                    break;

                case Calendar.SATURDAY:
                    writeData(tempSat, cb_breakfast, cb_launch, cb_dinner, cb_trans);
                    break;
            }
        }

    }

    //escribe el archivo de los valores presupuestados
    public void writeData(ClassDays days, int b, int l, int d, int t) {
        if (days.isMorning() && b == 1) {
            SharedPreferences sp = getSharedPreferences(VALUED_FILE, 0);
            int breakfast = sp.getInt("breakfast", 0);
            SharedPreferences spp = getSharedPreferences(VALUED_FILE, 0);
            SharedPreferences.Editor editor = spp.edit();
            editor.putInt("breakfast", breakfast + values(1));
            editor.commit();
        }

        if (days.isEvening() && l == 1) {
            SharedPreferences sp = getSharedPreferences(VALUED_FILE, 0);
            int launch = sp.getInt("launch", 0);
            SharedPreferences spp = getSharedPreferences(VALUED_FILE, 0);
            SharedPreferences.Editor editor = spp.edit();
            editor.putInt("launch", launch + values(2));
            editor.commit();
        }

        if (days.isNight() && d == 1) {
            SharedPreferences sp = getSharedPreferences(VALUED_FILE, 0);
            int dinner = sp.getInt("dinner", 0);
            SharedPreferences spp = getSharedPreferences(VALUED_FILE, 0);
            SharedPreferences.Editor editor = spp.edit();
            editor.putInt("dinner", dinner + values(3));
            editor.commit();
        }

        if ((days.isEvening() || days.isNight() || days.isMorning()) && t == 1) {
            SharedPreferences sp = getSharedPreferences(VALUED_FILE, 0);
            int trans = sp.getInt("transportation", 0);
            SharedPreferences spp = getSharedPreferences(VALUED_FILE, 0);
            SharedPreferences.Editor editor = spp.edit();
            editor.putInt("transportation", trans + values(4));
            editor.commit();
        }
    }

    //crea los elementos de las parte grafica  del main
    public void setTextView() {

        Prev_val_1 = (TextView) findViewById(R.id.Prev_val_1);
        Prev_val_2 = (TextView) findViewById(R.id.Prev_val_2);
        Prev_val_3 = (TextView) findViewById(R.id.Prev_val_3);
        Prev_val_4 = (TextView) findViewById(R.id.Prev_val_4);
        Prev_val_5 = (TextView) findViewById(R.id.Prev_val_5);
        Prev_val_6 = (TextView) findViewById(R.id.Prev_val_6);
        Prev_val_7 = (TextView) findViewById(R.id.Prev_val_7);
        Prev_val_8 = (TextView) findViewById(R.id.Prev_val_8);
        Prev_val_9 = (TextView) findViewById(R.id.Prev_val_9);
        Prev_val_10 = (TextView) findViewById(R.id.Prev_val_10);

        real_val1 = (TextView) findViewById(R.id.real_val_1);
        real_val2 = (TextView) findViewById(R.id.real_val_2);
        real_val3 = (TextView) findViewById(R.id.real_val_3);
        real_val4 = (TextView) findViewById(R.id.real_val_4);
        real_val5 = (TextView) findViewById(R.id.real_val_5);
        real_val6 = (TextView) findViewById(R.id.real_val_6);
        real_val7 = (TextView) findViewById(R.id.real_val_7);
        real_val8 = (TextView) findViewById(R.id.real_val_8);
        real_val9 = (TextView) findViewById(R.id.real_val_9);
        real_val10 = (TextView) findViewById(R.id.real_val_10);

    }

    public void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA_FILE, 0);
        n = sharedPreferences.getString("User_Name", "No Data");
        e = sharedPreferences.getString("User_Email", "No Data");
        p = sharedPreferences.getString("User_Picture_URL", "No Data");

        //settear texto
        userName.setText(n);
        userEmail.setText(e);


        //setear imagen perfil con libreria picasso
        //cambiarlo para almacenamiento en local storage******
        Picasso.with(MainActivity.this)
                .load(p)
                .resize(117, 117)
                .centerCrop()
                .transform(new CircleTransform())
                .into(userPictureURL);
    }

    //El metodo verifica si el drawer está cerrado, si no esta cerrado el boton atras locierta, si está cerrado verifica
    //para apretar dos veces para salir
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toast onBackPressedToast = Toast.makeText(this, R.string.press_once_again_to_exit, Toast.LENGTH_SHORT);
        long currentTime = System.currentTimeMillis();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentTime - mLastPress > mTimeLimit) {
                onBackPressedToast.show();
                mLastPress = currentTime;
            } else {
                onBackPressedToast.cancel();
                super.onBackPressed();

            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                callSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Schedule) {
            Intent schedule = new Intent(MainActivity.this, Schedule.class);
            startActivity(schedule);

        } else if (id == R.id.nav_ini_config) {
            Intent iniConfig = new Intent(MainActivity.this, InitialConfiguration.class);
            startActivity(iniConfig);

        } else if (id == R.id.nav_Tutorial) {
            Toast swipeToast = Toast.makeText(this, R.string.swipe_toast, Toast.LENGTH_SHORT);
            Intent tutorial = new Intent(MainActivity.this, Tutorial.class);
            startActivity(tutorial);
            swipeToast.show();


        } else if (id == R.id.nav_calendar) {
            Intent calendar = new Intent(MainActivity.this, CalendarWidgetWindow.class);
            startActivity(calendar);

        } else if (id == R.id.nav_share) {
            Intent share = new Intent(MainActivity.this, Share.class);
            startActivity(share);

        } else if (id == R.id.nav_about) {
            Intent aboutApp = new Intent(MainActivity.this, AboutApp.class);
            startActivity(aboutApp);

        } else if (id == R.id.nav_extra) {
            Intent extras = new Intent(MainActivity.this, Extras.class);
            startActivity(extras);

        } else if (id == R.id.nav_external_links) {
            Intent externalLinks = new Intent(MainActivity.this, ExternalLinks.class);
            startActivity(externalLinks);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    //metodo llamado al dar clic en boton settings
    public void callSettings() {
        Intent settings = new Intent(MainActivity.this, com.firecaster.saver.Settings.class);
        startActivity(settings);
    }

    //Metodo que se encarga de cerrar el proceso
    public void appExit() {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(0);
    }

    //RTC_Wakeup es para que sirva inclusio si el telefono esta bloqueado
    //Interval day para que sea diaria
    public void dailyAlarms(int dayOfWeek, int hour, int minutes, int seconds, int id, String title, String text) {
        Calendar calendar = Calendar.getInstance();
        String enter = getResources().getString(R.string.enter);

        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("ID", id);
        intent.putExtra("TITLE", title);
        intent.putExtra("TEXT", text);
        intent.putExtra("ACTION", enter);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void verifyWeekNotifications() {
        SharedPreferences schedule_SPreferences = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        String getMonday = schedule_SPreferences.getString("Monday", "No Data saved");
        SharedPreferences sp = getSharedPreferences(VALUED_FILE, 0);
        int breakfast = sp.getInt("breakfast", 0);
        if (getMonday.equals("No Data saved")) {
            saveEmptySchedule();
            getWeekNotifications();
        } else {
            if (breakfast == 0) {
                valuedValues();
            }
            getWeekNotifications();
        }
    }

    public void getWeekNotifications() {
        SharedPreferences ch_SPreferences = getSharedPreferences(CHECKBOX_STATES, 0);
        SharedPreferences schedule_SPreferences = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        int cb_breakfast = ch_SPreferences.getInt("Breakfast", 0);
        int cb_launch = ch_SPreferences.getInt("Launch", 0);
        int cb_dinner = ch_SPreferences.getInt("Dinner", 0);

        String getMonday = schedule_SPreferences.getString("Monday", "No Data saved");
        String getTuesday = schedule_SPreferences.getString("Tuesday", "No Data saved");
        String getWednesday = schedule_SPreferences.getString("Wednesday", "No Data saved");
        String getThursday = schedule_SPreferences.getString("Thursday", "No Data saved");
        String getFriday = schedule_SPreferences.getString("Friday", "No Data saved");
        String getSaturday = schedule_SPreferences.getString("Saturday", "No Data saved");
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

        String notification = getResources().getString(R.string.app_name);
        String breakfastTime = getResources().getString(R.string.breakfast_time);
        String launchTime = getResources().getString(R.string.launch_time);
        String dinnerTime = getResources().getString(R.string.dinner_time);


        verifyMorning(cb_breakfast, 2, tempMon, 9, 50, 0, 0, notification, breakfastTime);
        verifyMorning(cb_breakfast, 3, tempTue, 9, 50, 0, 1, notification, breakfastTime);
        verifyMorning(cb_breakfast, 4, tempWed, 9, 50, 0, 2, notification, breakfastTime);
        verifyMorning(cb_breakfast, 5, tempThur, 9, 50, 0, 3, notification, breakfastTime);
        verifyMorning(cb_breakfast, 6, tempFri, 9, 50, 0, 4, notification, breakfastTime);
        verifyMorning(cb_breakfast, 7, tempSat, 9, 50, 0, 5, notification, breakfastTime);

        verifyEvening(cb_launch, 2, tempMon, 12, 0, 0, 6, notification, launchTime);
        verifyEvening(cb_launch, 3, tempTue, 12, 0, 0, 7, notification, launchTime);
        verifyEvening(cb_launch, 4, tempWed, 12, 0, 0, 8, notification, launchTime);
        verifyEvening(cb_launch, 5, tempThur, 12, 0, 0, 9, notification, launchTime);
        verifyEvening(cb_launch, 6, tempFri, 12, 0, 0, 10, notification, launchTime);
        verifyEvening(cb_launch, 7, tempSat, 12, 0, 0, 11, notification, launchTime);

        verifyNight(cb_dinner, 2, tempMon, 17, 0, 0, 12, notification, dinnerTime);
        verifyNight(cb_dinner, 3, tempTue, 17, 0, 0, 13, notification, dinnerTime);
        verifyNight(cb_dinner, 4, tempWed, 17, 0, 0, 14, notification, dinnerTime);
        verifyNight(cb_dinner, 5, tempThur, 17, 0, 0, 15, notification, dinnerTime);
        verifyNight(cb_dinner, 6, tempFri, 17, 0, 0, 16, notification, dinnerTime);
        verifyNight(cb_dinner, 7, tempSat, 17, 0, 0, 17, notification, dinnerTime);
    }

    public void loadDate(Calendar received, int weekDay, int hour, int minutes, int seconds) {
        int tmpYear = Calendar.getInstance().get(Calendar.YEAR);
        int tmpMonth = Calendar.getInstance().get(Calendar.MONTH);
         int tmpDayofMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        received.set(tmpYear, tmpMonth, tmpDayofMonth, hour, minutes, seconds);
        received.set(Calendar.DAY_OF_WEEK, weekDay);


    }

    public void verifyMorning(int checked, int dayOfWeek, ClassDays day, int hour, int minutes, int seconds, int id, String title, String text) {
        String TAG = "PRUEBA: ";
        Calendar currentTime = Calendar.getInstance();


        Calendar received = Calendar.getInstance();

        loadDate(received, dayOfWeek, hour, minutes, seconds);

        Log.d(TAG,received.getTime().toString());

        if (received.after(currentTime)) {
            if ((checked == 1) && day.isMorning()) {
                dailyAlarms(dayOfWeek, hour, minutes, seconds, id, title, text);
            }
        }


    }

    public void verifyEvening(int checked, int dayOfWeek, ClassDays day, int hour, int minutes, int seconds, int id, String title, String text) {
        Calendar currentTime = Calendar.getInstance();
        Calendar received = Calendar.getInstance();

        loadDate(received, dayOfWeek, hour, minutes, seconds);

        if (received.after(currentTime)) {
            if ((checked == 1) && day.isEvening()) {
                dailyAlarms(dayOfWeek, hour, minutes, seconds, id, title, text);
            }
        }
    }

    public void verifyNight(int checked, int dayOfWeek, ClassDays day, int hour, int minutes, int seconds, int id, String title, String text) {
        Calendar currentTime = Calendar.getInstance();
        Calendar received = Calendar.getInstance();

        loadDate(received, dayOfWeek, hour, minutes, seconds);

        if (received.after(currentTime)) {
            if ((checked == 1) && day.isNight()) {
                dailyAlarms(dayOfWeek, hour, minutes, seconds, id, title, text);
            }
        }
    }

    public void saveEmptySchedule() {

        monday = new ClassDays("Monday", false, false, false);
        tuesday = new ClassDays("Tuesday", false, false, false);
        wednesday = new ClassDays("Wednesday", false, false, false);
        thursday = new ClassDays("Thursday", false, false, false);
        friday = new ClassDays("Friday", false, false, false);
        saturday = new ClassDays("Saturday", false, false, false);

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

}
