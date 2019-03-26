package com.blastbeatsandcode.trashcan.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class GroceryListview extends AppCompatActivity implements TrashCanView {

    JSONArray items;
    LinearLayout layout;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_listview);

        //Enable back button
        setSupportActionBar((Toolbar) findViewById(R.id.grocery_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get reference to linear layout
        layout = (LinearLayout) findViewById(R.id.grocerylist_linearlayout);
        scrollView = (ScrollView) findViewById(R.id.grocerylist_scrollview);

        final RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP + "grocerylist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            items = new JSONArray(response);
                            for (int i = 0; i < items.length(); i++)
                            {
                                TextView view = new TextView(getApplicationContext());
                                JSONObject obj = (JSONObject) items.get(i);
                                view.setText("Name: " + obj.getString("title") + "\nBarcode: " + obj.getString("barcode") + "\nCount: " + obj.getString("count") + "\n");
                                view.setTextSize(22);
                                view.setTextColor(Color.BLACK);
                                layout.addView(view);
                            }
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
}
