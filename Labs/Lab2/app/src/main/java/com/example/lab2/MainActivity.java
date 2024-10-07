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
import android.widget.Button;
import android.widget.ScrollView;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import java.io.*; // For file i/o, IOException, etc
import java.net.*; // For URL, MalformedURLException, etc
import java.util.*; // For ArrayList, Scanner, etc
import java.text.*; // For DecimalFormat

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // Allow network access
        StrictMode.setThreadPolicy(policy); // Set policy/Allow network access
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    } // End onCreate

    public void processPress(View view) throws IOException {
        // Initialize EditText for URL input
        EditText inputUrl = findViewById(R.id.input_url);

        // Initialize URL object AND scanner object to retreive from URL provided by EditText and scan
        URL url = new URL(inputUrl.getText().toString());
        Scanner urlScanner = new Scanner(url.openStream());

        // Initialize list to store watch objects
        ArrayList<Watch> watches = new ArrayList<Watch>();

        // Initialize TextView for output
        TextView outputResults = findViewById(R.id.output_results);

        // Get number of watches for watch array
        int line = 0; // Line counter, one watch for every six lines
        int numWatches = 0;
        while (urlScanner.hasNextLine()) {
            line++;
            if (line % 6 == 0)
                numWatches++;
        }

        // Populate watch array
        for (int i = 0; i < numWatches; i++) {
            Watch watch = new Watch (
                    urlScanner.nextLine(),
                    urlScanner.nextLine(),
                    urlScanner.nextLine(),
                    Integer.parseInt(urlScanner.nextLine()),
                    urlScanner.nextLine(),
                    Double.parseDouble(urlScanner.nextLine())
            );
            watches.add(watch);
        }

        // Output watch data to TextView for every watch
        outputResults.setText("Watch Data:\n");
        for (int i = 0; i < numWatches; i++) {
            outputResults.append(
                    "Watch " + i + ": \n" +
                    " Make: " + watches.get(i).getMake() + "\n" +
                    " Model: " + watches.get(i).getModel() + "\n" +
                    " Power Source: " + watches.get(i).getPowerSource() + "\n" +
                    " Water Resistance: " + watches.get(i).getWaterResistance() + "\n" +
                    " Band Content: " + watches.get(i).getBandContent() + "\n"
                    " Price: " + watches.get(i).getPrice() + "\n"
            );
      }
        // Average Water Resistance for watches
        double totalWaterResistance = 0;
        DecimalFormat df = new DecimalFormat("0.0"); // Format to one decimal place
        for (int i = 0; i < numWatches; i++) {
            totalWaterResistance += watches.get(i).getWaterResistance();
        }
        totalWaterResistance /= numWatches;
        outputResults.append("Average water resistance of watches: " + df.format(totalWaterResistance) + " m\n");

        // Average Price for watches
        double totalPrice = 0;
        DecimalFormat df = new DecimalFormat("0.00"); // Format to two decimal places
        for (int i = 0; i < numWatches; i++) {
            totalPrice += watches.get(i).getPrice();
        }
        totalPrice /= numWatches;
        outPutResults.append("Average price of watches: $" + df.format(totalPrice) + "\n");
}


