package com.firecaster.saver;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

public class InitialConfiguration extends AppCompatActivity {
    public static String CURRENCY_SYMBOL = "";
    public static final String CURRENCY_SELECTION = "currencySelection";
    private TextView c1, c2, c3, c4;
    private String cs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_configuration);


        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        c1 = (TextView) findViewById(R.id.tv_c1);
        c2 = (TextView) findViewById(R.id.tv_c2);
        c3 = (TextView) findViewById(R.id.tv_c3);
        c4 = (TextView) findViewById(R.id.tv_c4);


        verifyCurrencyValue();
        getCurrencySelection();
        setCurrency();

    }

    //setea el valor de la moneda
    public void setCurrency() {
        c1.setText(cs);
        c2.setText(cs);
        c3.setText(cs);
        c4.setText(cs);


    }

    public void getCurrencySelection() {
        SharedPreferences sharedPreferences = getSharedPreferences(CURRENCY_SELECTION, 0);
        cs = sharedPreferences.getString("Currency", "No Data");
    }


    public void verifyCurrencyValue() {
        String value = "₡";
        SharedPreferences sharedPreferences = getSharedPreferences(CURRENCY_SELECTION, 0);
        String csTemp = sharedPreferences.getString("Currency", "No Data");
        if(csTemp.equals("No Data")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Currency", value);
            editor.commit();
        }
    }

}
