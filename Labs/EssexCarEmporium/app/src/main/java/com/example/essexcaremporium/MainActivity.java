package com.example.essexcaremporium;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import java.io.*;
import java.util.*;
import java.net.URL;
import java.text.*;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    CarList carList;   // Reference to the singleton CarList object

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set the network policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        tv = findViewById(R.id.text_main);  // Reference to the main TextView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_display_list) {
            onOption1(item);  // Display list
            return true;
        } else if (id == R.id.action_load_from_web) {
            onOption2(item);  // Load list from web file
            return true;
        } else if (id == R.id.action_add_new_car) {
            onOption3(item);  // Add a new car
            return true;
        } else if (id == R.id.action_show_car_details) {
            onOption4(item);  // Show car details
            return true;
        } else if (id == R.id.action_delete_car) {
            onOption5(item);  // Delete a car
            return true;
        } else if (id == R.id.action_show_avg_price) {
            onOption6(item);  // Show average price
            return true;
        } else if (id == R.id.action_show_avg_mpg) {
            onOption7(item);  // Show average MPG (for non-EVs)
            return true;
        } else if (id == R.id.action_show_by_make) {
            onOption8(item);  // Show cars by make
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Option 1: Display list
    public void onOption1(MenuItem i) {
        // Implementation for displaying the car list (without a new activity)
        tv.setText("Current Inventory List of Vehicles: \n");
        if (carList.isEmpty()) {
            tv.append("No cars found\n");
        }
        for (int j = 0; j < carList.size(); j++) {
             tv.append(carList.get(j).toString() + "\n");
        }
    }

    // Option 2: Load list from web file
    public void onOption2(MenuItem i) {
        // Implementation for loading the list from a web file (utilizing new activity)
        startActivity(new Intent(this, WebActivity.class));
    }

    // Option 3: Load car from assets file
    public void onOption3(MenuItem i) {
        // Implementation for adding a new car from an assets file (utilizing new activity)
        startActivity(new Intent(this, AssetsActivity.class));
    }

    // Option 4: Show car details from provided VIN
    public void onOption4(MenuItem i) {
        // Implementation for showing car details from a provided VIN
        startActivity(new Intent(this, SearchCarActivity.class));
    }

    // Option 5: Delete a car from provided VIN
    public void onOption5(MenuItem i) {
        // Implementation for deleting a car from a provided VIN
        startActivity(new Intent(this, DeleteCarActivity.class));
    }

    // Option 6: Show average price of cars (without new activity)
    public void onOption6(MenuItem i) {
        // Implementation for showing the average price of cars
        double total = 0;
        for (int j = 0; j < carList.size(); j++) {
            total += carList.get(j).getPrice();
        }
        if (carList.isEmpty()) {
            tv.setText("No cars found");
            return;
        }
        tv.setText("Average Price of Cars: $" + (total / carList.size()));
    }

    // Option 7: Show average MPG for non-EVs (without new activity)
    public void onOption7(MenuItem i) {
        // Implementation for showing the average MPG (excluding EVs)
        DecimalFormat df = new DecimalFormat("#.#");
        int total = 0;
        int count = 0;
        for (int j = 0; j < carList.size(); j++) {
            if (carList.get(j).getMpg() != -1) {
                total += carList.get(j).getMpg();
                count++;
            }
        }
        if (count == 0) {
            tv.setText("No non-EV cars found");
            return;
        }
        tv.setText("Average MPG of non-EVs: " + df.format((double) total / count) + " MPG");
    }

    // Option 8: Show all cars of a given make (with new activity
    public void onOption8(MenuItem i) {
        // Implementation for showing cars by make
        startActivity(new Intent(this, FindMakeActivity.class));
    }
}