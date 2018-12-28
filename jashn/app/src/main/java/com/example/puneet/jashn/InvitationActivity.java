package com.example.puneet.jashn;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvitationActivity extends AppCompatActivity {

    private String date;
    private String time;
    private String venue;
    private String guests;
    private String description;
    private int color;
    private String colorHexString;
    private String userName;
    TextView eDate, eTime, eVenue, eGuests, eDescription,eMinAge,eFees;
    TextView eDateVal, eTimeVal, eVenueVal, eGuestsVal, eThemeVal,eMinAgeVal,eFeesVal;
    Button bAccept,bDecline;
    DatabaseReference mFirebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference ref,ref2,refAccept;
    FirebaseAuth.AuthStateListener mAuthListener;
    final String TAG = "InvitationActivity";
    String eventID;
    int flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        eDate = findViewById(R.id.dateView);
        eTime = findViewById(R.id.timeView);
        eVenue = findViewById(R.id.venueView);
        eGuests = findViewById(R.id.guestsView);
        eDescription = findViewById(R.id.descView);
        eMinAge = findViewById(R.id.minAgeView);
        eFees = findViewById(R.id.feesView);
        bAccept = findViewById(R.id.acceptButton);
        bAccept.setVisibility(View.GONE);
        bDecline = findViewById(R.id.declineButton);
        eDateVal = findViewById(R.id.dateValue);
        eTimeVal = findViewById(R.id.timeValue);
        eVenueVal = findViewById(R.id.venueValue);
        eGuestsVal = findViewById(R.id.guestsValue);
        eThemeVal = findViewById(R.id.themeValue);
        eMinAgeVal = findViewById(R.id.minAgeViewValue);
        eFeesVal = findViewById(R.id.feesViewValue);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        ref = mFirebaseDatabase.child("events");

        Bundle b = this.getIntent().getExtras();
        final ArrayList<Event> myList = b.getParcelableArrayList("categoryList");
        Invitation invite = new Invitation();
        invite.setDate(myList.get(0).getDate());
        Log.d(TAG, "onCreate: getDate " + myList.get(0).getDate());
        invite.setDescription(myList.get(0).getDescription());
        invite.setGuests(myList.get(0).getGuests());
        invite.setTime(myList.get(0).getTime());
        invite.setMinAge(myList.get(0).getMinAge());
        invite.setFees(myList.get(0).getEntryFee());
        invite.setColor(myList.get(0).getColor());
        /*color = myList.get(0).getColor();
        invite.setColor(color);*/

        /*colorHexString = String.format("#%06X", (0xFFFFFF & color));

        */


        eventID = myList.get(0).getEventID();
        eDateVal.setText(myList.get(0).getDate());
        //Log.d(TAG, "onDataChange: dfssdf" + event.getDate());
        eTimeVal.setText(myList.get(0).getTime());
        color = myList.get(0).getColor();
        colorHexString = String.format("#%06X", (0xFFFFFF & color));
        Log.d(TAG, "color value retrived: "  + colorHexString);
        eThemeVal.setText(colorHexString);
        RelativeLayout invBgLayout = (RelativeLayout) findViewById(R.id.InvitationLayoutBG);
        invBgLayout.setBackgroundColor(Color.parseColor(colorHexString));
        //eThemeVal.setText(myList.get(0).getColor());
        eVenueVal.setText(myList.get(0).getLocation());
        eGuestsVal.setText(myList.get(0).getGuests());
        eDescription.setText(myList.get(0).getDescription());
        try {
            eMinAgeVal.setText(myList.get(0).getMinAge());
            eFeesVal.setText(myList.get(0).getEntryFee());
        }
        catch (DatabaseException databaseException) {
            eMinAgeVal.setText(0);
            eFeesVal.setText(0);
        }




        Log.d(TAG, "onCreate: mylist " + myList.size());
        Log.d(TAG, "onCreate: mylist " + myList.get(0).getDate());
        //Log.d(TAG, "onCreate: myList2 " + myList.get(myList.size()-1));
//          eventID = getIntent().getStringExtra("event_id");
        Log.d(TAG, "onCreate: eventID "+eventID);

        ref2 = mFirebaseDatabase.child("profiles");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String pid = ds.child("profileID").getValue(String.class);
                    Log.d(TAG, "onDataChange: profile oiter" + pid);
                    if(userID.equals(pid)) {
                        //Log.d(TAG, "onDataChange: eventID" + ds.getKey());
                        userName = ds.child("fName").getValue(String.class);
                        Log.d(TAG, "onDataChange: userName" + userName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refAccept = mFirebaseDatabase.child("profiles/"+userID+"/acceptedEvent");
        refAccept.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    String eid = ds1.getValue(String.class);
                    Log.d(TAG, "onDataChange: eid" + eid);
                    Log.d(TAG, "onDataChange: eventid" + eid);
                    if(eventID.equals(eid)){
                        flag=1;
                    }
                }
                if(flag==0){
                    bAccept.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(!invite.getAccept()){
            bAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Invitation invite = new Invitation();
                    Event event = new Event();
                    invite.setAccept(true);
                    ref2.child(userID).child("acceptedEvent").push().setValue(eventID);//getEventID karni hai
                    Toast.makeText(InvitationActivity.this, "Event Accepted", Toast.LENGTH_SHORT).show();
                    bAccept.setVisibility(View.GONE);

                }
            });
        }else {
            bAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Event Already Accepted",Toast.LENGTH_SHORT).show();
                    bAccept.setVisibility(View.GONE);

                }
            });
        }

        bDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InvitationActivity.this,EventLandingActivity.class);
                intent.putExtra("Event_ID", eventID);
                intent.putExtra("UserName", userName);
                //intent.putExtra("colorString", colorHexString);
                intent.putExtra("colorString", "#555");
                startActivity(intent);
            }
        });

    }

}