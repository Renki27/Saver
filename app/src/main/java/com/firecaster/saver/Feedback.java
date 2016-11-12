package com.firecaster.saver;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {

    static final String KREI_EMAIL = "kreisoftwaredevelopment@gmail.com";
    Button sendEmail;
    RadioButton contact;
    RadioButton report;
    RadioButton suggestions;
    RadioButton others;
    RadioButton features;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sendEmail = (Button) findViewById(R.id.btn_send_email);
        contact = (RadioButton) findViewById(R.id.rb_contact);
        report = (RadioButton) findViewById(R.id.rb_report);
        suggestions = (RadioButton) findViewById(R.id.rb_suggestions);
        others = (RadioButton) findViewById(R.id.rb_others);
        features = (RadioButton) findViewById(R.id.rb_feature_request);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });


    }

    public void sendEmail() {

        String[] em = {KREI_EMAIL};
        String subject;

        //Definimos la tipologia de datos del contenido dle Email en este caso text/html
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, em);
        subject = radioButtonSelected();
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        try {
            //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
            startActivity(Intent.createChooser(emailIntent, "Send feedback..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.no_email_app, Toast.LENGTH_SHORT).show();
        }
    }


    //seleccion de los radioButtons
    public String radioButtonSelected() {
        String s = "No Subject";
        if (contact.isChecked()) {
            s = getResources().getString(R.string.contact);
        } else if (report.isChecked()) {
            s = getResources().getString(R.string.report_bug);
        } else if (suggestions.isChecked()) {
            s = getResources().getString(R.string.suggestions);
        } else if (others.isChecked()) {
            s = getResources().getString(R.string.other);
        } else if (features.isChecked()) {
            s = getResources().getString(R.string.feature_request);
        }
        return s;
    }
}
