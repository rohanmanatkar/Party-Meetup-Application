package com.example.puneet.jashn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SearchEventActivity extends AppCompatActivity implements Serializable {
    DatabaseReference eventRef;
    final String TAG = "SearchActivity";
    CardView cTech, cSports, cBusiness, cParty, cMovie;
    ArrayList<Category> data;
    ArrayList<String> data1;
    ArrayList<String> data2;
    ArrayList<String> data3;

    String userID;
    DatabaseReference mFirebaseDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //nav
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onClick: Starting Activity Search Event on Create");
        setContentView(R.layout.activity_search_event);

        cTech = findViewById(R.id.cardViewTech);
        cSports = findViewById(R.id.cardViewSports);
        cBusiness = findViewById(R.id.cardViewBusiness);
        cParty = findViewById(R.id.cardViewParty);
        cMovie = findViewById(R.id.cardViewMovie);

        //nav-drawer

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_event_search);
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
//                        i = new Intent(SearchEventActivity.this, SearchEventActivity.class);
//                        startActivity(i);
                        //Toast.makeText(MyEventActivity.this, "Search Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuEventHost :
                        item.setChecked(true);
                        i = new Intent(SearchEventActivity.this, EventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MainActivity.this, "Host Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
//                    case R.id.menuMyEvents :
//                        item.setChecked(true);
//                        i = new Intent(SearchEventActivity.this, MyEventActivity.class);
//                        startActivity(i);
//                        //Toast.makeText(MainActivity.this, "My Events", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawers();
//                        return true;
                    case R.id.menuLogout :
                        item.setChecked(true);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(SearchEventActivity .this, MainActivity.class);
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

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        eventRef = mFirebaseDatabase.child("events");
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged: Signedin " + user.getUid());
//                    Toast.makeText(SearchEventActivity.this, "Su", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };


        cTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Tech");
                onCardClick("Tech");
            }
        });

        cSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClick("Sports");
            }
        });

        cBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Business");
                onCardClick("Business");
            }
        });

        cParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClick("Party");
                }
            });


        cMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Movie");
                onCardClick("Movie");
            }
        });
    }

    public void onCardClick(final String choice){
        Log.d(TAG, "onDataChange: in on card clicked");
        data = new ArrayList<Category>();
        data1 = new ArrayList<String>();
        data2 = new ArrayList<String>();
        data3 = new ArrayList<String>();


        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Event event = dataSnapshot.getValue(Event.class);
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Category category = new Category();
                    Log.d(TAG, "onDataChange: ds children + " + ds.child("category"));

                    String cat = ds.child("category").getValue(String.class);
                    Log.d(TAG, "onDataChange: category" + cat + " here " +choice);
                    if (cat.equals(choice)){
                    category.setDate(ds.child("date").getValue(String.class));
                    category.setEventName(ds.child("eventName").getValue(String.class));
                    category.setEventDetail(ds.child("description").getValue(String.class));
                        category.setEventID(ds.child("eventID").getValue(String.class));
                        category.setFees(ds.child("entryFee").getValue(String.class));
                        category.setTime(ds.child("time").getValue(String.class));
                        category.setVenue(ds.child("location").getValue(String.class));
                        category.setGuests(ds.child("guests").getValue(String.class));
                        category.setMinAge(ds.child("minAge").getValue(String.class));
                        category.setColor(ds.child("color").getValue(Integer.class));
//                        Log.d(TAG, "onDataChange: searchactivi" + ds.child("events").getValue(Event.class).getEventName());
                        data.add(category);
                        Log.d(TAG, "onDataChange: finL DATA" + ds.child("date").toString() + ' ' + cat + ' ' + ds.child("eventName").toString());
                    }

                }

                Log.d(TAG, "onDataChange: gsjfgsd" + data);

                if (data.size()==0){
                    Toast.makeText(SearchEventActivity.this, "No results for selected category", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d(TAG, "onDataChange: choice" + choice);
                    Intent intent = new Intent(SearchEventActivity.this,CategoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("categoryList",data);
                    intent.putExtras(bundle);

//                    startActivity(new Intent(SearchEventActivity.this,CategoryActivity.class));
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: cancelled");
            }
        });
    }

}
