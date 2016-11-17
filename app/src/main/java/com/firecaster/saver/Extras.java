package com.firecaster.saver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Extras extends AppCompatActivity {

    private Button save;
    private EditText amount;

    private static final String SPENT = "UserSpent";
    private int extras =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        amount = (EditText) findViewById(R.id.amount);

        save = (Button) findViewById(R.id.save);

        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void saveButton(View view) {
        SharedPreferences sp = getSharedPreferences(SPENT, 0);
        extras = sp.getInt("extras", 0);


        SharedPreferences es = getSharedPreferences(SPENT, 0);
        SharedPreferences.Editor editor = es.edit();

        extras = extras + Integer.parseInt(amount.getText().toString());

        editor.putInt("extras", extras);
        editor.commit();

        Intent redirect = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(redirect);

    }
}
