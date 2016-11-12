package com.firecaster.saver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppInfo extends AppCompatActivity {

    Button licenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);


        licenses = (Button) findViewById(R.id.bt_licenses);


        licenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent licensesWindow = new Intent(AppInfo.this, Licenses.class);
                startActivity(licensesWindow);
            }
        });
    }


}
