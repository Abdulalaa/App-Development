package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    } // End onCreate

    public void calculate(View view) {
        EditText etPrincipal;
        EditText etYears;
        EditText etInterestRate;
        int years;
        double originalPrincipal;
        double interestRate;
        double finalPrincipal;
        TextView result = (TextView) findViewById(R.id.getResult);


        try {
            etPrincipal = (EditText) findViewById(R.id.getOriginalPrincipal);
            etYears = (EditText) findViewById(R.id.getYears);
            etInterestRate = (EditText) findViewById(R.id.getInterestRate);

            interestRate = Double.parseDouble(etInterestRate.getText().toString().trim());
            originalPrincipal = Double.parseDouble(etPrincipal.getText().toString().trim());
            years = Integer.parseInt(etYears.getText().toString().trim());

            finalPrincipal = principal(years, originalPrincipal, interestRate);

            result.setText(String.format("Principal after %d years is $%.2f \n", years, finalPrincipal));
            result.append(String.format("Accrued interest: $%.2f\n",(finalPrincipal - originalPrincipal)));
        } catch (NumberFormatException e) {
            result.setText("Problem: " + e.getMessage());
        }
    } // End calculate

    public double principal(int y, double p_orig, double i_rate) {
        // Recursive process of compound interest
        if (y == 0) {
            return p_orig;
        }

        return  principal(y - 1, p_orig, i_rate) * (1 + (i_rate/100));

    } // End principal

}