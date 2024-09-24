package com.example.lab1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.content.res.AssetManager;
import androidx.core.view.WindowInsetsCompat;

import java.util.*; //Scanner, ArrayList, List
import java.io.*; //IOException, input/output

public class MainActivity extends AppCompatActivity {

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
    }

    public void processPress(View view) throws java.io.IOException {
        // Initialize EditText for user input/file names
        EditText fileName = (EditText) findViewById(R.id.edit_infile);
        EditText outputName = (EditText) findViewById(R.id.edit_outfile);

        // Initialize assetmanager/scanner to read assets folder
        AssetManager assetManager = getAssets();
        Scanner fsc = new Scanner(assetManager.open(fileName.getText().toString().trim()));

        // Initialize TextView to display results
        TextView results = (TextView) findViewById(R.id.cube_root_results);

        // Initialize list to store/manipulate results
        ArrayList<Double> resultsList = new ArrayList<Double>();

        // While loop to read input/add to list
        while (fsc.hasNextDouble()) {
            resultsList.add(fsc.nextDouble());
        }

        // Close scanner
        fsc.close();

        // Error checking for empty file
        if (resultsList.isEmpty()) {
            results.setText("File is empty!");
            return;
        }

        // Method to cube root list of doubles
        cube_root_it(resultsList, resultsList.size());

        // Loop through and display results
        results.setText("Cube Root Results: \n");
        for (int i = 0; i < resultsList.size(); i++) {
            results.append(resultsList.get(i) + "\n");
        }

        // Initialize file/writer to write to output file
        File outfile = new File(getExternalFilesDir(null), outputName.getText().toString().trim());
        FileWriter fw = new FileWriter(outfile);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        // If file already exists, notify user
        results.append("\n Appending values to file...\n");
        if (outfile.exists())
            results.append("File already exists, updating...");

        // Loop through cube root array and write to file
        for (int i = 0; i < resultsList.size(); i++) {

            pw.println(resultsList.get(i));
        }
        pw.close();
    } // End of processPress

    public void cube_root_it(ArrayList<Double> list, int size) {

        // Loop through and cube root each element
        for (int i = 0; i < size; i++) {
            list.set(i, Math.cbrt(list.get(i)));
        }
        return;
    } // End of cube_root_it
}