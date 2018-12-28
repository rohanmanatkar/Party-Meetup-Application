package com.example.puneet.jashn;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class EventLandingActivity extends AppCompatActivity {

    public String eventName;
    public String userName;
    public int eventBannerColor;
    public static Bundle fragmentArgumentBundle;

    FragmentPagerAdapter adapterViewPager;

    //nav
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_landing);


        LinearLayout eventBannerLayout = (LinearLayout) findViewById(R.id.eventBannerLayout);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        eventName = extras.getString("EVENT_NAME");

        //nav-drawer

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_event_landing);
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
                        i = new Intent(EventLandingActivity.this, SearchEventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MyEventActivity.this, "Search Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuEventHost :
                        item.setChecked(true);
                        i = new Intent(EventLandingActivity.this, EventActivity.class);
                        startActivity(i);
                        //Toast.makeText(MainActivity.this, "Host Event", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
//                    case R.id.menuMyEvents :
//                        item.setChecked(true);
//                        i = new Intent(EventLandingActivity.this, MyEventActivity.class);
//                        startActivity(i);
//                        //Toast.makeText(MainActivity.this, "My Events", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawers();
//                        return true;
                    case R.id.menuLogout :
                        item.setChecked(true);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(EventLandingActivity.this, MainActivity.class);
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

        eventName ="Event Landing";
        //userName = extras.getString("USER_NAME");
        userName = "INDRA";
        //eventBannerColor = extras.getString("EVENT_COLOR");
        String hexColor = String.format("#%06X", (0xFFFFFF & 14142604));
        //eventBannerColor = 14142604;

        Bundle fragmentArgumentBundle = new Bundle();
        fragmentArgumentBundle.putString("EVENT_NAME", eventName);
        fragmentArgumentBundle.putString("USER_NAME", userName);

        eventBannerLayout.setBackgroundColor(Color.parseColor(hexColor));

        TextView eventTitleBanner = (TextView)findViewById(R.id.eventBannerTitle);

        //in your OnCreate() method
        eventTitleBanner.setText(eventName);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }




    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            GroupchatFragment grpfrag = new GroupchatFragment();
            grpfrag.setArguments(fragmentArgumentBundle);

            ToDoFragment tdfrag = new ToDoFragment();
            tdfrag.setArguments(fragmentArgumentBundle);
            switch (position) {
                case 0:
                    return grpfrag.newInstance();
                case 1:
                    return tdfrag.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "Group Chat";
            else return "To-Do List";
        }

    }

}