package com.firecaster.saver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class InitialConfiguration extends AppCompatActivity {
    public static String CURRENCY_SYMBOL = "";
    public static final String CURRENCY_SELECTION = "currencySelection";
    public static final String CHECKBOX_STATES = "checkboxStates";
    public static final String VALUES = "values";
    private TextView c1, c2, c3, c4, c5, c6, c7, c8;
    private EditText txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8;
    private CheckBox breakfast, launch, dinner, trans, inter, water, elect, rent;
    Button saveButton;
    private SmallBang mSmallBang;
    private String cs;
    private boolean notEmptyValues = false;
    private ImageButton internet_timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_configuration);

        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        //libreria animaciones
        mSmallBang = SmallBang.attach2Window(this);

        saveButton = (Button) findViewById(R.id.bt_save_user_values);
        internet_timePicker = (ImageButton) findViewById(R.id.internet_picker);

        setVariables();
        verifyCurrencyValue();
        getCurrencySelection();
        setCurrency();


        setListeners();
        verifyUserValues();


        getCheckBoxStates();


        internet_timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent internetPicker = new Intent(InitialConfiguration.this, TimePickers.class);
                startActivity(internetPicker);
                internetPicker.putExtra("INTERNET", R.string.internet);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEditTextValue(txt1, breakfast);
                verifyEditTextValue(txt2, launch);
                verifyEditTextValue(txt3, dinner);
                verifyEditTextValue(txt4, trans);
                verifyEditTextValue(txt5, inter);
                verifyEditTextValue(txt6, water);
                verifyEditTextValue(txt7, elect);
                verifyEditTextValue(txt8, rent);

                if (notEmptyValues) {
                    saveUserValues();
                    saveCheckBoxStates();
                    Toast.makeText(InitialConfiguration.this, R.string.data_saved_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InitialConfiguration.this, R.string.invalid_checked_value, Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    //Inicializa las variables
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
        dinner = (CheckBox) findViewById(R.id.cb_dinner);
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

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCB(dinner, txt3);
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


    public void saveUserValues() {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("breakfast", Integer.parseInt(txt1.getText().toString()));
        editor.putInt("launch", Integer.parseInt(txt2.getText().toString()));
        editor.putInt("dinner", Integer.parseInt(txt3.getText().toString()));
        editor.putInt("transportation", Integer.parseInt(txt4.getText().toString()));
        editor.putInt("internet", Integer.parseInt(txt5.getText().toString()));
        editor.putInt("water", Integer.parseInt(txt6.getText().toString()));
        editor.putInt("electricity", Integer.parseInt(txt7.getText().toString()));
        editor.putInt("renting", Integer.parseInt(txt8.getText().toString()));

        editor.commit();
    }

    public void verifyEditTextValue(EditText et, CheckBox cb) {
        if (cb.isChecked()) {
            if ((et.getText().toString().equals("")) || (et.getText().toString().equals("0"))) {
                notEmptyValues = false;
            } else {
                notEmptyValues = true;
            }

        }


    }


    public void getUserValues() {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);
        int breakfast = sp.getInt("breakfast", 0);
        int launch = sp.getInt("launch", 0);
        int dinner = sp.getInt("dinner", 0);
        int transportation = sp.getInt("transportation", 0);
        int internet = sp.getInt("internet", 0);
        int water = sp.getInt("water", 0);
        int electricity = sp.getInt("electricity", 0);
        int renting = sp.getInt("renting", 0);

        txt1.setText(Integer.toString(breakfast));
        txt2.setText(Integer.toString(launch));
        txt3.setText(Integer.toString(dinner));
        txt4.setText(Integer.toString(transportation));
        txt5.setText(Integer.toString(internet));
        txt6.setText(Integer.toString(water));
        txt7.setText(Integer.toString(electricity));
        txt8.setText(Integer.toString(renting));

    }

    public void verifyUserValues() {
        SharedPreferences sp = getSharedPreferences(VALUES, 0);
        int breakfast = sp.getInt("breakfast", 0);
        int launch = sp.getInt("launch", 0);
        int dinner = sp.getInt("dinner", 0);
        int transportation = sp.getInt("transportation", 0);
        int internet = sp.getInt("internet", 0);
        int water = sp.getInt("water", 0);
        int electricity = sp.getInt("electricity", 0);
        int renting = sp.getInt("renting", 0);
        if ((breakfast == 0) && (launch == 0) && (dinner == 0)
                && (transportation == 0) && (internet == 0) && (water == 0) && (electricity == 0) && (renting == 0)) {

            txt1.setText(Integer.toString(breakfast));
            txt2.setText(Integer.toString(launch));
            txt3.setText(Integer.toString(dinner));
            txt4.setText(Integer.toString(transportation));
            txt5.setText(Integer.toString(internet));
            txt6.setText(Integer.toString(water));
            txt7.setText(Integer.toString(electricity));
            txt8.setText(Integer.toString(renting));

            setEditTextDisabled();
            saveUserValues();
            getUserValues();
        } else {

            getUserValues();
            verifyALLEditText();
        }
    }

    public void setEditTextDisabled() {
        txt1.setEnabled(false);
        txt2.setEnabled(false);
        txt3.setEnabled(false);
        txt4.setEnabled(false);
        txt5.setEnabled(false);
        txt6.setEnabled(false);
        txt7.setEnabled(false);
        txt8.setEnabled(false);
    }

    public void saveCheckBoxStates() {
        SharedPreferences sp = getSharedPreferences(CHECKBOX_STATES, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Breakfast", verifyCheckBox(breakfast));
        editor.putInt("Launch", verifyCheckBox(launch));
        editor.putInt("Dinner", verifyCheckBox(dinner));
        editor.putInt("Transportation", verifyCheckBox(trans));
        editor.putInt("Internet", verifyCheckBox(inter));
        editor.putInt("Water", verifyCheckBox(water));
        editor.putInt("Electricity", verifyCheckBox(elect));
        editor.putInt("Renting", verifyCheckBox(rent));
        editor.commit();
    }

    public void getCheckBoxStates() {
        SharedPreferences sp = getSharedPreferences(CHECKBOX_STATES, 0);
        int cb_breakfast = sp.getInt("Breakfast", 0);
        int cb_launch = sp.getInt("Launch", 0);
        int cb_dinner = sp.getInt("Dinner", 0);
        int cb_transportation = sp.getInt("Transportation", 0);
        int cb_internet = sp.getInt("Internet", 0);
        int cb_water = sp.getInt("Water", 0);
        int cb_electricity = sp.getInt("Electricity", 0);
        int cb_renting = sp.getInt("Renting", 0);

        setCheckboxStates(cb_breakfast, breakfast, txt1);
        setCheckboxStates(cb_launch, launch, txt2);
        setCheckboxStates(cb_dinner, dinner, txt3);
        setCheckboxStates(cb_transportation, trans, txt4);
        setCheckboxStates(cb_internet, inter, txt5);
        setCheckboxStates(cb_water, water, txt5);
        setCheckboxStates(cb_electricity, elect, txt6);
        setCheckboxStates(cb_renting, rent, txt6);
    }

    // verifica un checkbox
    public int verifyCheckBox(CheckBox cb) {
        if (cb.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    //setea los checkbox
    public void setCheckboxStates(int state, CheckBox cb, EditText editText) {
        if (state == 1) {
            if ((editText.getText().toString().equals("")) || (editText.getText().toString().equals("0"))) {
                cb.setChecked(false);
            } else {
                cb.setChecked(true);
            }
        } else {
            cb.setChecked(false);
        }
    }


    public void verifyEditText(EditText et) {
        int temp = Integer.parseInt(et.getText().toString());
        if (temp == 0) {
            et.setEnabled(false);
        }

    }

    public void verifyALLEditText() {
        verifyEditText(txt1);
        verifyEditText(txt2);
        verifyEditText(txt3);
        verifyEditText(txt4);
        verifyEditText(txt5);
        verifyEditText(txt6);
        verifyEditText(txt7);
        verifyEditText(txt8);
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
