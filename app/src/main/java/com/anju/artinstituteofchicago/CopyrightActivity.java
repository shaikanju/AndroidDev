package com.anju.artinstituteofchicago;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CopyrightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copyright_activity);

        // Find the TextViews in the layout
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);
        TextView textView7 = findViewById(R.id.textView7);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CopyrightActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                finish();
            }
        });

        // Set text for each TextView
        textView3.setText("ART INSTITUTE OF");
        textView5.setText("CHICAGO API APP");
        textView6.setText("Content Provided by");
        textView7.setText("ART INSTITUTE OF CHICAGO API");
    }
}
