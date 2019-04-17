package com.blastbeatsandcode.trashcan.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blastbeatsandcode.trashcan.R;
import com.blastbeatsandcode.trashcan.utils.Constant;
import com.blastbeatsandcode.trashcan.utils.Messages;

public class SettingsView extends AppCompatActivity implements TrashCanView {
    Button btnTest;
    Button btnAddJob;
    Button btnSaveSchedule;
    ListView jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_view);

        // Get Interface features
        btnTest = (Button) findViewById(R.id.btnTestConnection);
        btnAddJob = (Button) findViewById(R.id.btnAddJob);
        btnSaveSchedule = (Button) findViewById(R.id.btnSaveSchedule);
        jobList = (ListView) findViewById(R.id.jobList);

        //Enable back button
        setSupportActionBar((Toolbar) findViewById(R.id.settings_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create volley queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        // Test the connection to the server
        btnTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Messages.makeToast(getApplicationContext(), "Response from the server:\n" + response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Messages.makeToast(getApplicationContext(), "Request failed, no response received!");
                    }
                });
                // Add the request to the RequestQueue
                queue.add(stringRequest);
            }
        });

        // Add a job to the schedule
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Messages.makeToast(getApplicationContext(), "TODO: Add a job");
            }
        });

        // Save the schedule
        btnSaveSchedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Messages.makeToast(getApplicationContext(), "TODO: Save the schedule");
            }
        });
    }

    @Override
    public void update() {
        // TODO: Implement this
    }
}
