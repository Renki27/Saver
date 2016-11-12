package com.firecaster.saver;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class InitialConfiguration extends AppCompatActivity {
    public static String CURRENCY_SYMBOL = "";
    public static final String CURRENCY_SELECTION = "currencySelection";
    public static final String VALUES = "values";
    private TextView c1, c2, c3, c4, c5, c6, c7, c8;
    private EditText txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8;
    private CheckBox breakfast, launch, diner, trans, inter, water, elect, rent;
    Button saveButton;

    private String cs;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_configuration);

        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        saveButton = (Button) findViewById(R.id.bt_save_user_values);


        setVariables();

        setListeners();

        verifyCurrencyValue();

        getCurrencySelection();

        setCurrency();

        verifyUserValues();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserValues();
                Toast.makeText(InitialConfiguration.this, R.string.data_saved_successfully, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setVariables() {
        c1 = (TextView) findViewById(R.id.tv_c1);
        c2 = (TextView) findViewById(R.id.tv_c2);
        c3 = (TextView) findViewById(R.id.tv_c3);
        c4 = (TextView) findViewById(R.id.tv_c4);
        c5 = (TextView) findViewById(R.id.tv_c5);
        c6 = (TextView) findViewById(R.id.tv_c6);
        c7 = (TextView) findViewById(R.id.tv_c7);
        c8 = (TextView) findViewById(R.id.tv_c8);

        txt1 = (EditText) findViewById(R.id.txt1);
        txt2 = (EditText) findViewById(R.id.txt2);
        txt3 = (EditText) findViewById(R.id.txt3);
        txt4 = (EditText) findViewById(R.id.txt4);
        txt5 = (EditText) findViewById(R.id.txt5);
        txt6 = (EditText) findViewById(R.id.txt6);
        txt7 = (EditText) findViewById(R.id.txt7);
        txt8 = (EditText) findViewById(R.id.txt8);

        breakfast = (CheckBox) findViewById(R.id.cb_breakfast);
        launch = (CheckBox) findViewById(R.id.cb_launch);
        diner = (CheckBox) findViewById(R.id.cb_diner);
        trans = (CheckBox) findViewById(R.id.cb_transportation);
        inter = (CheckBox) findViewById(R.id.cb_internet);
        water = (CheckBox) findViewById(R.id.cb_Water);
        rent = (CheckBox) findViewById(R.id.cb_Renting);
        elect = (CheckBox) findViewById(R.id.cb_Electricity);
    }

    public void setListeners() {
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(breakfast, txt1);
            }
        });

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(launch, txt2);
            }
        });

        diner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(diner, txt3);
            }
        });

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(trans, txt4);
            }
        });

        inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(inter, txt5);
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(water, txt6);
            }
        });


        elect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(elect, txt7);
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(rent, txt8);
            }
        });
    }

    public void getUserValues() {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);
        int breakfast = sp.getInt("breakfast", 0);
        int launch = sp.getInt("launch", 0);
        int diner = sp.getInt("diner", 0);
        int transportation = sp.getInt("transportation", 0);
        int internet = sp.getInt("internet", 0);
        int water = sp.getInt("water", 0);
        int electricity = sp.getInt("electricity", 0);
        int renting = sp.getInt("renting", 0);

        txt1.setText(breakfast + "");
        txt2.setText(launch + "");
        txt3.setText(diner + "");
        txt4.setText(transportation + "");
        txt5.setText(internet + "");
        txt6.setText(water + "");
        txt7.setText(electricity + "");
        txt8.setText(renting + "");

    }

    public void verifyUserValues() {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);
        int breakfast = sp.getInt("breakfast", 0);
        int launch = sp.getInt("launch", 0);
        int diner = sp.getInt("diner", 0);
        int transportation = sp.getInt("transportation", 0);
        int internet = sp.getInt("internet", 0);
        int water = sp.getInt("water", 0);
        int electricity = sp.getInt("electricity", 0);
        int renting = sp.getInt("renting", 0);
        if (breakfast == 0) {

            txt1.setText(breakfast + "");
            txt2.setText(launch + "");
            txt3.setText(diner + "");
            txt4.setText(transportation + "");
            txt5.setText(internet + "");
            txt6.setText(water + "");
            txt7.setText(electricity + "");
            txt8.setText(renting + "");

            saveUserValues();
            getUserValues();
        } else {
            getUserValues();
        }
    }

    //setea el valor de la moneda
    public void setCurrency() {
        c1.setText(cs);
        c2.setText(cs);
        c3.setText(cs);
        c4.setText(cs);
        c5.setText(cs);
        c6.setText(cs);
        c7.setText(cs);
        c8.setText(cs);

    }

    public void getCurrencySelection() {
        SharedPreferences sharedPreferences = getSharedPreferences(CURRENCY_SELECTION, 0);
        cs = sharedPreferences.getString("Currency", "No Data");
    }

    public void saveUserValues() {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("breakfast", Integer.parseInt(txt1.getText().toString()));
        editor.putInt("launch", Integer.parseInt(txt2.getText().toString()));
        editor.putInt("diner", Integer.parseInt(txt3.getText().toString()));
        editor.putInt("transportation", Integer.parseInt(txt4.getText().toString()));
        editor.putInt("internet", Integer.parseInt(txt5.getText().toString()));
        editor.putInt("water", Integer.parseInt(txt6.getText().toString()));
        editor.putInt("electricity", Integer.parseInt(txt7.getText().toString()));
        editor.putInt("renting", Integer.parseInt(txt8.getText().toString()));

        editor.commit();
    }


    //verifica el checkbox
    public void verifyCB(CheckBox cb, EditText et) {

        if (cb.isChecked()) {
            et.setEnabled(true);
        } else {
            et.setEnabled(false);
        }

    }

    public void verifyCurrencyValue() {
        String value = "₡";
        SharedPreferences sharedPreferences = getSharedPreferences(CURRENCY_SELECTION, 0);
        String csTemp = sharedPreferences.getString("Currency", "No Data");
        if (csTemp.equals("No Data")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Currency", value);
            editor.commit();
        }
    }

}
