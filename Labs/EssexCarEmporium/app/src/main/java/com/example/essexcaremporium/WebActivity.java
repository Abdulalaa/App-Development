package com.example.essexcaremporium;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WebActivity extends AppCompatActivity {

    @Inject
    CarList carList;  // Reference to the singleton CarList object

    private EditText urlInput;  // Input field for the URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web);

        // Set up padding for system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize URL input field
        urlInput = findViewById(R.id.edit_web_url);
    }

    // Method to load car data from a web URL
    public void webLoad(View view) {
        String urlString = urlInput.getText().toString().trim();

        // Validate URL
        if (urlString.isEmpty()) {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Please enter a valid URL.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        try {
            // Fetch and parse data from URL
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // Clear existing car list before loading new data
            carList.clear();

            // Variables to store car info
            String make = "", model = "", vin = "", fuelType = "", imageUrl = "";
            int year = 0, mpg = 0;
            double price = 0;
            int lineCounter = 0;

            // Parse car data (8 lines per car)
            String line;
            while ((line = reader.readLine()) != null) {
                switch (lineCounter % 8) {
                    case 0:
                        make = line;
                        break;
                    case 1:
                        model = line;
                        break;
                    case 2:
                        year = Integer.parseInt(line);
                        break;
                    case 3:
                        vin = line;
                        break;
                    case 4:
                        fuelType = line;
                        break;
                    case 5:
                        price = Double.parseDouble(line);
                        break;
                    case 6:
                        mpg = Integer.parseInt(line);  // -1 for EVs
                        break;
                    case 7:
                        imageUrl = line;
                        // Add new Car to the car list
                        Car car = new Car(make, model, year, vin, fuelType, price, mpg, imageUrl);
                        carList.add(car);
                        break;
                }
                lineCounter++;
            }

            reader.close();
            hideKeyboard();

            // Display success message using Snackbar
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Cars loaded successfully from web file.", Snackbar.LENGTH_SHORT).show();

        } catch (Exception e) {
            // Handle any errors
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Error loading car data from the provided URL.", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Method to hide the soft keyboard
    private void hideKeyboard() {
        Context context = getCurrentFocus().getContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}