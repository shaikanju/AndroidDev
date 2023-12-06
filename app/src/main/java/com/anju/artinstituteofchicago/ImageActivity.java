package com.anju.artinstituteofchicago;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageActivity extends AppCompatActivity {
    private int imageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);

        // Retrieve the image URL from the intent
        // Get the Intent that started this activity and retrieve the data
        Intent intent = getIntent();
        if (intent != null) {
            String imageUrl = intent.getStringExtra("image_url");
            String title = intent.getStringExtra("title");
            String artistDisplay = intent.getStringExtra("artist_display");


            // Load the image into the ImageView
            PhotoView photoView = findViewById(R.id.imageView5); // Replace with the appropriate ImageView ID


            Picasso.get().load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(photoView);
            photoView.setMaximumScale(12.25f);
            photoView.setMediumScale(3.5f);
            photoView.setMinimumScale(1f);
            photoView.setMaximumScale(10);
            photoView.setZoomable(true);
            TextView textView14 = findViewById(R.id.textView14);
            TextView textView15 = findViewById(R.id.textView15);
            textView14.setText(title);

            // Extract the first part of the artist display
            String[] parts = artistDisplay.split("\\n");
            String firstPart = parts[0];
            textView15.setText(firstPart);
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            });
        }
    }
}
