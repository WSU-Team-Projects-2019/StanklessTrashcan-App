package com.blastbeatsandcode.trashcan.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blastbeatsandcode.trashcan.R;
import com.blastbeatsandcode.trashcan.utils.Constant;
import com.blastbeatsandcode.trashcan.utils.Messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SettingsView extends AppCompatActivity implements TrashCanView {
    JSONArray jsonJobs;
    Button btnTest;
    Button btnAddJob;
    Button btnSaveSchedule;
    ListView jobList;
    CustomAdapter customAdapter;

    ArrayList<String> jobs = new ArrayList<String>();
    int[] hours;
    int[] mins;

    Context con = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_view);

        // Get Interface features
        btnTest = (Button) findViewById(R.id.btnTestConnection);
        btnAddJob = (Button) findViewById(R.id.btnAddJob);
        jobList = (ListView) findViewById(R.id.jobList);

        // Create custom adapter
        customAdapter = new CustomAdapter();

        //Enable back button
        setSupportActionBar((Toolbar) findViewById(R.id.settings_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create volley queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        loadJobs();

        // Test the connection to the server
        btnTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Messages.makeToast(getApplicationContext(), "Connected to server successfully.");
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
                if (!(jobs.size() >= 5)) {
                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(con,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    int[] tempHours = new int[hours.length + 1];
                                    int[] tempMins = new int[mins.length + 1];

                                    for (int i = 0; i < hours.length; i++)
                                    {
                                        tempHours[i] = hours[i];
                                        tempMins[i] = mins[i];
                                    }

                                    // Create values for time conversion
                                    SimpleDateFormat dateFormatUTC = new SimpleDateFormat("HH:mm");
                                    SimpleDateFormat dateFormatLocal = new SimpleDateFormat("HH:mm");
                                    dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    dateFormatLocal.setTimeZone(TimeZone.getDefault());
                                    try {
                                        // Convert from UTC to local time
                                        Date givenDate = dateFormatLocal.parse(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                                        String localTime = dateFormatUTC.format(givenDate);
                                        String[] partsTime = localTime.split(":");

                                        tempHours[hours.length] = Integer.parseInt(partsTime[0]);
                                        tempMins[mins.length] = Integer.parseInt(partsTime[1]);


                                        // Move values back over into hours and minutes
                                        hours = new int[tempHours.length];
                                        mins = new int[tempMins.length];
                                        hours = Arrays.copyOf(tempHours, tempHours.length);
                                        mins = Arrays.copyOf(tempMins, tempMins.length);


                                        jobs.add(hours[hours.length - 1] + ":" + mins[mins.length - 1]);

                                        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP + "schedule-job?hr=" + hours[hours.length - 1] + "&min=" + mins[mins.length - 1],
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        // Display the first 500 characters of the response string.
                                                        Messages.makeToast(con, response);
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Messages.makeToast(con, "Request failed, no response received!");
                                            }
                                        });
                                        // Add the request to the RequestQueue
                                        queue.add(stringRequest);

                                        jobList.setAdapter(customAdapter);
                                    } catch (ParseException e) {
                                        Messages.makeToast(getApplicationContext(), "Invalid time");
                                    }
                                }
                            }, hour, minute, false);
                    timePickerDialog.show();
                } else {
                    Messages.makeToast(getApplicationContext(), "Maximum of 5 cleaning cycles allowed. Please remove one to insert a different time.");
                }
            }
        });
    }


    // Pulls down scheduled jobs from the server
    private void loadJobs()
    {
        // Populate the list of items from the shopping list on the server
        final RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP + "jobs",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonJobs = new JSONArray(response);
                            int len = jsonJobs.length();
                            hours = new int[len];
                            mins = new int[len];

                            for (int i = 0; i < jsonJobs.length(); i++)
                            {
                                JSONObject obj = (JSONObject) jsonJobs.get(i);
                                hours[i] = Integer.parseInt(obj.getString("hour"));
                                mins[i] = Integer.parseInt(obj.getString("minute"));
                                jobs.add(hours[i] + ":" + mins[i]);
                            }

                            // Create the custom adapter to fill in the food items based on our JSON array
                            jobList = (ListView)findViewById(R.id.jobList);
                            jobList.setAdapter(customAdapter);

                            jobList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //list is my listView
                                @Override
                                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                                    AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(con);
                                    builder.setMessage("Remove clean cycle job?").setTitle("Remove scheduled clean cycle");

                                    // Yes Button
                                    builder.setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP + "remove-job?hr=" + hours[pos] + "&min=" + mins[pos],
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    // Display the first 500 characters of the response string.
                                                                    Messages.makeToast(getApplicationContext(), response);
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Messages.makeToast(getApplicationContext(), "Request failed, no response received!");
                                                        }
                                                    });
                                                    // Add the request to the RequestQueue
                                                    queue.add(stringRequest);

                                                    // Reload the view
                                                    recreate();
                                                }
                                            });

                                    // No button
                                    builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                    return true;
                                }
                            });
                        } catch (JSONException e) {
                            Messages.makeToast(getApplicationContext(), "Could not properly parse data from server.");
                        }
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


    @Override
    public void update() {
        // TODO: Implement this
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return jobs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Create values for time conversion
            SimpleDateFormat dateFormatUTC = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateFormatLocal = new SimpleDateFormat("HH:mm");
            dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
            dateFormatLocal.setTimeZone(TimeZone.getDefault());
            try {
                // Convert from UTC to local time
                Date givenDate = dateFormatUTC.parse(hours[position] + ":" + mins[position]);
                String localTime = dateFormatLocal.format(givenDate);

                convertView = getLayoutInflater().inflate(R.layout.joblist_item, null);


                TextView time = (TextView)convertView.findViewById(R.id.txtTime);
                time.setText(localTime);
            } catch (ParseException e) {
                Messages.makeToast(getApplicationContext(), "Invalid time");
            }

            return convertView;
        }
    }
}
