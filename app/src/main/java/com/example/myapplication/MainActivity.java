package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myapplication.GameFrag;
import com.example.myapplication.R;
import com.example.myapplication.Simple;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private FrameLayout frameLayoutCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        frameLayoutCenter = findViewById(R.id.frame_layout_center);

        // Set up the ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set up the navigation item selected listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_test_game:
                        showGameFragment();
                        break;
                    case R.id.action_commits:
                        // Open a website in a browser
                        String url = "https://example.com";
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                        websiteIntent.setData(Uri.parse(url));
                        startActivity(websiteIntent);
                        break;
                    // Add more cases for other menu items as needed

                    default:
                        break;
                }

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Show the Simple fragment initially
        showSimpleFragment();
    }

    private void showSimpleFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new Simple())
                .commit();
        frameLayoutCenter.setVisibility(View.GONE);
    }

    public void showGameFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new GameFrag())
                .commit();
        frameLayoutCenter.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Toggle the drawer when the hamburger icon is selected
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
