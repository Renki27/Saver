package com.firecaster.saver;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class GLogOut extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks {


    private GoogleApiClient mGoogleApiClient;
    Button signOutButton, disconnectButton;
    public static final String USER_DATA_FILE = "UserGoogleDataFile";
    public static final String SCHEDULE_DATA_FILE = "UserScheduleFile";
    public static final String CURRENCY_SELECTION = "currencySelection";

    TextView accountEmail;
    private static final String TAG = Settings.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glog_out);


        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        accountEmail = (TextView) findViewById(R.id.settings_account_email);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        signOutButton = (Button) findViewById(R.id.google_sign_out_button);
        disconnectButton = (Button) findViewById(R.id.google_disconnect_account);
        signOutButton.setOnClickListener(this);
        disconnectButton.setOnClickListener(this);

        setUserEmail();


    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    //La diferencia entre el sign out y el revoke access es que el sign out cierra la cuenta pero esta queda guardada,
    //el revoke access la desconecta del dispositivo totalmente
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.google_sign_out_button:

                //Alert dialog que pide confirmacion para cerrar sesion
                new AlertDialog.Builder(this)
                        .setTitle(R.string.sign_out_title)
                        .setMessage(R.string.sign_out_confirmation)
                        .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // sign out
                                signOut();
                            }
                        })
                        .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // el usuario no desea salir
                                dialog.cancel();
                            }
                        })
                        .show();
                break;

            case R.id.google_disconnect_account:
                //Alert dialog que pide confirmacion para cerrar sesion
                new AlertDialog.Builder(this)
                        .setTitle(R.string.disconnect_title)
                        .setMessage(R.string.disconnect_confirmation)
                        .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //desconectar cuenta
                                revokeAccess();


                            }
                        })
                        .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // el usuario no desea borrar los datos
                                dialog.cancel();

                            }
                        })
                        .show();

                break;
        }
    }

    // los flags incluidos son para que no pueda volver a la pantalla anterior despues de cerrar sesion
    //si se quitan la aplicacion vuelve del login al main activity
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent login = new Intent(GLogOut.this, GLogin.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login);
                        mGoogleApiClient.disconnect();
                        finish();
                    }
                });
    }


    //para desconectar la cuenta de la app
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                      //  wipeData();
                        Intent login = new Intent(GLogOut.this, GLogin.class);
                        Log.d(TAG, "revokeAccess:onResult:" + status);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login);
                        finish();
                    }
                });
    }


    public void wipeData() {
        SharedPreferences userData = getSharedPreferences(USER_DATA_FILE, 0);
        userData.edit().clear().commit();
        SharedPreferences scheduleData = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        scheduleData.edit().clear().commit();
        SharedPreferences currencyData = getSharedPreferences(SCHEDULE_DATA_FILE, 0);
        currencyData.edit().clear().commit();

    }


    public void setUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA_FILE, 0);
        String email = sharedPreferences.getString("User_Email", "No Data");
        accountEmail.setText(email);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("", "onConnected " + bundle);
        if (bundle != null)
            Log.i("", "bundle " + bundle.size() + " " + bundle.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("", "onConnectionSuspended " + i);
    }

}
