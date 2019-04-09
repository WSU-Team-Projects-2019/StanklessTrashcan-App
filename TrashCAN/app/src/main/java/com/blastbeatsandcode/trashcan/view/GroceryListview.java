package com.blastbeatsandcode.trashcan.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
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
    LinearLayout layout;
    ScrollView scrollView;
    ListView listView;

    // Arrays to hold item values
    ImageView[] images;
    String[] itemNames;
    String[] barcodes;
    String[] counts;

    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_listview);

        //Enable back button
        setSupportActionBar((Toolbar) findViewById(R.id.grocery_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        // Get reference to linear layout
        layout = (LinearLayout) findViewById(R.id.grocerylist_linearlayout);
        scrollView = (ScrollView) findViewById(R.id.grocerylist_scrollview);
        */

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
                            CustomAdapter customAdapter = new CustomAdapter();
                            listView.setAdapter(customAdapter);

                            listView.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {

                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_DOWN:
                                            historicX = event.getX();
                                            historicY = event.getY();
                                            break;

                                        case MotionEvent.ACTION_UP:
                                            if (event.getX() - historicX < -DELTA) {
                                                //FunctionDeleteRowWhenSlidingLeft();
                                                Messages.makeToast(getApplicationContext(), "LEFT");
                                                return true;
                                            }
                                            else if (event.getX() - historicX > DELTA) {
                                                Messages.makeToast(getApplicationContext(), "RIGHT");
                                                return true;
                                            }
                                            break;
                                            case MotionEvent.

                                        default:
                                            return false;
                                    }
                                    return false;
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
