package com.firecaster.saver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;


public class GLogin extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = GLogin.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private boolean firsTime = true;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private SmallBang mSmallBang;
    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView userName;
    private TextView userEmail;
    private String name;
    private String email;
    private String picture;
    public static final String USER_DATA_FILE = "UserGoogleDataFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glogin);

        //libreria animaciones
        mSmallBang = SmallBang.attach2Window(this);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);


        btnSignIn.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic profile. ID and
        // basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_WIDE);
        btnSignIn.setScopes(gso.getScopeArray());
    }


    //cuando el boton de inicio es clickeado, inicia el signin intent
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                animButton(v);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // si se loguea, obtinene los datos
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personPhotoUrl;

            //para que verifique si el usuario tiene foto de perfil, sino poner una por defecto y avisarle al usuario
            if (acct.getPhotoUrl() != null) {
                personPhotoUrl = acct.getPhotoUrl().toString();
            } else {
                personPhotoUrl = "android.resource://com.firecaster.saver/" + R.drawable.ic_default_user;

                Toast.makeText(this, R.string.google_photo_alert, Toast.LENGTH_LONG).show();


            }

            name = personName;
            email = personEmail;
            picture = personPhotoUrl;

            Log.e(TAG, "Name: " + personName + ", email: " + personEmail
                    + ", Image: " + personPhotoUrl);


            setConnection(true);
        }
        else {
            if (!firsTime)  {
                setConnection(false);
            }
            firsTime = false;

        }


    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
            mGoogleApiClient.connect();
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
          showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void setConnection(boolean isSignedIn) {
        if (isSignedIn) {
            firsTime = false;
            saveUserData();
            finish();
            Intent main = new Intent(GLogin.this, MainActivity.class);
            startActivity(main);

        } else {
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);

    }


    // para guardar los datos de la cuenta en el almacenamiento del telefono
    public void saveUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA_FILE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User_Name", name);
        editor.putString("User_Email", email);
        editor.putString("User_Picture_URL", picture);
        editor.commit();
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


    //Metodo animacion
    public void animButton(View view) {
        mSmallBang.bang(view, new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }
        });
    }

}

