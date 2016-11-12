package com.firecaster.saver;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.ActionBar;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;


import static com.firecaster.saver.InitialConfiguration.CURRENCY_SYMBOL;

public class Settings extends AppCompatActivity {


    RadioButton colones;
    RadioButton dollars;
    String cs;
    public static final String CURRENCY_SELECTION = "currencySelection";
    ListView settings_list;
    ListView currency_list;
    int selectedCurrency = 0;
    int restoreSelectedCurrency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        verifyCurrencyValue();

        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        settings_list = (ListView) findViewById(R.id.l_settings_accounts);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.accounts_list, android.R.layout.simple_list_item_1);
        settings_list.setAdapter(adapter);


        //lista de las cuentas
        settings_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        Intent gLO = new Intent(Settings.this, GLogOut.class);
                        startActivity(gLO);
                        break;
                }
            }
        });

        currency_list = (ListView) findViewById(R.id.l_settings_currency);
        ArrayAdapter<CharSequence> adapterCurrency = ArrayAdapter.createFromResource(this, R.array.currency_list, android.R.layout.simple_list_item_1);
        currency_list.setAdapter(adapterCurrency);


        //lista de las cuentas
        currency_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        currencyAlertDialog();
                        break;
                }
            }
        });



    }

    public void saveCurrencySelection() {
        SharedPreferences sharedPreferences = getSharedPreferences(CURRENCY_SELECTION, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Currency", CURRENCY_SYMBOL);
        editor.commit();
    }


    private void currencyAlertDialog() {

        String colones = getResources().getString(R.string.colones);
        String dollars = getResources().getString(R.string.dollars);
        final CharSequence[] items = {colones, dollars};
        restoreSelectedCurrency = getCurrency();

        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle(R.string.currency);
        builder.setSingleChoiceItems(items, restoreSelectedCurrency,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        selectedCurrency = item;
                    }
                });

        builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setCurrency(selectedCurrency);
                saveCurrencySelection();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });


        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setCurrency(int item) {
        switch (item) {
            case 0:
                CURRENCY_SYMBOL = "₡";
                break;
            case 1:
                CURRENCY_SYMBOL = "$";
                break;
            default:
                CURRENCY_SYMBOL = "₡";
                break;
        }
    }


    public int getCurrency() {
        int temp = -1;
        SharedPreferences sharedPreferences = getSharedPreferences(CURRENCY_SELECTION, 0);
        cs = sharedPreferences.getString("Currency", "No Data");
        switch (cs) {
            case "₡":
                temp = 0;
                break;

            case "$":
                temp = 1;
                break;
        }
        return temp;
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