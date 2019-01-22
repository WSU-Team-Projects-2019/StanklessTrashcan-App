package com.blastbeatsandcode.trashcan.view;

import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.blastbeatsandcode.trashcan.R;
import com.blastbeatsandcode.trashcan.controller.CanController;
import com.blastbeatsandcode.trashcan.utils.Messages;

// MainActivity is the initial view for the application
public class MainActivity extends AppCompatActivity implements TrashCanView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tempLabel = (TextView) findViewById(R.id.lblTempLabel);
        tempLabel.setTextSize(32f);
        tempLabel.setTextColor(Color.BLACK);
        tempLabel.setText("TrashCAN with Stankless Technology\n\nBrought to you by the guys who got stuck in a group together but it actually worked out pretty okay.\n\n-Trashbois");
        tempLabel.setGravity(Gravity.CENTER_HORIZONTAL);

        Messages.makeToast(this, "Application loaded! Time to get STANKLESS!");
    }

    @Override
    public void update() {
        // TODO: Implement this
    }
}
