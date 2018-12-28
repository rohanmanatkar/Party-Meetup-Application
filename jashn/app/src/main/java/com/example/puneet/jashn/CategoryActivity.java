package com.example.puneet.jashn;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements Serializable {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    CategoryAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final String TAG = "SearchActivity";

    //nav
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //findViewById(R.id.cardEventBg).setBackgroundColor(-14142604);

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
                        i = new Intent(CategoryActivity.this, SearchEventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MyEventActivity.this, "Search Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuEventHost :
                        item.setChecked(true);
                        i = new Intent(CategoryActivity.this, EventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MainActivity.this, "Host Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
//                    case R.id.menuMyEvents :
//                        item.setChecked(true);
//                        i = new Intent(CategoryActivity.this, MyEventActivity.class);
//                        startActivity(i);
//                        //Toast.makeText(MainActivity.this, "My Events", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawers();
//                        return true;
                    case R.id.menuLogout :
                        item.setChecked(true);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(CategoryActivity.this, MainActivity.class);
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

        Bundle b = this.getIntent().getExtras();
        ArrayList<Category> myList = b.getParcelableArrayList("categoryList");
//        Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<Category> myList =((ArrayList<Category>)args.getSerializable("categoryList"));
////        myList = (ArrayList<Category>) getIntent().getSerializableExtra("categoryList");
        Log.d(TAG, "onCreate: puneet1" + myList.size());
        adapter = new CategoryAdapter(myList,this);
        mRecyclerView.setAdapter(adapter);
    }


}
