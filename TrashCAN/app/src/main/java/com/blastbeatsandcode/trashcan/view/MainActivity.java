package com.blastbeatsandcode.trashcan.view;

import android.graphics.Color;
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
    private DrawerLayout _drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _drawerLayout = findViewById(R.id.drawer_layout);


        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);



        TextView tempLabel = (TextView) findViewById(R.id.lblTempLabel);
        tempLabel.setTextSize(18f);
        tempLabel.setTextColor(Color.BLACK);
        tempLabel.setText("TrashCAN with Stankless Technology\n\nBrought to you by the guys who got stuck in a group together but it actually worked out pretty okay.\n\n-Trashbois");
        tempLabel.setGravity(Gravity.CENTER_HORIZONTAL);

        Messages.makeToast(this, "Application loaded! Time to get STANKLESS!");
    }

    // Open the navigation menu when the user taps on the button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void update() {
        // TODO: Implement this
    }
}
