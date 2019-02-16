package com.blastbeatsandcode.trashcan.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.blastbeatsandcode.trashcan.R;
import com.blastbeatsandcode.trashcan.controller.CanController;
import com.blastbeatsandcode.trashcan.utils.Messages;

// MainActivity is the initial view for the application
public class MainActivity extends AppCompatActivity implements TrashCanView {

    // Reference to the drawer layout
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References to UI elements
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);


        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        // TODO: Get rid of this and put something intelligible here
        TextView tempLabel = (TextView) findViewById(R.id.lblTempLabel);
        tempLabel.setTextSize(18f);
        tempLabel.setTextColor(Color.BLACK);
        tempLabel.setText("TrashCAN with Stankless Technology\n\nBrought to you by the guys who got stuck in a group together but it actually worked out pretty okay.\n\n-Trashbois");
        tempLabel.setGravity(Gravity.CENTER_HORIZONTAL);

        // Intents that can be used to start other activities
        final Intent homeIntent = new Intent(this, MainActivity.class);
        final Intent groceryIntent = new Intent(this, GroceryListview.class);
        final Intent settingsIntent = new Intent(this, SettingsView.class);

        // Gets if the user has tapped on menu items
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.status_btn:
                                startActivity(homeIntent);
                                mDrawerLayout.closeDrawers();
                                return true;
                            case R.id.grocery_list_btn:
                                startActivity(groceryIntent);
                                mDrawerLayout.closeDrawers();
                                return true;
                            case R.id.settings_btn:
                                startActivity(settingsIntent);
                                mDrawerLayout.closeDrawers();
                                return true;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    // Open the navigation menu when the user taps on the button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void update() {
        // TODO: Implement this
    }
}
