package com.firecaster.saver;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends Activity {
    private int delay = 4000;
    MediaPlayer sound;
    int selectedSound;
    public static final String SOUND_SELECTION = "soundSelection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        selectedSound = getSelectedSound();

        playSound();

//        Hilo para correr el splash screen antes de entrar a la aplicacion
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, GLogin.class);
                startActivity(intent);
                //importante el finish para que no se pueda volver a esta pantalla
                finish();
            }
        }, delay);
    }


    public int getSelectedSound() {
        int temp = -1;
        SharedPreferences sharedPreferences = getSharedPreferences(SOUND_SELECTION, 0);
        selectedSound = sharedPreferences.getInt("Sound", 0);
        switch (selectedSound) {
            case 0:
                temp = 0;
                break;

            case 1:
                temp = 1;
                break;
        }
        return temp;
    }


    public void playSound() {
        if (selectedSound == 0) {
            sound = MediaPlayer.create(this, R.raw.splash_sound_screen);
            sound.start();
        }
    }
}
