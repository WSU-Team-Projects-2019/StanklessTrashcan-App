package com.blastbeatsandcode.trashcan.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.blastbeatsandcode.trashcan.R;
import com.blastbeatsandcode.trashcan.utils.Messages;

public class DrawerView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_view);
        Messages.makeToast(this, "THIS IS A TEST");
    }

    // Handles option selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.status_btn:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.grocery_list_btn:
                startActivity(new Intent(this, GroceryListview.class));
                return true;
            case R.id.settings_btn:
                startActivity(new Intent(this, SettingsView.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);


        return false;
    }
}
