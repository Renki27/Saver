package com.firecaster.saver;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends Activity {
    private int delay = 4000;
    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sound = MediaPlayer.create(this, R.raw.splash_sound_screen);
        sound.start();
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
}
