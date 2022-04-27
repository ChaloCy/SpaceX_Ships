package com.chalo.spacexships.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chalo.spacexships.R;
import com.chalo.spacexships.adapters.ShipsAdapter;
import com.chalo.spacexships.models.Ship;
import com.chalo.spacexships.utils.APIEndPoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Volley request
    private RequestQueue queue;
    public static final String TAG = "MyTag";
    private ProgressBar progressBar;
    private ArrayList<Ship> shipsList = new ArrayList<>();
    private ShipsAdapter adapter;
    private RecyclerView recyclerView;
    private TextView noShipsTxt;
    private EditText searchEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Volley request
        queue = Volley.newRequestQueue(this);
        // Call method to initialize views
        initViews();
        // call method to get ships from API
        getShips();
    }

    /**
     * Method to initializes views
     */
    private void initViews(){
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        noShipsTxt = findViewById(R.id.noShipsTxt);
        searchEditTxt = findViewById(R.id.searchEditTxt);
        // set the layout manager
        // use GridLayoutManager to divide the layout into 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        searchEditTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // Get text entered by user in search EditText
                String shipName = (searchEditTxt.getText().toString()).trim().toLowerCase(Locale.getDefault());
                // Call method to to perform the filtering
                adapter.filterShips(shipName);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // no implementation needed
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // no implementation needed
            }
        });
    }



    /**
     * Method that gets all Ships
     */
    private void getShips(){
        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIEndPoints.ALL_SHIPS_URL,
                response -> {
                    // hide progress bar
                    progressBar.setVisibility(View.GONE);
                    // call method to process the response
                    processResponse(response);
                }, error -> {
                    // hide progress bar
                    progressBar.setVisibility(View.GONE);
                    // use Toast to show error message
                    Toast.makeText(MainActivity.this, getString(R.string.connection_error),
                            Toast.LENGTH_SHORT).show();
                });
        // Set the tag on the request.
        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**
     * Parses the JSON return in response, sets the data adapter
     * RecyclerView display the data parsed to it by the adapter
     * @param response String
     */
    private void processResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);

            int i = 0;

            while ( i < jsonArray.length()) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String shipID = jsonObject.optString("ship_id");
                String shipName = jsonObject.optString("ship_name");
                String shipImageUrl = jsonObject.optString("image");
                String homePort = jsonObject.optString("home_port");
                String yearBuilt = jsonObject.optString("year_built");
                String type = jsonObject.optString("ship_type");

                //initialize Ship class and add to shipsList
                Ship ship = new Ship(shipID, shipName, shipImageUrl, homePort, yearBuilt, type);
                shipsList.add(ship);

                //increment counter
                i++;
            }

            if( shipsList.size() < 1){
                noShipsTxt.setVisibility(View.VISIBLE);
            }
            //Initialize adapter
            adapter = new ShipsAdapter(this, shipsList);
            // add adapter to RecyclerView
            recyclerView.setAdapter(adapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // stop request
        queue.cancelAll(TAG);
    }
}