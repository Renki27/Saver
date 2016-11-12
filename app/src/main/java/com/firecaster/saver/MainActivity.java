package com.firecaster.saver;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private long mLastPress = 0;    // Cuándo se pulsó atrás por última vez
    private long mTimeLimit = 2000; // Límite de tiempo entre pulsaciones, en ms
    private TextView userName;
    private TextView userEmail;
    private ImageView userPictureURL;
    String n;
    String e;
    String p;
    public static final String USER_DATA_FILE = "UserGoogleDataFile";


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

        } else if (id == R.id.nav_confVariables) {


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


}
