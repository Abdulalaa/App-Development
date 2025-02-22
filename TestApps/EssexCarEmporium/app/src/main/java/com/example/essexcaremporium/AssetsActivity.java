package com.example.essexcaremporium;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AssetsActivity extends AppCompatActivity {

    @Inject
    CarList carList;  // Reference to the singleton CarList object

    private EditText fileNameInput;  // Input field for the assets file name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);

        // Initialize the input field for the file name
        fileNameInput = findViewById(R.id.edit_file_name);
    }

    // Method to load a car from an assets file
    public void loadFromAssets(View view) {
        String fileName = fileNameInput.getText().toString().trim();

        // Validate if the file name is provided
        if (fileName.isEmpty()) {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Please enter a valid file name.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        try {
            // Get the AssetManager and open the file
            AssetManager assetManager = getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));

            // Variables to store car information
            String make = "", model = "", vin = "", fuelType = "", imageUrl = "";
            int year = 0, mpg = 0;
            double price = 0;

            // Read the car data in the correct order
            make = reader.readLine();
            model = reader.readLine();
            year = Integer.parseInt(reader.readLine());
            vin = reader.readLine();
            fuelType = reader.readLine();
            price = Double.parseDouble(reader.readLine());
            mpg = Integer.parseInt(reader.readLine());
            imageUrl = reader.readLine();

            // Create a new Car object with the loaded data
            Car newCar = new Car(make, model, year, vin, fuelType, price, mpg, imageUrl);

            // Add the new car to the car list
            carList.add(newCar);
            hideKeyboard();

            // Show success message using Snackbar
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Car added successfully from assets file.", Snackbar.LENGTH_SHORT).show();

            reader.close();

        } catch (Exception e) {
            // Handle any errors while reading the file
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Error loading car data from the file.", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Method to hide the soft keyboard after loading the data
    private void hideKeyboard() {
        Context context = getCurrentFocus().getContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}