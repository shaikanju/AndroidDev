package com.anju.artinstituteofchicago;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    EditText searchText;
    ImageView backgroundImage;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    private List<Artwork> artworkList = new ArrayList<>();

    boolean isResponseReceived = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Picasso picasso = new Picasso.Builder(this)
                .loggingEnabled(true)
                .build();

        ImageView clearButton = findViewById(R.id.clearButton);
        searchText = findViewById(R.id.editTextText);

        backgroundImage = findViewById(R.id.backgroundImage);

        recyclerView = findViewById(R.id.recyclerView);
        int itemSpacing = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(itemSpacing));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArtworkAdapter artworkAdapter = new ArtworkAdapter(artworkList); // Create the adapter
        recyclerView.setAdapter(artworkAdapter);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Button searchButton = findViewById(R.id.button);
        Button randomButton = findViewById(R.id.button2);
        TextView textView6 = findViewById(R.id.copyrightTextView);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CopyrightActivity
                Intent intent = new Intent(MainActivity.this, CopyrightActivity.class);
                startActivity(intent);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressBar.setVisibility(View.VISIBLE);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                reQueryForArtwork(v.getContext());
                /* requestQueue = Volley.newRequestQueue(v.getContext());
                String url = "https://api.artic.edu/api/v1/galleries?limit=100&fields=id&page=1";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                showRecyclerView();
                                hideBackgroundImage();
                                try {
                                    List<String> galleryIds = new ArrayList<>();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        int galleryId = data.getJSONObject(i).getInt("id");
                                        galleryIds.add(String.valueOf(galleryId));
                                    }
                                    String randomGalleryId = selectRandomGalleryId(galleryIds);
                                    fetchRandomArtwork(v.getContext(),randomGalleryId);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "Error occurred while fetching gallery IDs: " + error.getMessage());


                    }
                });

                // Adding request to request queue
                requestQueue.add(jsonObjectRequest);*/
            }
            private void reQueryForArtwork(Context context) {
                String url = "https://api.artic.edu/api/v1/galleries?limit=100&fields=id&page=1";
                requestQueue = Volley.newRequestQueue(context);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                showRecyclerView();
                                hideBackgroundImage();
                                try {
                                    List<String> galleryIds = new ArrayList<>();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        int galleryId = data.getJSONObject(i).getInt("id");
                                        galleryIds.add(String.valueOf(galleryId));
                                    }
                                    if (galleryIds.isEmpty()) {
                                        reQueryForArtwork(context); // Call the method again if no data is received
                                    } else {
                                        String randomGalleryId = selectRandomGalleryId(galleryIds);
                                        fetchRandomArtwork(context, randomGalleryId);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "Error occurred while fetching gallery IDs: " + error.getMessage());
                        reQueryForArtwork(context); // Call the method again if there is an error
                    }
                });

                // Adding request to request queue
                requestQueue.add(jsonObjectRequest);
            }

            private String selectRandomGalleryId(List<String> galleryIds) {
                Random random = new Random();
                return galleryIds.get(random.nextInt(galleryIds.size()));
            }

            private void fetchRandomArtwork(Context context, String galleryId) {
                requestQueue = Volley.newRequestQueue(context);
                String url = "https://api.artic.edu/api/v1/artworks/search?query[term][gallery_id]=" + galleryId + "&limit=100&fields=title,date_display,artist_display,medium_display,artwork_type_title,image_id,dimensions,provenance_text,department_title,credit_line,place_of_origin,gallery_title,gallery_id,id,api_link,thumbnail";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressBar.setVisibility(View.GONE);
                                artworkList.clear();
                                try {
                                    JSONArray data = response.getJSONArray("data");
                                    if (data.length() > 0) {
                                        int randomIndex = new Random().nextInt(data.length());
                                        JSONObject artworkJson = data.getJSONObject(randomIndex);
                                        Artwork artwork =  new Artwork();
                                        artwork.setTitle(artworkJson.getString("title"));
                                        artwork.setDateDisplay(artworkJson.getString("date_display"));
                                        artwork.setArtistDisplay(artworkJson.getString("artist_display"));
                                        artwork.setMediumDisplay(artworkJson.getString("medium_display"));
                                        artwork.setArtworkTypeTitle(artworkJson.getString("artwork_type_title"));
                                        artwork.setImageId(artworkJson.getString("image_id"));
                                        artwork.setDimensions(artworkJson.getString("dimensions"));
                                        artwork.setDepartmentTitle(artworkJson.getString("department_title"));
                                        artwork.setCreditLine(artworkJson.getString("credit_line"));
                                        artwork.setPlaceOfOrigin(artworkJson.getString("place_of_origin"));
                                        artwork.setGalleryTitle(artworkJson.optString("gallery_title", "Not on Display"));
                                        if (!artworkJson.isNull("gallery_id")) {
                                            int galleryId = artworkJson.getInt("gallery_id");
                                            artwork.setGalleryId(galleryId);
                                        } else {
                                            artwork.setGalleryId(0); // or any other appropriate default value
                                        }

                                        artwork.setId(artworkJson.getInt("id"));
                                        artwork.setApiLink(artworkJson.getString("api_link"));
                                        artworkList.add(artwork);
                                        artworkAdapter.notifyDataSetChanged();
                                        // Add this artwork to your list
                                    }


                                    else {
                                        reQueryForArtwork(context);
                                         // Retry if no artwork is found
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "Error occurred while fetching random artwork: " + error.getMessage());

                    }
                });

                // Adding request to request queue
                requestQueue.add(jsonObjectRequest);
            }
        });



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchQuery = searchText.getText().toString();
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                boolean isConnected = capabilities != null && (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED));

                if (!isConnected) {
                    displayNoConnectionDialog();
                    return;
                }
                if (searchQuery.isEmpty()) {
                    // Do nothing or handle this case as needed
                    return;
                }
                if (searchQuery.length() < 3) {

                    displayDialog();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                    // Initialize a RequestQueue
                    // Initialize a RequestQueue
                     requestQueue = Volley.newRequestQueue(v.getContext());

                    // Construct the URL using Uri.Builder
                    Uri.Builder builder = Uri.parse("https://api.artic.edu/api/v1/artworks/search").buildUpon();
                    builder.appendQueryParameter("q", searchQuery); // Replace searchString with your actual search string

                    // Fetch the string resource for the limit value
                    String searchLimit = getResources().getString(R.string.search_limit);
                    builder.appendQueryParameter("limit", searchLimit);

                    builder.appendQueryParameter("page", "1");
                    builder.appendQueryParameter("fields", "title, date_display, artist_display, medium_display, artwork_type_title, image_id, dimensions, department_title, credit_line, place_of_origin, gallery_title, gallery_id, id, api_link");
                    String url = builder.build().toString();

// Create a StringRequest with appropriate method, URL, and listeners
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    isResponseReceived = true;
                                    progressBar.setVisibility(View.GONE);
                                    // Handle API response
                                    // Parse JSON response and extract relevant information
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        JSONArray data = jsonResponse.getJSONArray("data");
                                        if (data.length() == 0) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setIcon(R.drawable.img);
                                            builder.setTitle("No Results Found");
                                            builder.setMessage("No results found for \"" + searchQuery + "\", please try another search string.");

                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Continue with search operation or handle the action as needed
                                                    dialog.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }
                                        else{
                                            artworkList.clear();
                                        for (int i = 0; i < data.length(); i++) {
                                            JSONObject artworkJson = data.getJSONObject(i);
                                            Artwork artwork = new Artwork();
                                            artwork.setTitle(artworkJson.getString("title"));
                                            artwork.setDateDisplay(artworkJson.getString("date_display"));
                                            artwork.setArtistDisplay(artworkJson.getString("artist_display"));
                                            artwork.setMediumDisplay(artworkJson.getString("medium_display"));
                                            artwork.setArtworkTypeTitle(artworkJson.getString("artwork_type_title"));
                                            artwork.setImageId(artworkJson.getString("image_id"));
                                            artwork.setDimensions(artworkJson.getString("dimensions"));
                                            artwork.setDepartmentTitle(artworkJson.getString("department_title"));
                                            artwork.setCreditLine(artworkJson.getString("credit_line"));
                                            artwork.setPlaceOfOrigin(artworkJson.getString("place_of_origin"));
                                            artwork.setGalleryTitle(artworkJson.optString("gallery_title", "Not on Display"));
                                            if (!artworkJson.isNull("gallery_id")) {
                                                int galleryId = artworkJson.getInt("gallery_id");
                                                artwork.setGalleryId(galleryId);
                                            } else {
                                                artwork.setGalleryId(0); // or any other appropriate default value
                                            }
                                            artwork.setId(artworkJson.getInt("id"));
                                            artwork.setApiLink(artworkJson.getString("api_link"));
                                            artworkList.add(artwork);
                                            // Add this artwork to your list
                                        }
                                        artworkAdapter.notifyDataSetChanged();

                                        // Update your RecyclerView with the retrieved data
                                        if (isResponseReceived) {
                                            showRecyclerView();
                                            hideBackgroundImage();
                                        } else {
                                            hideRecyclerView();
                                            showBackgroundImage();
                                        }
                                    }} catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error cases
                                    Log.e("MainActivity", "Volley error: " + error.getMessage());
                                }
                            });


// Add the StringRequest to the RequestQueue
                    requestQueue.add(stringRequest);

                }
            }
        });

    }


    private void displayNoConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.drawable.img);
        builder.setTitle("No Connection Error");
        builder.setMessage("No network connection present - cannot contact Art Institute API.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Continue with any appropriate action or dismiss the dialog as needed
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.img);
        builder.setTitle("Search String Too Short");
        builder.setMessage("Please try a longer search string");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with search operation
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void hideBackgroundImage() {
        backgroundImage.setVisibility(View.GONE); // Hide the background image
    }

    private void showBackgroundImage() {
        backgroundImage.setVisibility(View.VISIBLE); // Show the background image
    }

    private void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView
    }
}

