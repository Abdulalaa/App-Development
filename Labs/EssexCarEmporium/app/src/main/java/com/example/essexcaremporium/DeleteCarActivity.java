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

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeleteCarActivity extends AppCompatActivity {

    @Inject
    CarList carList;  // Reference to the singleton CarList object

    private EditText editVin;
    private TextView resultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_car);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editVin = findViewById(R.id.delete_vin);  // Input field for VIN
        resultMessage = findViewById(R.id.result_message);  // TextView for feedback messages
    }

    // Method to remove the car by VIN
    public void deleteCarByVin(View view) {
        String vinInput = editVin.getText().toString().trim();

        if (vinInput.isEmpty()) {
            resultMessage.setText("Please enter a valid VIN.");
            return;
        }

        // Iterate through the car list to find and remove the car by VIN
        boolean carFound = false;
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getVin().equalsIgnoreCase(vinInput)) {
                carList.remove(i);
                carFound = true;
                break;
            }
        }

        if (carFound) {
            resultMessage.setText("Car with VIN " + vinInput + " has been successfully deleted.");
        } else {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Car with VIN " + vinInput + " not found.",
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}