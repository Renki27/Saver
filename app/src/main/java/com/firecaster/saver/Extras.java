package com.firecaster.saver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Extras extends AppCompatActivity {

    private Button add;
    private EditText amount;

    private static final String SPENT = "UserSpent";
    private int extras = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        amount = (EditText) findViewById(R.id.amount);

        add = (Button) findViewById(R.id.add_extras);

        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue();
            }
        });

    }

    public void addValue() {


        if ((amount.getText().toString().equals("")) || (amount.getText().toString().equals("0"))) {
            Toast.makeText(this, R.string.invalid_value, Toast.LENGTH_SHORT).show();
        } else {
            getExtras();
            SharedPreferences es = getSharedPreferences(SPENT, 0);
            SharedPreferences.Editor editor = es.edit();
            extras += Integer.parseInt(amount.getText().toString());
            Toast.makeText(this, R.string.data_saved_successfully, Toast.LENGTH_SHORT).show();
            amount.setText("");
            editor.putInt("extras", extras);
            editor.commit();
        }


    }

    public void getExtras() {
        SharedPreferences sp = getSharedPreferences(SPENT, 0);
        extras = sp.getInt("extras", 0);

    }
}
