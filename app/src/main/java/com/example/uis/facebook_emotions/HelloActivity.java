package com.example.uis.facebook_emotions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    private TextView textViewHello;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        textViewHello = findViewById(R.id.textViewHello);
        username = getIntent().getStringExtra("USERNAME");

        textViewHello.setText("Hello, \n"+username);


    }
}
