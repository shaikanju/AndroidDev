package com.anju.artinstituteofchicago;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import androidx.appcompat.app.AppCompatActivity;

public class ArtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.art_activity);
        Picasso picasso = new Picasso.Builder(this)
                .loggingEnabled(true)
                .build();

        TextView titleTextView = findViewById(R.id.textView);
        TextView dateDisplayTextView = findViewById(R.id.textView2);
        TextView artistDisplayTextView = findViewById(R.id.artistDisplayTextView);
        //TextView mediumDisplayTextView = findViewById(R.id.textView8);
        TextView artworkTypeTitleTextView = findViewById(R.id.artworkTypeTitleTextView);
        TextView dimensionsTextView = findViewById(R.id.dimensionsTextView);
        TextView departmentTitleTextView = findViewById(R.id.departmentTitleTextView);
        TextView creditLineTextView = findViewById(R.id.creditLineTextView);
        TextView placeOfOriginTextView = findViewById(R.id.placeOfOriginTextView);
        TextView galleryTitleTextView = findViewById(R.id.galleryTitleTextView);
        TextView textview4 = findViewById(R.id.textView4);
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageView2 = findViewById(R.id.imageView2);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String dateDisplay = intent.getStringExtra("date_display");
            String artistDisplay = intent.getStringExtra("artist_display");
            String mediumDisplay = intent.getStringExtra("medium_display");
            String artworkTypeTitle = intent.getStringExtra("artwork_type_title");
            String imageIdString = intent.getStringExtra("image_id"); // 0 is the default value
            String dimensions = intent.getStringExtra("dimensions");
            String departmentTitle = intent.getStringExtra("department_title");
            String creditLine = intent.getStringExtra("credit_line");
            String placeOfOrigin = intent.getStringExtra("place_of_origin");
            String galleryTitle = intent.getStringExtra("gallery_title");
            int galleryId = intent.getIntExtra("gallery_id", 0); // 0 is the default value
            int id = intent.getIntExtra("id", 0); // 0 is the default value
            String apiLink = intent.getStringExtra("api_link");

            String fullImageUrl = "https://www.artic.edu/iiif/2/" + imageIdString + "/full/200,/0/default.jpg"; // Construct the full image URL
            Picasso.get()
                    .load(fullImageUrl)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.error_placeholder)
                    .into(imageView2);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Define the action to be performed when imageView2 is clicked
                    Intent intent = new Intent(ArtActivity.this, ImageActivity.class);
                    intent.putExtra("image_url", fullImageUrl); // Pass the image URL to the next activity
                    intent.putExtra("title", titleTextView.getText().toString()); // Pass the title to the next activity
                    intent.putExtra("artist_display", artistDisplayTextView.getText().toString()); // Pass the artist display to the next activity // Pass the image URL to the next activity
                    startActivity(intent);
                }
            });
            titleTextView.setText(title);
            dateDisplayTextView.setText(dateDisplay);

            String combinedText = artworkTypeTitle + " - " + mediumDisplay;

            artworkTypeTitleTextView.setText(combinedText);

            String ct = artistDisplay;
            String[] artistDisplayParts = artistDisplay.split("\n");
            if (artistDisplayParts.length > 1) {
                ct = artistDisplayParts[0];
                textview4.setText(artistDisplayParts[1]);
                textview4.setVisibility(View.VISIBLE);
                artistDisplayTextView.setText(ct);
            }
else {
                String ft = artistDisplayParts[0];
                artistDisplayTextView.setText(ft);
                textview4.setVisibility(View.INVISIBLE);

            }
            dimensionsTextView.setText(dimensions);
            departmentTitleTextView.setText(departmentTitle);
            creditLineTextView.setText(creditLine);
            placeOfOriginTextView.setText(placeOfOrigin);
            galleryTitleTextView.setText(galleryTitle);
            String galleryLink = "https://www.artic.edu/galleries/" + galleryId;
            SpannableString spannableString = new SpannableString(galleryTitle);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Handle the click action here
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(galleryLink));
                    startActivity(browserIntent);
                }
            };
            spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            galleryTitleTextView.setText(spannableString);
            galleryTitleTextView.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }
}


