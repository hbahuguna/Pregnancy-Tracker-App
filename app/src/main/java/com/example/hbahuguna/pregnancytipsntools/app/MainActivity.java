package com.example.hbahuguna.pregnancytipsntools.app;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import android.content.DialogInterface;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.wdullaer.materialdatetimepicker.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    private TextView dateTextView;

    static final String KEY_IS_FIRST_TIME =  "com.pregnancytipsntools.first_time";
    static final String KEY =  "com.pregnancytipsntools";
    static final String CONCEPTION_DAY =  "day";
    static final String CONCEPTION_MONTH =  "month";
    static final String CONCEPTION_YEAR =  "year";
    BottomBar bottomBar;
    Bundle instanceState = null;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle instanceState1 = savedInstanceState;
        if(savedInstanceState != null){
            bottomBar = (BottomBar) findViewById(R.id.bottomBar);
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.frame, TabFragment.get(tabId, instanceState, getIntent()))
                            .commit();
                }
            });
        }

        instanceState = savedInstanceState;
        JodaTimeAndroid.init(this);

        if(isFirstTime()) {
            setContentView(R.layout.date_picker);
            dateTextView = (TextView)findViewById(R.id.date_textview);
            Button dateButton = (Button)findViewById(R.id.date_button);
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            MainActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.setMaxDate(now);
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }
            });
        } else {
            todayView();
        }

    }

    public  void todayView() {
        setContentView(R.layout.activity_main);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame, TabFragment.get(tabId, instanceState, getIntent()))
                        .commit();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        getSharedPreferences(KEY, Context.MODE_PRIVATE).edit().putInt(CONCEPTION_DAY, dayOfMonth).commit();
        getSharedPreferences(KEY, Context.MODE_PRIVATE).edit().putInt(CONCEPTION_MONTH, monthOfYear ).commit();
        getSharedPreferences(KEY, Context.MODE_PRIVATE).edit().putInt(CONCEPTION_YEAR, year).commit();
        setKeyIsFirstTime();
        todayView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab_id", bottomBar.getCurrentTabId());
    }

    public boolean isFirstTime(){
        return getSharedPreferences(KEY, Context.MODE_PRIVATE).getBoolean(KEY_IS_FIRST_TIME, true);
    }

    public void setKeyIsFirstTime() {
        getSharedPreferences(KEY, Context.MODE_PRIVATE).edit().putBoolean(KEY_IS_FIRST_TIME, false).commit();
    }
}
