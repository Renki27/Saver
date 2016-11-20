package com.firecaster.saver;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AboutApp extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button backButton;
    ListView aboutAppListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);


        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        //se crea un objeto listview y un adaptador para la lista
        aboutAppListView = (ListView) findViewById(R.id.list_about_app);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.aboutList, android.R.layout.simple_list_item_1);
        aboutAppListView.setAdapter(adapter);


        aboutAppListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        Intent info = new Intent(AboutApp.this, AppInfo.class);
                        startActivity(info);
                        break;
                    case 1:
                        Intent FAQ =  new Intent(AboutApp.this, Faq.class);
                        startActivity(FAQ);
                        break;

                    case 2:
                        Intent contact = new Intent(AboutApp.this, Feedback.class);
                        startActivity(contact);
                        break;

                    case 3:
                        Intent changelog = new Intent(AboutApp.this, Changelog.class);
                        startActivity(changelog);
                        break;
                }
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
