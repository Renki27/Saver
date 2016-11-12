package com.firecaster.saver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExternalLinks extends AppCompatActivity {

    ListView externalLinksListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_links);


        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //se crea un objeto listview y un adaptador para la lista
        externalLinksListView = (ListView) findViewById(R.id.list_external_links);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.external_links_array, android.R.layout.simple_list_item_1);
        externalLinksListView.setAdapter(adapter);



        //lista de los lnks externos, crea un listener para cuando se clickee un objeto de la lista,
        //segun el indice que se clickee el switch ejecuta un intent que abre el navegador con una direccion web
        externalLinksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        Intent openSite1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://becas.ucr.ac.cr/"));
                        startActivity(openSite1);
                        break;

                    case 1:
                        Intent openSite2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.oaf.ucr.ac.cr/"));
                        startActivity(openSite2);
                        break;

                    case 2:
                        Intent openSite3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ematricula.ucr.ac.cr/ematricula/"));
                        startActivity(openSite3);
                        break;

                    case 3:
                        Intent openSite4 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ori.ucr.ac.cr/sitio/"));
                        startActivity(openSite4);
                        break;

                }
            }
        });


    }
}
