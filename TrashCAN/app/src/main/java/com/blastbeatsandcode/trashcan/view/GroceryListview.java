package com.blastbeatsandcode.trashcan.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class GroceryListview extends AppCompatActivity implements TrashCanView {

    JSONArray items;
    ListView listView;
    SwipeRefreshLayout refreshLayout;
    CustomAdapter customAdapter;

    // Arrays to hold item values
    ImageView[] images;
    String[] itemNames;
    String[] barcodes;
    String[] counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_listview);

        // Get swipe refresh layout
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_list);

        // Get custom adapter
        customAdapter = new CustomAdapter();

        //Enable back button
        setSupportActionBar((Toolbar) findViewById(R.id.grocery_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                refreshLayout.setRefreshing(false);
            }
        });

        loadItemsToShoppingList();
    }

    // Pulls down items to load to shopping list
    private void loadItemsToShoppingList()
    {
        // Populate the list of items from the shopping list on the server
        final RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SERVER_IP + "grocerylist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            items = new JSONArray(response);
                            int len = items.length();
                            images = new ImageView[len];
                            itemNames = new String[len];
                            barcodes = new String[len];
                            counts = new String[len];

                            for (int i = 0; i < items.length(); i++)
                            {
                                JSONObject obj = (JSONObject) items.get(i);
                                // TODO: Change this to some unique image...maybe
                                //images[i] = something;
                                itemNames[i] = obj.getString("title");
                                barcodes[i] = obj.getString("barcode");
                                counts[i] = obj.getString("count");
                            }

                            // Create the custom adapter to fill in the food items based on our JSON array
                            listView = (ListView)findViewById(R.id.grocerylist_listview);
                            listView.setAdapter(customAdapter);
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
            return items.length();
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
            convertView = getLayoutInflater().inflate(R.layout.grocerylist_item, null);

            ImageView itemImage = (ImageView)convertView.findViewById(R.id.item_image);
            TextView itemName = (TextView)convertView.findViewById(R.id.item_name);
            TextView barcode = (TextView)convertView.findViewById(R.id.barcode);
            TextView count = (TextView)convertView.findViewById(R.id.count);

            itemImage.setImageResource(R.drawable.vaporwave);
            itemName.setText(itemNames[position]);
            barcode.setText(barcodes[position]);
            count.setText("Count: " + counts[position]);

            return convertView;
        }
    }

}
