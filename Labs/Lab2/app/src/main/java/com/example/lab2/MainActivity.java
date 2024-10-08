package com.example.lab2;

import android.os.Bundle;
import android.os.StrictMode;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        // Set up the toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Watch App");
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set the network policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void processPress(View view) throws IOException {
        // Initialize EditText for URL input
        EditText inputUrl = findViewById(R.id.input_url);

        // Initialize URL object
        URL url = new URL(inputUrl.getText().toString());

        // Initialize list to store watch objects
        ArrayList<Watch> watches = new ArrayList<>();

        // Initialize TextView for output
        TextView outputResults = findViewById(R.id.output_results);

        // Initialize buffered reader, open stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Count the number of watches (1 watch = 6 lines)
        int lineCounter = 0;
        int numWatches = 0;
        while (reader.readLine() != null) {
            lineCounter++;
            if (lineCounter % 6 == 0) {
                numWatches++;
            }
        }

        // Reset the reader to start again from the beginning
        reader.close();
        reader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Populate the watches array
        for (int i = 0; i < numWatches; i++) {
            Watch watch = new Watch(
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine(),
                    Integer.parseInt(reader.readLine()),
                    reader.readLine(),
                    Double.parseDouble(reader.readLine())
            );
            watches.add(watch);
        }

        // Close the reader
        reader.close();

        // Output watch data to TextView for every watch
        outputResults.setText("Watch Data:\n");
        for (int i = 0; i < numWatches; i++) {
            // Get the watch object for the current watch and output its data to the TextView
            Watch watch = watches.get(i);
            outputResults.append(
                    "Watch " + (i + 1) + ": \n" +
                            " Make: " + watch.getMake() + "\n" +
                            " Model: " + watch.getModel() + "\n" +
                            " Power Source: " + watch.getPowerSource() + "\n" +
                            " Water Resistance: " + watch.getWaterResistance() + "\n" +
                            " Band Content: " + watch.getBandContent() + "\n" +
                            " Price: " + watch.getPrice() + "\n"
            );
        }

        // Calculate and display the average water resistance
        double totalWaterResistance = 0;
        DecimalFormat df1 = new DecimalFormat("0.0"); // Format to one decimal place
        for (int i = 0; i < numWatches; i++) {
            totalWaterResistance += watches.get(i).getWaterResistance();
        }
        totalWaterResistance /= numWatches;
        outputResults.append("Average water resistance of watches: " + df1.format(totalWaterResistance) + " m\n");

        // Calculate and display the average price
        double totalPrice = 0;
        DecimalFormat df2 = new DecimalFormat("0.00"); // Format to two decimal places
        for (int i = 0; i < numWatches; i++) {
            totalPrice += watches.get(i).getPrice();
        }
        totalPrice /= numWatches;
        outputResults.append("Average price of watches: $" + df2.format(totalPrice) + "\n");
    }
}