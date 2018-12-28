package com.example.puneet.jashn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyEventActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference eventRef,ref,refAccept,refCreate;
    TextView eDate,eTime,eTitle,eCategory,eHost;
    MyEventAdapter adapter;
    ArrayList<MyEvent> createdEvents;
    String userID;
    private static final String TAG = "MyEventActivity";
    List<String> usercreatedevents =new ArrayList<String>();
    List <String> useracceptedevents  =new ArrayList<String>();
    ArrayList<MyEvent> MyEvents;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    DatabaseReference mFirebaseDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    public String temp = null;
    public String checkEventID= null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Intent intent = getIntent();
        //nav-drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_home_activity);
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
                        i = new Intent(MyEventActivity.this, SearchEventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MyEventActivity.this, "Search Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuEventHost :
                        item.setChecked(true);
                        i = new Intent(MyEventActivity.this, EventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MainActivity.this, "Host Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
//                    case R.id.menuMyEvents :
//                        item.setChecked(true);
//                        //i = new Intent(MyEventActivity.this, MyEventActivity.class);
//                        //startActivity(i);
//                        //Toast.makeText(MainActivity.this, "My Events", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawers();
//                        return true;
                    case R.id.menuLogout :
                        item.setChecked(true);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(MyEventActivity.this, MainActivity.class);
                        startActivity(i);
                        //Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    default:
                        return true;
                }
            }
        });


        Toast.makeText(MyEventActivity.this, "myevent", Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        //eventRef = FirebaseDatabase.getInstance().getReference();
        ref = mFirebaseDatabase.child("profiles");
        userID = user.getUid();

        //Created Events
        refCreate = mFirebaseDatabase.child("profiles/"+userID+"/createdEvent");
        createdEvents = new ArrayList<>();
        refCreate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    String eid = ds1.getValue(String.class);
                    Log.d(TAG, "Profile: user" + userID);
                    //Log.d(TAG, "onDataChange: eventid" + eid);
                    usercreatedevents.add(eid);
                    Log.d(TAG, "Arraylist populated " +usercreatedevents.size());
                }
                eventRef = mFirebaseDatabase.child("events");
                Log.d(TAG, "Events database "+ usercreatedevents.size() );
                for(int i=0;i<usercreatedevents.size();i++){
                    Log.d(TAG, "Looping Through Arraylist " + usercreatedevents.get(i));
                    temp = usercreatedevents.get(i);
                    eventRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {

                                String eid = ds1.child("eventID").getValue(String.class);
                                if(checkEventID==null || checkEventID!= eid) {
                                    if (temp.equals(eid)) {
                                        checkEventID = eid;
                                        MyEvent myevent = new MyEvent();
                                        myevent.setDescription(ds1.child("description").getValue(String.class));
                                        myevent.setDate(ds1.child("date").getValue(String.class));
                                        myevent.setTitle(ds1.child("eventName").getValue(String.class));
                                        myevent.setFees(ds1.child("entryFee").getValue(String.class));
                                        myevent.setEventID(ds1.child("eventID").getValue(String.class));
                                        createdEvents.add(myevent);
                                        Log.d(TAG, "onDataChange: createdEvents " + createdEvents.get(0).getEventID());
                                        Log.d(TAG, "onDataChange: createdEvents " + createdEvents.get(0).getTitle());
                                        Log.d(TAG, "onDataChange: createdEvents " + createdEvents.size());
                                        Log.d(TAG, "onDataChange: eid" + eid);
                                        Log.d(TAG, "pratik : heritage" + eid);
                                        adapter = new MyEventAdapter(createdEvents, getApplicationContext());
                                        Log.d(TAG, "onDataChange: adapter " + adapter);
                                        mRecyclerView.setAdapter(adapter);
                                    }
                                }
                                else{
                                    continue;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Accepted Events
        refAccept = mFirebaseDatabase.child("profiles/"+userID+"/acceptedEvent");
        createdEvents = new ArrayList<>();
        refAccept.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    String eid = ds1.getValue(String.class);
                    Log.d(TAG, "Profile: user" + userID);
                    //Log.d(TAG, "onDataChange: eventid" + eid);
                    useracceptedevents.add(eid);
                    Log.d(TAG, "Arraylist populated " +useracceptedevents.size());
                }
                eventRef = mFirebaseDatabase.child("events");
                Log.d(TAG, "Events database "+ useracceptedevents.size() );
                for(int i=0;i<useracceptedevents.size();i++){
                    Log.d(TAG, "Looping Through Arraylist " + useracceptedevents.get(i));
                    temp = useracceptedevents.get(i);
                    eventRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {

                                String eid = ds1.child("eventID").getValue(String.class);
                                if(checkEventID==null || checkEventID!= eid) {
                                    if (temp.equals(eid)) {
                                        checkEventID = eid;
                                        MyEvent myevent = new MyEvent();
                                        myevent.setDescription(ds1.child("description").getValue(String.class));
                                        myevent.setDate(ds1.child("date").getValue(String.class));
                                        myevent.setTitle(ds1.child("eventName").getValue(String.class));
                                        myevent.setFees(ds1.child("entryFee").getValue(String.class));
                                        myevent.setEventID(ds1.child("eventID").getValue(String.class));
                                        createdEvents.add(myevent);
                                        Log.d(TAG, "onDataChange: createdEvents " + createdEvents.get(0).getEventID());
                                        Log.d(TAG, "onDataChange: createdEvents " + createdEvents.get(0).getTitle());
                                        Log.d(TAG, "onDataChange: createdEvents " + createdEvents.size());
                                        Log.d(TAG, "onDataChange: eid" + eid);
                                        Log.d(TAG, "pratik : heritage" + eid);
                                        adapter = new MyEventAdapter(createdEvents, getApplicationContext());
                                        Log.d(TAG, "onDataChange: adapter " + adapter);
                                        mRecyclerView.setAdapter(adapter);
                                    }
                                }
                                else{
                                    continue;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }



//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user!=null) {
//                    Log.d(TAG, "onAuthStateChanged: Signedin "+user.getUid());
////                    Toast.makeText(SearchEventActivity.this, "Su", Toast.LENGTH_SHORT).show();
//                }else {
//                    Log.d(TAG, "onAuthStateChanged: Signed out");
//                }
//            }
//        };

//        //useracceptedevents = new ArrayList<String>();
//        useracceptedevents = new ArrayList<String>();
//        MyEvents = new ArrayList<MyEvent>();
//        eventRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Profile profile = dataSnapshot.getValue(Profile.class);
//                for(DataSnapshot ds:dataSnapshot.getChildren()){
//                    String user = ds.child("profiles").getValue(Profile.class).getProfileID();
//                    if(user.equals(userID)) {
//                        String acceptevents = eventRef.child("profiles").child(userID).child("acceptedEvent").getKey();
//                        String createdevents = eventRef.child("profiles").child(userID).child("createdEvent").getKey();
//                        useracceptedevents.add(acceptevents);
//                        usercreatedevents.add(createdevents);
//                    }
//                }
//
//                    MyEvent myEvent = new MyEvent();
//                    Event event = dataSnapshot.getValue(Event.class);
//                for (DataSnapshot ds:dataSnapshot.getChildren()){
//                    for (String events :useracceptedevents){
//                        String eveID = ds.child("events").getValue(Event.class).getEventID();
//                        if (eveID.equals(events)){
//                            myEvent.setDate(ds.child("events").getValue(Event.class).getDate());
//                            myEvent.setTitle(ds.child("events").getValue(Event.class).getEventName());
//                            myEvent.setDescription(ds.child("events").getValue(Event.class).getDescription());
//                            Log.d(TAG, "onDataChange: title " + ds.child("events").getValue(Event.class).getEventName());
//                            MyEvents.add(myEvent);
//                        }
//                    }
//                    for (String events :usercreatedevents){
//                        String eveID = ds.child("events").getValue(Event.class).getEventID();
//                        if (eveID.equals(events)){
//                            myEvent.setDate(ds.child("events").getValue(Event.class).getDate());
//                            myEvent.setTitle(ds.child("events").getValue(Event.class).getEventName());
//                            myEvent.setDescription(ds.child("events").getValue(Event.class).getDescription());
//                            Log.d(TAG, "onDataChange: title " + ds.child("events").getValue(Event.class).getEventName());
//                            MyEvents.add(myEvent);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }


}
