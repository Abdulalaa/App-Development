package com.example.paragraphapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.res.AssetManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicateActivity extends AppCompatActivity {

    private Socket socket = null; // Socket connection to the server
    private PrintWriter out = null; // Output stream to send data to the server
    private BufferedReader in = null; // Input stream to receive data from the server
    private TextView tv; // TextView for displaying messages to the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_communicate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        int port;
        String host;

        Intent intent = getIntent();
        port = intent.getIntExtra(MainActivity.PORT, 0); // Port number
        host = intent.getStringExtra(MainActivity.HOST_NAME); // Hostname

        tv = (TextView) findViewById(R.id.text_main);

        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            tv.setText("Connected to server successfully");
        } catch (IOException e) {
            tv.setText("Problem " + e.getMessage());
            socket = null; // Ensure socket is null if the connection fails
        }
    }

    public void sendParagraph(View view) {
        EditText filename;
        EditText operation;
        AssetManager assetManager;
        BufferedReader fsc;
        StringBuilder sb;
        int lineCount = 0;
        String line;

        if (socket == null) {
            tv.setText("Not connected to server");
            return;
        }

        try {
            filename = (EditText) findViewById(R.id.edit_file);
            operation = (EditText) findViewById(R.id.edit_operation);

            if (filename.getText().toString().equalsIgnoreCase("exit")) {
                out.println("exit");
                return;
            }

            assetManager = getAssets();
            fsc = new BufferedReader(new InputStreamReader(assetManager.open(filename.getText().toString())));
            sb = new StringBuilder();

            while ((line = fsc.readLine()) != null) {
                sb.append(line).append("\n");
                lineCount++;
            }
            fsc.close();

            out.println(operation.getText().toString());
            out.println(lineCount);
            out.println(sb.toString());
            out.flush();

            int responseLines = Integer.parseInt(in.readLine());
            StringBuilder response = new StringBuilder();

            for (int i = 0; i < responseLines; i++) {
                response.append(in.readLine()).append("\n");
            }

            tv.setText(response.toString());
        } catch (IOException e) {
            tv.setText("Problem " + e.getMessage());
        }
    }
}