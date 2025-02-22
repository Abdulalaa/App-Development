package com.example.lab4;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar setup
        Toolbar myToolbar = (Toolbar) findViewById(R.id.header_toolbar);
        setSupportActionBar(myToolbar);
    }

    // Process image from URL when button clicked
    public void processImage(View view) {
        // Initialize EditText to get URL of image
        EditText urlInput = (EditText) findViewById(R.id.url_input);
        String urlString = urlInput.getText().toString(); // Get URL as string

        try {
            URL url = new URL(urlString); // Initialize URL object

            // Initialize TextView to display processed result
            TextView result = (TextView) findViewById(R.id.display_text);
            result.setText("Processed Image:\n");

            // Initialize SimpleDraweeView to display image
            Uri uri = Uri.parse(url.toString()); // Convert URL to URI
            SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.image_area);
            draweeView.setImageURI(uri);
        } catch (MalformedURLException e) {
            // Handle invalid URL
            TextView result = (TextView) findViewById(R.id.display_text);
            result.setTextColor(Color.RED);
            result.setText("Invalid URL");
        }
    }
}