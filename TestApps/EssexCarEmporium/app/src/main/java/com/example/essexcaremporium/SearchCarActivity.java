package com.example.essexcaremporium;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchCarActivity extends AppCompatActivity {

    @Inject
    CarList carList;   // Reference to the singleton CarList object

    private EditText vinInput;
    private TextView carDetailsText;
    private SimpleDraweeView carImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);  // Initialize Fresco
        setContentView(R.layout.activity_search);

        vinInput = findViewById(R.id.edit_vin);
        carDetailsText = findViewById(R.id.text_car_details);
        carImage = findViewById(R.id.car_image);
    }

    // Method to search the car by VIN
    public void searchCarByVin(View view) {
        String vin = vinInput.getText().toString().trim();

        // Check if the input VIN is valid
        if (vin.isEmpty()) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Please enter a valid VIN", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Search for the car with the given VIN
        Car foundCar = null;
        for (Car car : carList) {
            if (car.getVin().equalsIgnoreCase(vin)) {
                foundCar = car;
                break;
            }
        }

        // If the car is found, display its details
        if (foundCar != null) {
            StringBuilder carDetails = new StringBuilder();
            carDetails.append("Make: ").append(foundCar.getMake()).append("\n");
            carDetails.append("Model: ").append(foundCar.getModel()).append("\n");
            carDetails.append("Year: ").append(foundCar.getYear()).append("\n");
            carDetails.append("Fuel Type: ").append(foundCar.getFuelType()).append("\n");
            carDetails.append("Price: $").append(foundCar.getPrice()).append("\n");

            // Display federal tax credit if applicable
            double taxCredit = foundCar.getTaxCredit();
            if (taxCredit > 0) {
                carDetails.append("Federal Tax Credit: $").append(taxCredit).append("\n");
            }

            // Display MPG if not an EV
            if (!foundCar.getFuelType().equalsIgnoreCase("EV")) {
                carDetails.append("MPG: ").append(foundCar.getMpg()).append("\n");
            }

            // Display car details in the TextView
            carDetailsText.setText(carDetails.toString() + "\n");

            // Load and display car image using SimpleDraweeView
            Uri uri = Uri.parse(foundCar.getImageUrl());
            carImage.setImageURI(uri);
        } else {
            // Show error message if the VIN is not found
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Car with VIN " + vin + " not found", Snackbar.LENGTH_SHORT).show();
        }
    }
}