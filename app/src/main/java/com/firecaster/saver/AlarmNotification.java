package com.firecaster.saver;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlarmNotification extends AppCompatActivity {

    Button yes;
    Button no;
    Button ok;
    TextView notificationEvent;
    TextView amount;
    TextView hmm;
    TextView currency;
    String cs;
    EditText value;
    String title;
    public static final String VALUES = "values";
    public static final String CURRENCY_SELECTION = "currencySelection";
    private static final String SPENT = "UserSpent";
    int notificationID;
    int breakfast = 0;
    int launch = 0;
    int dinner = 0;
    int transportation = 0;
    int internet = 0;
    int water = 0;
    int electricity = 0;
    int renting = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_notification);


        yes = (Button) findViewById(R.id.noti_yes_button);
        no = (Button) findViewById(R.id.noti_no_button);
        ok = (Button) findViewById(R.id.noti_ok);
        amount = (TextView) findViewById(R.id.noti_value);
        hmm = (TextView) findViewById(R.id.hmm);
        currency = (TextView) findViewById(R.id.noti_currency);
        notificationEvent = (TextView) findViewById(R.id.notification_event);
        value = (EditText) findViewById(R.id.et_noti_value);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeInfo();
                turnOffUI();
                finish();


            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnUI();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                different();
                turnOffUI();
                finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("SUBJECT");
        notificationID = bundle.getInt("ID");
        notificationEvent.setText(title);

        readInfo();

        readValue();

        getCurrencySelection();

        setCurrency();

        notificationDismiss();

    }

    public void different () {
        SharedPreferences sp = getSharedPreferences(SPENT, 0);
        SharedPreferences spp = getSharedPreferences(SPENT, 0);
        SharedPreferences.Editor editor = spp.edit();
        switch (title) {
            case "Breakfast time!":
                int bf = sp.getInt("breakfast", 0);
                bf = bf + Integer.parseInt(value.getText().toString());

                editor.putInt("breakfast", bf);
                editor.commit();
                break;
            case "Launch time!":
                int lc = sp.getInt("launch", 0);
                lc = lc + Integer.parseInt(value.getText().toString());

                editor.putInt("launch", lc);
                editor.commit();
                break;
            case "Dinner time!":
                int din = sp.getInt("dinner", 0);
                din = din + Integer.parseInt(value.getText().toString());

                editor.putInt("dinner", din);
                editor.commit();
                break;

            case "Transportation expenses!":
                int trans = sp.getInt("transportation", 0);
                trans = trans + Integer.parseInt(value.getText().toString());

                editor.putInt("transportation", trans);
                editor.commit();
                break;

            case "Internet service payment!":
                int inter = sp.getInt("internet", 0);
                inter = inter + Integer.parseInt(value.getText().toString());

                editor.putInt("internet", inter);
                editor.commit();
                break;

            case "Water service payment!":
                int water = sp.getInt("water", 0);
                water = water + Integer.parseInt(value.getText().toString());

                editor.putInt("water", water);
                editor.commit();
                break;

            case "Electricity service payment!":
                int elect = sp.getInt("electricity", 0);
                elect = elect + Integer.parseInt(value.getText().toString());

                editor.putInt("electricity", elect);
                editor.commit();
                break;

            case "Renting Service Payment!":
                int rent = sp.getInt("renting", 0);
                rent = rent + Integer.parseInt(value.getText().toString());

                editor.putInt("renting", rent);
                editor.commit();
                break;
            default:


        }
    }

    public void writeInfo () {
        SharedPreferences sp = getSharedPreferences(SPENT, 0);
        SharedPreferences spp = getSharedPreferences(SPENT, 0);
        SharedPreferences.Editor editor = spp.edit();
        switch (title) {
            case "Breakfast time!":
                int bf = sp.getInt("breakfast", 0);
                bf = bf + Integer.parseInt(amount.getText().toString());

                editor.putInt("breakfast", bf);
                editor.commit();
                break;
            case "Launch time!":
                int lc = sp.getInt("launch", 0);
                lc = lc + Integer.parseInt(amount.getText().toString());

                editor.putInt("launch", lc);
                editor.commit();
                break;
            case "Dinner time!":
                int din = sp.getInt("dinner", 0);
                din = din + Integer.parseInt(amount.getText().toString());

                editor.putInt("dinner", din);
                editor.commit();
                break;

            case "Transportation expenses!":
                int trans = sp.getInt("transportation", 0);
                trans = trans + Integer.parseInt(amount.getText().toString());

                editor.putInt("transportation", trans);
                editor.commit();
                break;
            default:

            case "Internet service payment!":
                int inter = sp.getInt("internet", 0);
                inter = inter + Integer.parseInt(amount.getText().toString());

                editor.putInt("internet", inter);
                editor.commit();
                break;

            case "Water service payment!":
                int water = sp.getInt("water", 0);
                water = water + Integer.parseInt(amount.getText().toString());

                editor.putInt("water", water);
                editor.commit();
                break;

            case "Electricity service payment!":
                int elect = sp.getInt("electricity", 0);
                elect = elect + Integer.parseInt(amount.getText().toString());

                editor.putInt("electricity", elect);
                editor.commit();
                break;

            case "Renting Service Payment!":
                int rent = sp.getInt("renting", 0);
                rent = rent + Integer.parseInt(amount.getText().toString());

                editor.putInt("renting", rent);
                editor.commit();
                break;


        }
    }

    public void turnOnUI() {
        ok.setVisibility(View.VISIBLE);
        hmm.setVisibility(View.VISIBLE);
        value.setVisibility(View.VISIBLE);
    }

    public void turnOffUI() {
        ok.setVisibility(View.GONE);
        hmm.setVisibility(View.GONE);
        value.setVisibility(View.GONE);
    }

    public void readInfo() {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);
        breakfast = sp.getInt("breakfast", 0);
        launch = sp.getInt("launch", 0);
        dinner = sp.getInt("dinner", 0);
        transportation = sp.getInt("transportation", 0);
        internet = sp.getInt("internet", 0);
        water = sp.getInt("water", 0);
        electricity = sp.getInt("electricity", 0);
        renting = sp.getInt("renting", 0);
    }

    public void readValue() {
        switch (title) {
            case "Breakfast time!":
                amount.setText(Integer.toString(breakfast));
                break;
            case "Launch time!":
                amount.setText(Integer.toString(launch));
                break;
            case "Dinner time!":
                amount.setText(Integer.toString(dinner));
                break;
            case "Transportation expenses!":
                amount.setText(Integer.toString(transportation));
                break;
            case "Internet service payment!":
                amount.setText(Integer.toString(internet));
                break;
            case "Water service payment!":
                amount.setText(Integer.toString(water));
                break;
            case "Electricity service payment!":
                amount.setText(Integer.toString(electricity));
                break;
            case "Renting Service Payment!":
                amount.setText(Integer.toString(renting));
                break;

            default:


        }
    }

    public void getCurrencySelection() {
        SharedPreferences sharedPreferences = getSharedPreferences(CURRENCY_SELECTION, 0);
        cs = sharedPreferences.getString("Currency", "No Data");
    }

    public void setCurrency() {
        currency.setText(cs);
    }


    public void notificationDismiss() {
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationID);
    }
}
