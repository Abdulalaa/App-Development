package com.example.essexcaremporium;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FindMakeActivity extends AppCompatActivity {

    @Inject
    CarList carList;   // Reference to the singleton CarList object

    private EditText editMake;
    private TextView textResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_make);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editMake = findViewById(R.id.edit_make);   // Input field for car make
        textResults = findViewById(R.id.text_results); // TextView for displaying the filtered results
    }

    // Method to filter and show cars by make
    public void searchByMake(View view) {
        String makeInput = editMake.getText().toString().trim();
        if (makeInput.isEmpty()) {
            textResults.setText("Please enter a valid car make.");
            return;
        }

        StringBuilder results = new StringBuilder("Cars matching the make: " + makeInput + "\n\n");

        // Iterate through the car list and find matches by make
        boolean carsFound = false;
        for (Car car : carList) {
            if (car.getMake().equalsIgnoreCase(makeInput)) {
                carsFound = true;
                results.append(car.getModel()).append(", ")
                        .append(car.getVin()).append(", ")
                        .append(car.getYear()).append(", ")
                        .append(car.getFuelType()).append(", $")
                        .append(car.getPrice()).append("\n");
            }
        }

        if (carsFound) {
            textResults.setText(results.toString());
        } else {
            textResults.setText("No cars found for the make: " + makeInput);
        }
    }
}