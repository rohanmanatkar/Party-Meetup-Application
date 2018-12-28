package com.example.puneet.jashn;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    ScrollView mLayout;
    int mDefaultColor;
    int eventColor;
    private EditText eventName,eventLocation,minAge, entryFee, eventDescription,eventGuest;
    private Button createEvent, colorButton;
    Spinner selectCategory;
    private static final String TAG = "EventActivity";
    String category;
    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference eventRef;
    String userID;
    int counterColor = 0;

    //Date Time Picker
    Button eventTime,eventDate;
    int day, month, year, hour,minute;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;

    //nav
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_create);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String email = extras.getString("EMAIL");
//        Toast.makeText(this,"Profile for " + email,Toast.LENGTH_LONG).show();
        mLayout = (ScrollView) findViewById(R.id.layoutEventCreate);
        mDefaultColor = ContextCompat.getColor(EventActivity.this,R.color.colorPrimary);
        colorButton = (Button) findViewById(R.id.editTextColorChooser);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
//        userID = user.getUid();

        //nav-drawer

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_event_create);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent i;
                switch (id) {
                    case R.id.menuEventSearch :
                        item.setChecked(true);
                        i = new Intent(EventActivity.this, SearchEventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MyEventActivity.this, "Search Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuEventHost :
                        item.setChecked(true);
                        //i = new Intent(EventActivity.this, EventActivity.class);
                        //startActivity(i);
                        //Toast.makeText(MainActivity.this, "Host Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
//                    case R.id.menuMyEvents :
//                        item.setChecked(true);
//                        i = new Intent(EventActivity.this, MyEventActivity.class);
//                        startActivity(i);
//                        //Toast.makeText(MainActivity.this, "My Events", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawers();
//                        return true;
                    case R.id.menuLogout :
                        item.setChecked(true);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(EventActivity.this, MainActivity.class);
                        startActivity(i);
                        //Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    default:
                        return true;
                }
            }
        });
        //nav-drawer-end

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterColor++;
                openColorPicker();
                colorButton.setText("");

            }
        });


//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
        eventName = findViewById(R.id.editTextEventName);
        eventDate = findViewById(R.id.editTextEventDate);
        eventLocation = findViewById(R.id.editTextEventLocation);
        eventTime = findViewById(R.id.editTextEventTime);
        minAge = findViewById(R.id.editTextMinAge);
        entryFee = findViewById(R.id.editTextEntryFee);
        eventDescription = findViewById(R.id.editTextEventDesc);
        eventGuest = findViewById(R.id.editTextGuests);
        selectCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.event_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategory.setAdapter(adapter);
        selectCategory.setOnItemSelectedListener(this);
        eventRef = FirebaseDatabase.getInstance().getReference();
        createEvent = findViewById(R.id.submitCreateEventForm);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        userID = "-LR4da1Z9yMvg7q9plap";





        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = eventRef.push().getKey();
                String name = eventName.getText().toString();
                String date = eventDate.getText().toString();
                String time = eventTime.getText().toString();
                String loc = eventLocation.getText().toString();
                String age = minAge.getText().toString();
                String fee = entryFee.getText().toString();
                String desc = eventDescription.getText().toString();
                String guest = eventGuest.getText().toString();

                if(name.length()>0 && date.length()>0 && time.length()>0 && loc.length()>0 && desc.length()>0){

                    Event event = new Event(id,name,date,time,loc,category,age,fee,eventColor,userID.toString(),desc,guest,userID);
                    Toast.makeText(EventActivity.this, "Event Created Successfully", Toast.LENGTH_SHORT).show();

                    eventRef.child("events").child(id).setValue(event);
                    Log.d(TAG, "onClick: here" + event.getCreatedBy());
//                    Profile profile = new Profile();
//                    Map<String,String> hash = new HashMap<>();
//                    hash.put(event.getEventID(),event.getEventID());
//                    profile.setCrtevnt(hash);
//                    eventRef.child("profiles").child(userID).setValue(profile);
                    eventRef.child("profiles").child(userID).child("createdEvent").push().setValue(id); //get Event ID karni hai


                    startActivity(new Intent(EventActivity.this,SearchEventActivity.class));
                    eventName.setText(" ");
                    eventLocation.setText("");
                    minAge.setText(" ");
                    entryFee.setText(" ");
                    eventDescription.setText(" ");
                    eventGuest.setText(" ");
                    eventDate.setText("");
                    eventTime.setText("");
                    colorButton.setText("");

                }
                else{
                    Toast.makeText(EventActivity.this, "Please Fill in All the Details", Toast.LENGTH_SHORT).show();
                }


            }
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog =  new TimePickerDialog(EventActivity.this,EventActivity.this,hour,minute, android.text.format.DateFormat.is24HourFormat(EventActivity.this));
                timePickerDialog.show();
            }
        });

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventActivity.this,EventActivity.this,year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                eventColor = mDefaultColor;
                if(counterColor<=1)
                    colorButton.setBackgroundColor(mDefaultColor);
                else
                    colorButton.setBackgroundColor(eventColor);
            }
        });
        colorPicker.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        yearFinal = year;
        monthFinal = month+1;
        dayFinal = dayOfMonth;
        eventDate.setText(monthFinal+"/"+dayFinal+"/"+yearFinal);



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int min) {

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

//        TimePickerDialog timePickerDialog =  new TimePickerDialog(EventActivity.this,EventActivity.this,hour,minute, android.text.format.DateFormat.is24HourFormat(this));
//        timePickerDialog.show();
        hourFinal = hourOfDay;
        minuteFinal=min;
        eventTime.setText(hourFinal+":"+minuteFinal);

    }
}
