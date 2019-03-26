package com.blastbeatsandcode.trashcan.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blastbeatsandcode.trashcan.R;
import com.blastbeatsandcode.trashcan.utils.Constant;
import com.blastbeatsandcode.trashcan.utils.Messages;

public class GroceryListview extends AppCompatActivity implements TrashCanView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_listview);

        //Enable back button
        setSupportActionBar((Toolbar) findViewById(R.id.grocery_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP + "grocerylist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Messages.makeToast(getApplicationContext(), "Response is: " + response);
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
