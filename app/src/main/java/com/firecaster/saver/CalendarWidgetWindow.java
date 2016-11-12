package com.firecaster.saver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class CalendarWidgetWindow extends AppCompatActivity implements OnDateSelectedListener {

    //Las clases principales para el manejo de fechas son Calendar, MaterialCalendarView

    private MaterialCalendarView calendarWidget;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private SmallBang mSmallBang;
    private int partyDays, feedDays, scheduleGuide, pre_registration,
            ordinaryRegistrationDates, webOrdinaryRegistration, inclusionQuotas, inclusionPreRegistration,
            inclusionRegistrationDates, webInclusionRegistration, hungerGamesStart;
    ArrayList<CalendarDay> partyDaysList = new ArrayList<>();
    ArrayList<CalendarDay> feedList = new ArrayList<>();
    ArrayList<CalendarDay> scheduleGuideList = new ArrayList<>();
    ArrayList<CalendarDay> pre_registrationList = new ArrayList<>();
    ArrayList<CalendarDay> ordinaryRegistrationDatesList = new ArrayList<>();
    ArrayList<CalendarDay> webOrdinaryRegistrationList = new ArrayList<>();
    ArrayList<CalendarDay> inclusionQuotasList = new ArrayList<>();
    ArrayList<CalendarDay> inclusionPreRegistrationList = new ArrayList<>();
    ArrayList<CalendarDay> inclusionRegistrationDatesList = new ArrayList<>();
    ArrayList<CalendarDay> webInclusionRegistrationList = new ArrayList<>();
    ArrayList<CalendarDay> hungerGamesStartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_widget_window);

        //libreria animaciones
        mSmallBang = SmallBang.attach2Window(this);

        //Instancias que se encargan de agragar el action bar y el boton de regreso a las ventanas
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        calendarWidget = (MaterialCalendarView) findViewById(R.id.calendarView);


        //boton que abre ventana de los colores
        ImageButton colors = (ImageButton) findViewById(R.id.color_button);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animButton(v);
                Intent colorWindow = new Intent(CalendarWidgetWindow.this, CalendarColors.class);
                startActivity(colorWindow);

            }
        });


        //metodo para dia actual y limite de fechas
        selectCurrentDay();
        setDateLimit();

        //inicializar colores
        startColors();


        //pintar dias
        paintDays();

        //para que capte cuando se clicea otro dia
        calendarWidget.setOnDateChangedListener(this);


    }

    //metodo para obtener la fecha fecha atual y seleccionar el día
    public void selectCurrentDay() {

        Calendar today = Calendar.getInstance();
        calendarWidget.setSelectedDate(today.getTime());
    }


    //el limite minimo es a inicios del anio actual, el maximo esta 2 años despues del actual **sujeto a cambios
    public void setDateLimit() {
        Calendar min = Calendar.getInstance();
        min.set(min.get(Calendar.YEAR), Calendar.AUGUST, 1);

        Calendar max = Calendar.getInstance();
        max.set(max.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        calendarWidget.state().edit()
                .setMinimumDate(min.getTime())
                .setMaximumDate(max.getTime())
                .commit();
    }


    //metodo que obtiene la fecha seleccionada y la pasa a un string
    private String getSelectedDatesString() {
        CalendarDay date = calendarWidget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }


    //menu de notificaciones arriba del calendario
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }


    //opciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //metodo que hace una accion al clickear un dia
    //en este caso muestra un toast con la fecha
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        String dateEvent = getResources().getString(R.string.no_event);
        for (int i = 0; i < partyDaysList.size(); i++) {
            if (widget.getSelectedDate().equals(partyDaysList.get(i))) {
                dateEvent = getResources().getString(R.string.economic_benefit) + "\n" + getSelectedDatesString();
            }
        }
        for (int i = 0; i < feedList.size(); i++) {
            if (widget.getSelectedDate().equals(feedList.get(i))) {
                dateEvent = getResources().getString(R.string.feeding_benefit) + "\n" + getSelectedDatesString();
            }
        }
        for (int i = 0; i < scheduleGuideList.size(); i++) {
            if (widget.getSelectedDate().equals(scheduleGuideList.get(i))) {
                dateEvent = getResources().getString(R.string.schedule_guide) + "\n" + getSelectedDatesString();
            }
        }
        for (int i = 0; i < pre_registrationList.size(); i++) {
            if (widget.getSelectedDate().equals(pre_registrationList.get(i))) {
                dateEvent = getResources().getString(R.string.preregistration) + "\n" + getSelectedDatesString();
            }
        }
        for (int i = 0; i < ordinaryRegistrationDatesList.size(); i++) {
            if (widget.getSelectedDate().equals(ordinaryRegistrationDatesList.get(i))) {
                dateEvent = getResources().getString(R.string.ordinary_registration_dates) + "\n" + getSelectedDatesString();
            }
        }

        for (int i = 0; i < webOrdinaryRegistrationList.size(); i++) {
            if (widget.getSelectedDate().equals(webOrdinaryRegistrationList.get(i))) {
                dateEvent = getResources().getString(R.string.ordinary_registration) + "\n" + getSelectedDatesString();
            }
        }

        for (int i = 0; i < inclusionQuotasList.size(); i++) {
            if (widget.getSelectedDate().equals(inclusionQuotasList.get(i))) {
                dateEvent = getResources().getString(R.string.inclusion_quotas) + "\n" + getSelectedDatesString();
            }
        }

        for (int i = 0; i < inclusionPreRegistrationList.size(); i++) {
            if (widget.getSelectedDate().equals(inclusionPreRegistrationList.get(i))) {
                dateEvent = getResources().getString(R.string.inclusion_pre_registration) + "\n" + getSelectedDatesString();
            }
        }

        for (int i = 0; i < inclusionRegistrationDatesList.size(); i++) {
            if (widget.getSelectedDate().equals(inclusionRegistrationDatesList.get(i))) {
                dateEvent = getResources().getString(R.string.inclusion_registration_dates) + "\n" + getSelectedDatesString();
            }
        }

        for (int i = 0; i < webInclusionRegistrationList.size(); i++) {
            if (widget.getSelectedDate().equals(webInclusionRegistrationList.get(i))) {
                dateEvent = getResources().getString(R.string.inclusion_registration) + "\n" + getSelectedDatesString();
            }
        }
        for (int i = 0; i < hungerGamesStartList.size(); i++) {
            if (widget.getSelectedDate().equals(hungerGamesStartList.get(i))) {
                dateEvent = getResources().getString(R.string.hunger_games_start) + "\n" + getSelectedDatesString();
            }
        }

        Toast swipeToast = Toast.makeText(this, dateEvent, Toast.LENGTH_LONG);
        swipeToast.show();
    }


    // Los meses en calendar empiezan desde 0
    // metodo para setear las fechas del semestre
    public void paintDays() {


        partyDaysList.add(setDaysEvent(2016, 8, 11));
        partyDaysList.add(setDaysEvent(2016, 9, 6));
        partyDaysList.add(setDaysEvent(2016, 10, 13));
        partyDaysList.add(setDaysEvent(2016, 11, 11));


        feedList.add(setDaysEvent(2016, 8, 10));
        feedList.add(setDaysEvent(2016, 9, 5));
        feedList.add(setDaysEvent(2016, 10, 11));
        feedList.add(setDaysEvent(2016, 11, 10));


        scheduleGuideList.add(setDaysEvent(2016, 8, 16));

        pre_registrationList.add(setDaysEvent(2016, 8, 22));
        pre_registrationList.add(setDaysEvent(2016, 8, 23));


        ordinaryRegistrationDatesList.add(setDaysEvent(2016, 8, 24));

        webOrdinaryRegistrationList.add(setDaysEvent(2016, 8, 25));
        webOrdinaryRegistrationList.add(setDaysEvent(2016, 8, 26));

        inclusionQuotasList.add(setDaysEvent(2016, 8, 29));

        inclusionPreRegistrationList.add(setDaysEvent(2016, 8, 29));
        inclusionPreRegistrationList.add(setDaysEvent(2016, 8, 30));

        inclusionRegistrationDatesList.add(setDaysEvent(2016, 8, 31));

        webInclusionRegistrationList.add(setDaysEvent(2016, 9, 1));
        webInclusionRegistrationList.add(setDaysEvent(2016, 9, 2));


        hungerGamesStartList.add(setDaysEvent(2016, 8, 29));


        calendarWidget.addDecorators(new EventDecorator(partyDays, partyDaysList));
        calendarWidget.addDecorators(new EventDecorator(feedDays, feedList));
        calendarWidget.addDecorators(new EventDecorator(scheduleGuide, scheduleGuideList));
        calendarWidget.addDecorators(new EventDecorator(pre_registration, pre_registrationList));
        calendarWidget.addDecorators(new EventDecorator(ordinaryRegistrationDates, ordinaryRegistrationDatesList));
        calendarWidget.addDecorators(new EventDecorator(webOrdinaryRegistration, webOrdinaryRegistrationList));
        calendarWidget.addDecorators(new EventDecorator(inclusionQuotas, inclusionQuotasList));
        calendarWidget.addDecorators(new EventDecorator(inclusionPreRegistration, inclusionPreRegistrationList));
        calendarWidget.addDecorators(new EventDecorator(inclusionRegistrationDates, inclusionRegistrationDatesList));
        calendarWidget.addDecorators(new EventDecorator(webInclusionRegistration, webInclusionRegistrationList));
        calendarWidget.addDecorators(new EventDecorator(hungerGamesStart, hungerGamesStartList));
    }


    public CalendarDay setDaysEvent(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        CalendarDay calendarDay = CalendarDay.from(calendar);
        return calendarDay;

    }


    public void startColors() {
        partyDays = getResources().getColor(R.color.red);
        feedDays = getResources().getColor(R.color.green);
        scheduleGuide = getResources().getColor(R.color.blue);
        pre_registration = getResources().getColor(R.color.pink);
        ordinaryRegistrationDates = getResources().getColor(R.color.orange);
        webOrdinaryRegistration = getResources().getColor(R.color.purple);
        inclusionQuotas = getResources().getColor(R.color.yellow);
        inclusionPreRegistration = getResources().getColor(R.color.violet);
        inclusionRegistrationDates = getResources().getColor(R.color.tomato);
        webInclusionRegistration = getResources().getColor(R.color.indigo);
        hungerGamesStart = getResources().getColor(R.color.cyan);
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




