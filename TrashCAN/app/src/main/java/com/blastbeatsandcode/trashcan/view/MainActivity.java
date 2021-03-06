package com.blastbeatsandcode.trashcan.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blastbeatsandcode.trashcan.R;
import com.blastbeatsandcode.trashcan.controller.CanController;
import com.blastbeatsandcode.trashcan.utils.Constant;
import com.blastbeatsandcode.trashcan.utils.Messages;
import com.gigamole.library.PulseView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

// MainActivity is the initial view for the application
public class MainActivity extends AppCompatActivity implements TrashCanView {

    // Reference to the drawer layout
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    // Pulseview animation
    PulseView pulseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References to UI elements
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        // Get pulseview
        pulseView = (PulseView) findViewById(R.id.pv);


        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Get swipe refresh layout
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_list_main);

        // Refresh the layout when the user drags down
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                refreshLayout.setRefreshing(false);
            }
        });

        // Set onClick listeners
        final RequestQueue queue = Volley.newRequestQueue(this);

        final TextView lblStatus = (TextView) findViewById(R.id.lblStatus);
        lblStatus.setTextSize(18);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP + "get-can-status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject json = new JSONObject(response);

                            final String lid = (json.getInt("lid_status") == 1) ? "Open" : "Closed";
                            final String fan = (json.getInt("fan_status") == 1) ? "On" : "Off";
                            final String led = (json.getInt("led_status") == 1) ? "On" : "Off";
                            final String bulb = (json.getInt("bulb_status") == 1) ? "On" : "Off";

                            lblStatus.setText("Lid:\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + lid + "\nFan Status:\t\t\t" + fan +
                                    "\nLED Status:\t\t\t\t" + led + "\nBulb Status:\t" + bulb);
                            lblStatus.setTextSize(24);
                            lblStatus.setAllCaps(true);
                            lblStatus.setTextColor(Color.rgb(0,0,0));
                            pulseView.startPulse();
                        } catch (JSONException e) {
                            lblStatus.setText("Could not get can status! :(");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblStatus.setText("Could not retrieve status from server. :(");
            }
        });
        // Add the request to the RequestQueue
        queue.add(stringRequest);

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
                                menuItem.setChecked(false);
                                mDrawerLayout.closeDrawers();
                                return false; // Return false to not highlight the selection
                            case R.id.grocery_list_btn:
                                startActivity(groceryIntent);
                                menuItem.setChecked(false);
                                mDrawerLayout.closeDrawers();
                                return false;
                            case R.id.settings_btn:
                                startActivity(settingsIntent);
                                menuItem.setChecked(false);
                                mDrawerLayout.closeDrawers();
                                return false;
                        }
                        mDrawerLayout.closeDrawers();
                        return false;
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
