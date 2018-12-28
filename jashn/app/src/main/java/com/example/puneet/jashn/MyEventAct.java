package com.example.puneet.jashn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MyEventAct extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    MyEventAdapter adapter;
    private String TAG = "MyEventAct";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Bundle b = this.getIntent().getExtras();
        ArrayList<MyEvent> createdEvents = b.getParcelableArrayList("createdEvents");
        adapter = new MyEventAdapter(createdEvents, getApplicationContext());
        Log.d(TAG, "onDataChange: adapter " + adapter);
        mRecyclerView.setAdapter(adapter);
    }
}
